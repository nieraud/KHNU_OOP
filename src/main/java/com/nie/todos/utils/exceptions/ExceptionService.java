package com.nie.todos.utils.exceptions;

import static spark.Spark.exception;

/**
 * Created by inna on 20.05.17.
 */
public class ExceptionService {
    public void init(){

        exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
        });

    }
}
