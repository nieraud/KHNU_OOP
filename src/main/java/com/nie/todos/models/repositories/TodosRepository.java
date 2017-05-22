package com.nie.todos.models.repositories;

import com.nie.todos.utils.status.Status;
import com.nie.todos.models.pojos.entities.Todos;
import org.sql2o.Sql2oException;

import java.util.List;

/**
 * Created by inna on 20.05.17.
 */
public interface TodosRepository {
    void add(String newTodos) throws Sql2oException;

    List<Todos> getAll();

    List<Todos> ofStatus(String statusString);

    List<Todos> ofStatus(Status status);

    void removeCompleted();

    void remove(String id);

   Todos find(String id);

    public void toggleAll(boolean complete);

    void update(String id, String title);

    void toggleStatus(String id) throws Exception;
}
