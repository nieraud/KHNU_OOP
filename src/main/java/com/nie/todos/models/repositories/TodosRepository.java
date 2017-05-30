package com.nie.todos.models.repositories;

import com.nie.todos.utils.status.Status;
import com.nie.todos.models.pojos.entities.Todos;
import org.sql2o.Sql2oException;

import java.util.List;

/**
 * Created by inna on 20.05.17.
 */
public interface TodosRepository {

    void add(String newTodos, String iduser) throws Sql2oException;

    List<Todos> getAll();

    List<Todos> getAllByUserId(String iduser);

    List<Todos> ofStatus(String statusString, String iduser);

    List<Todos> ofStatus(Status status, String iduser);

    void removeCompleted(String iduser);

    void remove(String id, String iduser);

    Todos find(String id);

    void toggleAll(boolean complete, String iduser);

    void update(String id, String title, String iduser);

    void toggleStatus(String id, String iduser) throws Exception;
}
