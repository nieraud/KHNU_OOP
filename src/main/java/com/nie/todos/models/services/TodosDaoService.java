package com.nie.todos.models.services;

import com.nie.todos.models.Status;
import com.nie.todos.models.Todo;
import com.nie.todos.models.TodoDao;
import com.nie.todos.models.factory.AbstractDaoFactory;
import com.nie.todos.models.pojos.entities.Todos;
import com.nie.todos.models.repositories.TodosRepository;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by inna on 20.05.17.
 */
public class TodosDaoService extends AbstractDaoService implements TodosRepository {
    public TodosDaoService(AbstractDaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public void add(String newTodos) throws Sql2oException {
        String sql = "INSERT INTO todos VALUES(DEFAULT, :title, DEFAULT);";

        try (Connection connection = daoFactory.getDataSource().open()) {
            connection.createQuery(sql)
                    .addParameter("title", newTodos)
                    .executeUpdate();
        } catch (Sql2oException e) {
            throw new Sql2oException(e);
        }
    }

    @Override
    public List<Todos> getAll() {
        String sql = "SELECT * FROM todos;";
        List<Todos> listTodos;

        try (Connection connection = daoFactory.getDataSource().open()) {
            listTodos = connection.createQuery(sql)
                    .executeAndFetch(Todos.class);
        } catch (Sql2oException e) {
            throw new Sql2oException(e);
        }
        return listTodos;
    }

    @Override
    public List<Todos> ofStatus(String statusString) {
        return (statusString == null || statusString.isEmpty()) ? getAll() : ofStatus(Status.valueOf(statusString.toUpperCase()));
    }

    @Override
    public List<Todos> ofStatus(Status status) {
        System.out.println("ofStatus (Status status)");
        return getAll().stream().filter(t -> t.getStatus().equals(status)).collect(Collectors.toList());
    }

    @Override
    public void removeCompleted() {
        // ofStatus(Status.COMPLETE).forEach(t -> remove(t.getUniqueid().toString()));
        String sql = "DELETE FROM todos WHERE status = :status;";

        try (Connection connection = daoFactory.getDataSource().open()) {
            connection.createQuery(sql)
                    .addParameter("status", Status.COMPLETE)
                    .executeUpdate();
        } catch (Sql2oException e) {
            throw new Sql2oException(e);
        }
    }

    @Override
    public void remove(String id) {
        String sql = "DELETE FROM todos WHERE uniqueid = :id;";

        try (Connection connection = daoFactory.getDataSource().open()) {
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException e) {
            throw new Sql2oException(e);
        }
        //listTodos.remove(find(id));
    }

    @Override
    public Todos find(String id) {
        String sql = "SELECT * FROM todos WHERE uniqueid = :id;";

        System.out.println(" UUID = " + id);
        try (Connection connection = daoFactory.getDataSource().open()) {
            return connection.createQuery(sql)
                    .addParameter("id", UUID.fromString(id))
                    .executeAndFetchFirst(Todos.class);
        } catch (Sql2oException e) {
            throw new Sql2oException(e);
        }
        //return listTodos.stream().filter(t -> t.getUniqueid().toString().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void update(String id, String title) {
        //find(id).setTitle(title);
        String sql = "UPDATE todos SET title = :title WHERE uniqueid = :id;";

        try (Connection connection = daoFactory.getDataSource().open()) {
            connection.createQuery(sql)
                    .addParameter("title", title)
                    .addParameter("uniqueid", id)
                    .executeUpdate();
        } catch (Sql2oException e) {
            throw new Sql2oException(e);
        }
    }

    @Override
    public void toggleStatus(String id) throws Exception {
        // todo.getStatus() = Status.COMPLETE ? Status.ACTIVE : Status.COMPLETE;
        String sql = "UPDATE todos SET status = :status WHERE uniqueid = :id;";

        try (Connection connection = daoFactory.getDataSource().beginTransaction()) {
            connection.createQuery(sql)
                    .addParameter("status", isComplete(find(id)))
                    .addParameter("id", UUID.fromString(id))
                    .executeUpdate();
            connection.commit(true);
        } catch (Sql2oException e) {
            throw new Sql2oException(e);
        }
    }

    private String isComplete(Todos todo) throws Exception {
        if (todo.getStatus() == Status.ACTIVE)
            return "COMPLETE";
        else if (todo.getStatus() == Status.COMPLETE)
            return "ACTIVE";
        else return "";
    }

    @Override
    public void toggleAll(boolean complete) {
        // getAll().forEach(t -> t.setStatus(complete ? Status.COMPLETE : Status.ACTIVE));
        String sql = "UPDATE todos SET status = :status;";

        try (Connection connection = daoFactory.getDataSource().open()) {
            connection.createQuery(sql)
                    .addParameter("status", complete ? Status.COMPLETE : Status.ACTIVE)
                    .executeUpdate();
        } catch (Sql2oException e) {
            throw new Sql2oException(e);
        }
    }

}
