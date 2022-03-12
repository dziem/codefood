package com.akmal.codefood.controller;

import com.akmal.codefood.api.request.CompleteStepForm;
import com.akmal.codefood.api.request.ReactionForm;
import com.akmal.codefood.api.search.ServeSearchForm;
import com.akmal.codefood.entity.dto.ServeDto;
import com.akmal.codefood.service.ServeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Locale;

@CrossOrigin
@RestController
@RequestMapping("serve-histories")
public class ServeController extends BaseController {
    @Autowired
    private ServeService serveService;

    @Secured("ROLE_USER")
    @GetMapping
    public ResponseEntity<Object> list(@RequestParam(value = "skip", required = false, defaultValue = "0") int skip,
                                       @RequestParam(value = "limit", required = false, defaultValue = "10") int size,
                                       @RequestParam(value = "sort", required = false) String sort,
                                       ServeSearchForm searchForm) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        searchForm.setUsername(username);
        searchForm.setIsReviewed(false);
        searchForm.setIsFinished(false);
        boolean asc = false;
        if (StringUtils.isEmpty(sort)) {
            sort = "updatedAt";
        } else if (sort.equals("oldest")) {
            sort = "updatedAt";
            asc = true;
        } else if (sort.equals("nserve_asc")) {
            sort = "nServing";
            asc = true;
        } else if (sort.equals("nserve_desc")) {
            sort = "nServing";
            asc = false;
        }
        if (!StringUtils.isEmpty(searchForm.getStatus())) {
            if (searchForm.getStatus().equals("need-rating")) {
                searchForm.setIsFinished(true);
            }
            if (searchForm.getStatus().equals("done")) {
                searchForm.setIsFinished(true);
                searchForm.setIsReviewed(true);
            }
        }
        Pageable pageable = PageRequest.of(skip, size, Sort.by(getSortBy(sort, asc)));
        return ok(serveService.list(pageable, searchForm));
    }

    @Secured("ROLE_USER")
    @GetMapping("/{id}")
    public ResponseEntity<Object> detail(@PathVariable("id") String id) {
        return ok(serveService.detail(id));
    }

    @Secured("ROLE_USER")
    @PostMapping
    public ResponseEntity<Object> start(@Valid @RequestBody ServeDto form) {
        return ok(serveService.create(form), HttpStatus.CREATED);
    }

    @Secured("ROLE_USER")
    @PutMapping("/{id}/done-step")
    public ResponseEntity<Object> completeStep(@PathVariable("id") String id, @Valid @RequestBody CompleteStepForm form) {
        return ok(serveService.completeStep(id, form.getStepOrder()));
    }

    @Secured("ROLE_USER")
    @PostMapping("/{id}/reaction")
    public ResponseEntity<Object> sendReaction(@PathVariable("id") String id, @Valid @RequestBody ReactionForm form) {
        return ok(serveService.sendReaction(id, form.getReaction().toLowerCase(Locale.ROOT)));
    }
}
