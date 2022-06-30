package com.redhat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/api")
public class ApiResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiResource.class);

    @POST@Transactional
    public Response addTodo(final Todo todoToAdd) {

        LOGGER.debug("creating Todo: {}", todoToAdd);

        Todo todo = new Todo();
        todo.setTitle(todoToAdd.getTitle());
        todo.setCompleted(todoToAdd.isCompleted());
        todo.setOrder(todoToAdd.getOrder());

        todo.persist();

        LOGGER.debug("Todo created: {}", todo);

        return Response.created(URI.create("/" + todo.getId())).entity(todo).build();
    }

    @PATCH @Transactional @Path("/{id}")
    public Response updateTodo(@PathParam("id") Long id, Todo updatedTodo){

        Todo todo = Todo.findById(id);
        todo.setOrder(updatedTodo.getOrder());
        todo.setCompleted(updatedTodo.isCompleted());
        todo.setTitle(updatedTodo.getTitle());
        todo.persist();
        return Response.ok().entity(todo).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteTodo(@PathParam("id") Long id) {

        Todo todo = Todo.findById(id);
        todo.delete();
        return Response.ok().build();
    }

    @GET
    public Response allTodos() {

        List<Todo> allTodos = Todo.listAll();
        return Response.ok().entity(allTodos).build();
    }
}
