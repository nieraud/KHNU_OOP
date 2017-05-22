package com.nie.todos.controllers.todosCtrl;

import com.nie.todos.controllers.Routing;
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
public class TodosRouter implements Routing {

    private static final TodosRepository todosService = DaoServiceFactory.getTodosService();

    @Override
    public void init() {

        exception(Exception.class, (e, req, res) -> e.printStackTrace()); // print all exceptions
        staticFiles.location("/public");
        port(9999);

        // Render main UI
        get("/", (req, res) -> renderTodos(req));

        // Add new
        post("/todos", (req, res) -> {
            todosService.add(req.queryParams("todo-title"));
            return renderTodos(req);
        });

        // Remove all completed
        delete("/todos/completed", (req, res) -> {
            todosService.removeCompleted();
            return renderTodos(req);
        });

        // Toggle all status
        put("/todos/toggle_status", (req, res) -> {
            todosService.toggleAll(req.queryParams("toggle-all") != null);
            return renderTodos(req);
        });

        // Remove by id
        delete("/todos/:uniqueid", (req, res) -> {
            todosService.remove(req.params("uniqueid"));
            return renderTodos(req);
        });

        // Update by id
        put("/todos/:uniqueid", (req, res) -> {
            todosService.update(req.params("uniqueid"), req.queryParams("todo-title"));
            return renderTodos(req);
        });

        // Toggle status by id
        put("/todos/:uniqueid/toggle_status", (req, res) -> {
            todosService.toggleStatus(req.params("uniqueid"));
            return renderTodos(req);
        });

        // Edit by id
        get("/todos/:uniqueid/edit", (req, res) -> renderEditTodo(req));

    }

    private static String renderEditTodo(Request req) {
        return renderTemplate("velocity/editTodo.vm", new HashMap() {{
            put("todo", todosService.find(req.params("uniqueid")));
        }});
    }

    private static String renderTodos(Request req) {
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
