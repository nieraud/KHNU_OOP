package com.nie.todos;

import com.nie.todos.utils.exceptions.ExceptionService;
import com.nie.todos.controllers.todosCtrl.TodosRouter;
import com.nie.todos.controllers.Routing;

/**
 * Created by inna on 20.05.17.
 */
public class FacadeStarter {

    static void start(){
        startExceptionService();
        startRoutingService();
    }

    private static void startExceptionService() {
        ExceptionService exceptionService = new ExceptionService();
        exceptionService.init();
    }

    private static void startRoutingService() {
        Routing route = new TodosRouter();
        route.init();
    }
}
