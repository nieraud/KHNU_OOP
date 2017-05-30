package com.nie.todos.controllers.todosCtrl;

import com.nie.todos.models.factory.DaoServiceFactory;
import com.nie.todos.models.pojos.entities.Todos;
import com.nie.todos.models.repositories.TodosRepository;
import com.nie.todos.utils.status.Status;
import lombok.Getter;
import spark.ModelAndView;
import spark.Request;
import spark.Route;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by inna on 29.05.17.
 */
public class TodosCtrl {
    private static final TodosRepository todosService = DaoServiceFactory.getTodosService();

    @Getter
    private static final Route todos = (request, response) -> renderTodos(request);

    @Getter
    private static final Route addNew = (request, response) -> {
        todosService.add(request.queryParams("todo-title"));
        return renderTodos(request);
    };

    @Getter
    private static final Route removeAllCompl = (request, response) -> {
        todosService.removeCompleted();
        return renderTodos(request);
    };

    @Getter
    private static final Route toggleAllStatus = (request, response) -> {
        todosService.toggleAll(request.queryParams("toggle-all") != null);
        return renderTodos(request);
    };

    @Getter
    private static final Route removeById = (request, response) -> {
        todosService.remove(request.params("id"));
        return renderTodos(request);
    };

    @Getter
    private static final Route updateById = (request, response) -> {
        todosService.update(request.params("id"), request.queryParams("todo-title"));
        return renderTodos(request);
    };

    @Getter
    private static final Route toggleStatusById = (request, response) -> {
        todosService.toggleStatus(request.params("id"));
        return renderTodos(request);
    };

    @Getter
    private static final Route editById = (request, response) -> renderTemplate("velocity/editTodo.vm", new HashMap() {{
        put("todo", todosService.find(request.params("id")));
    }});

    public static String renderTodos(Request req) {
        String statusStr = req.queryParams("status");
        List<Todos> list = todosService.ofStatus(statusStr);
        int sizeActive = todosService.ofStatus(Status.ACTIVE).size();

        Map<String, Object> model = new HashMap<>();
        model.put("todos", list);
        model.put("filter", Optional.ofNullable(statusStr).orElse(""));
        model.put("activeCount", sizeActive);
        model.put("anyCompleteTodos", (list.size() - sizeActive) > 0);
        model.put("allComplete", sizeActive == 0);
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
