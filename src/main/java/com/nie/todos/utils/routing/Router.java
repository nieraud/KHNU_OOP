package com.nie.todos.utils.routing;

import com.nie.todos.utils.status.Status;
import com.nie.todos.models.factory.DaoServiceFactory;
import com.nie.todos.models.repositories.TodosRepository;
import spark.ModelAndView;
import spark.Request;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static spark.Spark.*;

/**
 * Created by inna on 20.05.17.
 */
public class Router implements Routing {

    private static final TodosRepository todosService = DaoServiceFactory.getTodosService();

    @Override
    public void init() {

        exception(Exception.class, (e, req, res) -> e.printStackTrace()); // print all exceptions
        staticFiles.location("/public");
        port(9999);

        // Render main UI
        get("/", (req, res) -> renderTodosCustom(req));

        // Add new
        post("/todos", (req, res) -> {
            todosService.add(req.queryParams("todo-title"));
            return renderTodosCustom(req);
        });

        // Remove all completed
        delete("/todos/completed", (req, res) -> {
            todosService.removeCompleted();
            return renderTodosCustom(req);
        });

        // Toggle all status
        put("/todos/toggle_status", (req, res) -> {
            todosService.toggleAll(req.queryParams("toggle-all") != null);
            return renderTodosCustom(req);
        });

        // Remove by id
        delete("/todos/:uniqueid", (req, res) -> {
            todosService.remove(req.params("uniqueid"));
            return renderTodosCustom(req);
        });

        // Update by id
        put("/todos/:uniqueid", (req, res) -> {
            todosService.update(req.params("uniqueid"), req.queryParams("todo-title"));
            return renderTodosCustom(req);
        });

        // Toggle status by id
        put("/todos/:uniqueid/toggle_status", (req, res) -> {
            todosService.toggleStatus(req.params("uniqueid"));
            return renderTodosCustom(req);
        });

        // Edit by id
        get("/todos/:uniqueid/edit", (req, res) -> renderEditTodo(req));

    }

    private static String renderEditTodo(Request req) {
        return renderTemplate("velocity/editTodo.vm", new HashMap() {{
            put("todo", todosService.find(req.params("uniqueid")));
        }});
    }

    private static String renderTodosCustom(Request req) {
        String statusStr = req.queryParams("status");

        Map<String, Object> model = new HashMap<>();
        model.put("todos", todosService.ofStatus(statusStr));
        model.put("filter", Optional.ofNullable(statusStr).orElse(""));
        model.put("activeCount", todosService.ofStatus(Status.ACTIVE).size());
        model.put("anyCompleteTodos", todosService.ofStatus(Status.COMPLETE).size() > 0);
        model.put("allComplete", todosService.getAll().size() == todosService.ofStatus(Status.COMPLETE).size());
        model.put("status", Optional.ofNullable(statusStr).orElse(""));

        if ("true".equals(req.queryParams("ic-request"))) {
            return renderTemplate("velocity/todoList.vm", model);
        }
        return renderTemplate("velocity/index.vm", model);
    }

    private static String renderTemplate(String template, Map model) {
        return new VelocityTemplateEngine().render(new ModelAndView(model, template));
    }
}
