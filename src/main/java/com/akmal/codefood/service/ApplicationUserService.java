package com.akmal.codefood.service;

import com.akmal.codefood.api.AuthToken;
import com.akmal.codefood.entity.ApplicationUser;
import com.akmal.codefood.entity.dto.UserDto;
import com.akmal.codefood.exception.BadRequestException;
import com.akmal.codefood.repository.ApplicationUserRepository;
import com.akmal.codefood.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ApplicationUserService {
    public static final int MAX_FAILED_ATTEMPTS = 3;

    private static final long LOCK_TIME_DURATION = 60 * 1000;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTUtil jwtUtil;

    public UserDto register(UserDto userDto) {
        Optional<ApplicationUser> existingUser = applicationUserRepository.findByUsername(userDto.getUsername());
        if (existingUser.isPresent()) {
            throw new BadRequestException("username " + userDto.getUsername() + " already registered");
        }
        if (userDto.getPassword().length() < 6) {
            throw new BadRequestException("password minimum 6 characters");
        }
        BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(userDto.getUsername());
        applicationUser.setPassword(bcryptEncoder.encode(userDto.getPassword()));
        applicationUser.setFailedAttempt(0);
        applicationUser.setAccountNonLocked(true);
        applicationUserRepository.save(applicationUser);
        UserDto returnDto = new UserDto();
        returnDto.setId(applicationUser.getId());
        returnDto.setUsername(applicationUser.getUsername());
        return returnDto;
    }

    public AuthToken login(UserDto form) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword());

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(form.getUsername());

            successLogin(form.getUsername());

            return new AuthToken(token);
        } catch (AuthenticationException authExc){
            failedLogin(form.getUsername());
            throw new BadRequestException("Invalid username or Password", HttpStatus.UNAUTHORIZED);
        }
    }

    private void failedLogin(String username) {
        ApplicationUser user = getByUsername(username);
        if (user.getAccountNonLocked() == null) {
            user.setAccountNonLocked(true);
        }
        if (user.getFailedAttempt() == null) {
            user.setFailedAttempt(0);
        }

        if (user != null) {
            if (user.getAccountNonLocked().equals(Boolean.TRUE)) {
                if (user.getFailedAttempt() < MAX_FAILED_ATTEMPTS - 1) {
                    increaseFailedAttempts(user);
                } else {
                    lock(user);
                    throw new BadRequestException("Your account has been locked due to 3 failed attempts."
                            + " It will be unlocked after 1 minute.", HttpStatus.FORBIDDEN);
                }
            } else {
                if (unlockWhenTimeExpired(user)) {
                    throw new BadRequestException("Your account has been unlocked. Please try to login again.");
                }
                throw new BadRequestException("Your account has been locked due to 3 failed attempts."
                        + " It will be unlocked after 1 minute.", HttpStatus.FORBIDDEN);
            }
        }
    }

    private void successLogin(String username) {
        ApplicationUser user = getByUsername(username);
        unlockWhenTimeExpired(user);
        if (user.getLockTime() != null) {
            throw new BadRequestException("Your account has been locked due to 3 failed attempts."
                    + " It will be unlocked after 1 minute.", HttpStatus.FORBIDDEN);
        }
        if (user.getFailedAttempt() == null) {
            user.setFailedAttempt(0);
        }
        if (user.getFailedAttempt() > 0) {
            resetFailedAttempts(user);
        }
    }

    public ApplicationUser getByUsername(String username) {
        Optional<ApplicationUser> applicationUser = applicationUserRepository.findByUsername(username);
        if (applicationUser.isPresent()) {
            return applicationUser.get();
        }
        throw new BadRequestException("User not found");
    }

    private void increaseFailedAttempts(ApplicationUser user) {
        int newFailAttempts;
        if (user.getFailedAttempt() == null) {
            newFailAttempts = 1;
        } else {
            newFailAttempts = user.getFailedAttempt() + 1;
        }
        updateFailedAttempts(newFailAttempts, user);
    }

    private void resetFailedAttempts(ApplicationUser user) {
        user.setLockTime(null);
        user.setAccountNonLocked(true);
        updateFailedAttempts(0, user);
    }

    private void updateFailedAttempts(Integer attempt, ApplicationUser user) {
        user.setFailedAttempt(attempt);
        applicationUserRepository.save(user);
    }

    private void lock(ApplicationUser user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date());

        applicationUserRepository.save(user);
    }

    private boolean unlockWhenTimeExpired(ApplicationUser user) {
        if (user.getLockTime() != null) {
            long lockTimeInMillis = user.getLockTime().getTime();
            long currentTimeInMillis = System.currentTimeMillis();

            if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
                user.setAccountNonLocked(true);
                user.setLockTime(null);
                user.setFailedAttempt(0);

                applicationUserRepository.save(user);

                return true;
            }
        }

        return false;
    }
}
