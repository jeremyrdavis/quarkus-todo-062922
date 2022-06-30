package com.redhat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController @RequestMapping("/springapi")
public class SpringApiResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringApiResource.class);

    @PostMapping @Transactional
    public Todo addTodo(final Todo todoToAdd) {

        LOGGER.debug("Spring API creating Todo: {}", todoToAdd);

        Todo todo = new Todo();
        todo.setTitle(todoToAdd.getTitle());
        todo.setCompleted(todoToAdd.isCompleted());
        todo.setOrder(todoToAdd.getOrder());

        todo.persist();

        LOGGER.debug("Spring API Todo created: {}", todo);

        return todo;

    }
}
