package com.example.contentcalender.controller;

import com.example.contentcalender.model.Content;
import com.example.contentcalender.repository.ContentCollectionRepository;
import com.example.contentcalender.repository.ContentJdbcTemplateRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/content")
public class ContentController {
    //private final ContentCollectionRepository repository;
    private final ContentJdbcTemplateRepository jdbcTemplateRepository;

    /*
    Autowiring the Repository bean @Autowired can be here annotated but not mandatory
    You should not create new instances of classes, which are already in the application context.
    By default, the Scope of the Beans are Singleton, which mean for entire Application only one Bean is
    created, these Beans are recognised by Spring at the time of Component scan, which are typically
    annotated by @Component at class level or specialised annotations of @Component such as @Repository,
     @Service, @RestController and at Constructor or method  level by @Bean, remember, @Bean is always
     annotated by @Configuration at class level.
     */
    public ContentController(ContentJdbcTemplateRepository jdbcTemplateRepository) { // DI
        this.jdbcTemplateRepository = jdbcTemplateRepository;
    }

    // make a request and find all the pieces of content in the system
    @GetMapping("")  // specialised version of @RequestMapping
    public List<Content> findAll() {
        return jdbcTemplateRepository.getAllContent();
    }

    // CRUD

    @GetMapping("/{id}") // {id} dynamic placeholder - works for every id in the list
    public Content findById(@PathVariable Integer id) { // @PathVariable - mapping {id} to user input id
        // - whatever is in the path will be assigned  to the argument
        return jdbcTemplateRepository.getContent(id);

    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@Valid @RequestBody Content content) {
        jdbcTemplateRepository.createContent(content.title(), content.desc(), content.status(),
                content.contentType(), content.url());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@RequestBody Content updatedContent, @PathVariable Integer id) {
        if (!jdbcTemplateRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found.");
        }
        jdbcTemplateRepository.updateContent(id, updatedContent.title(), updatedContent.desc(),
                updatedContent.status(), updatedContent.contentType(), updatedContent.url());
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        jdbcTemplateRepository.deleteContent(id);
    }
}
