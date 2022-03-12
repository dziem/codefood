package com.akmal.codefood.service;

import com.akmal.codefood.api.search.ServeSearchForm;
import com.akmal.codefood.entity.ApplicationUser;
import com.akmal.codefood.entity.Recipe;
import com.akmal.codefood.entity.Serve;
import com.akmal.codefood.entity.dto.RecipeDto;
import com.akmal.codefood.entity.dto.RecipeListDto;
import com.akmal.codefood.entity.dto.ServeDto;
import com.akmal.codefood.entity.dto.ServeListDto;
import com.akmal.codefood.exception.BadRequestException;
import com.akmal.codefood.repository.ServeRepository;
import com.akmal.codefood.repository.specification.ServeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class ServeService implements CrudService<ServeDto> {
    @Autowired
    private ServeRepository serveRepository;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ApplicationUserService userService;

    private String entityName = "Serve history";

    public ServeListDto list(Pageable pageable, ServeSearchForm searchForm) {
        ServeSpecification specification = ServeSpecification.create(searchForm);
        Page<Serve> serves = serveRepository.findAll(specification, pageable);
        List<ServeDto> serveDtos = new ArrayList<>();
        for (Serve serve: serves.getContent()) {
            ServeDto serveDto = new ServeDto();
            serveDto.toDto(serve);
            if ((searchForm.getIsFinished().equals(Boolean.FALSE) && serveDto.getStatus().equals("progress"))
                || (searchForm.getIsFinished().equals(Boolean.TRUE) && serveDto.getStatus().equals("done"))
                    || StringUtils.isEmpty(searchForm.getStatus())) {
                serveDto.setSteps(null);
                serveDtos.add(serveDto);
            }
        }
        ServeListDto serveListDto = new ServeListDto();
        serveListDto.setTotal(serveDtos.size());
        serveListDto.setHistory(serveDtos);
        return serveListDto;
    }

    public ServeDto detail(String id) {
        Serve serve = serveRepository.getById(id, this.entityName);
        ServeDto serveDto = new ServeDto();
        serveDto.toDto(serve);
        return serveDto;
    }

    public ServeDto completeStep(String id, Integer stepOrder) {
        Serve serve = serveRepository.getById(id, this.entityName);
        validateUser(serve.getUser().getId());
        if (((stepOrder - serve.getNStepDone()) != 1)) {
            throw new BadRequestException("Some steps before " + stepOrder +" is not done yet");
        }
        if (stepOrder > serve.getRecipe().getSteps().size()) {
            throw new BadRequestException("Invalid step.");
        }
        serve.setNStepDone(stepOrder);
        serveRepository.save(serve);
        ServeDto serveDto = new ServeDto();
        serveDto.toDto(serve);
        return serveDto;
    }

    @Transactional
    public ServeDto sendReaction(String id, String reaction) {
        List<String> reactions = Arrays.asList("like", "neutral", "dislike");
        if (!reactions.contains(reaction)) {
            throw new BadRequestException("Invalid reaction.");
        }
        Serve serve = serveRepository.getById(id, this.entityName);
        if (serve.getNStepDone() != serve.getRecipe().getSteps().size()) {
            throw new BadRequestException("Complete recipe first");
        }
        validateUser(serve.getUser().getId());
        serve.setReaction(reaction);
        serveRepository.save(serve);
        recipeService.updateReaction(serve.getRecipe().getId(), reaction);
        ServeDto serveDto = new ServeDto();
        serveDto.toDto(serve);
        return serveDto;
    }

    private void validateUser(Long userId) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser user = userService.getByUsername(username);
        if (user != null && userId != user.getId()) {
            throw new BadRequestException("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ServeDto create(ServeDto dto) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Recipe recipe = recipeService.getById(dto.getRecipeId());
        ApplicationUser user = userService.getByUsername(username);
        Serve serve = dto.fromDto();
        serve.setRecipe(recipe);
        serve.setUser(user);
        serveRepository.save(serve);
        dto.toDto(serve);
        return dto;
    }

    @Override
    public ServeDto update(Long id, ServeDto dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
