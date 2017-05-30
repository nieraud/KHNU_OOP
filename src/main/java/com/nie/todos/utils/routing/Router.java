package com.nie.todos.utils.routing;

import com.nie.todos.controllers.oauthCtrl.OAuthCtrl;
import com.nie.todos.controllers.todosCtrl.TodosCtrl;

import static spark.Spark.*;

/**
 * Created by inna on 29.05.17.
 */
public class Router implements Routing {

    @Override
    public void init() {

        //exception(Exception.class, (e, req, res) -> e.printStackTrace()); // print all exceptions
        staticFiles.location("/public");
        port(8080);

        // main UI
        get("/", (req, res) -> {res.redirect("/starter.html"); return "";});

        //oauth with google
        get("/google", OAuthCtrl.getOauth());

        //get page with todos
        get("/auth/todos", OAuthCtrl.getTodos());

        //get todos for for status
        get("/todos", TodosCtrl.getTodos());

        // Add new
        post("/todos", TodosCtrl.getAddNew());

        // Remove all completed
        delete("/todos/completed", TodosCtrl.getRemoveAllCompl());

        // Toggle all status
        put("/todos/toggle_status", TodosCtrl.getToggleAllStatus());

        // Remove by id
        delete("/todos/:id", TodosCtrl.getRemoveById());

        // Update by id
        put("/todos/:id", TodosCtrl.getUpdateById());

        // Toggle status by id
        put("/todos/:id/toggle_status", TodosCtrl.getToggleStatusById());

        // Edit by id
        get("/todos/:id/edit", TodosCtrl.getEditById());

        //Exit
        get("/exit", OAuthCtrl.getExit());

    }
}