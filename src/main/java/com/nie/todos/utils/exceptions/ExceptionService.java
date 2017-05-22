package com.nie.todos.utils.exceptions;

import org.sql2o.Sql2oException;

import static spark.Spark.exception;

/**
 * Created by inna on 20.05.17.
 */
public class ExceptionService {
    public void init(){

        exception(Exception.class, (exception, request, response) -> {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        });

        exception(Sql2oException.class, (exception, request, response) -> {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        });

        exception(NullPointerException.class, (exception, request, response) ->{
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        });

    }
}
