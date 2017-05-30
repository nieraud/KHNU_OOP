package com.nie.todos.models.services;

import com.nie.todos.utils.status.Status;
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
        String sql = "INSERT INTO todos VALUES(:id, :title, 'ACTIVE');";
        String id = UUID.randomUUID().toString();

        try (Connection connection = daoFactory.getDataSource().open()) {
            connection.createQuery(sql)
                    .addParameter("id",id)
                    .addParameter("title", newTodos)
                    .executeUpdate();
        } catch (Sql2oException e) {
            throw new Sql2oException(e);
        }
    }

    @Override
    public List<Todos> getAll() {
        String sql = "SELECT * FROM todos;";

        try (Connection connection = daoFactory.getDataSource().open()) {
            return connection.createQuery(sql)
                    .executeAndFetch(Todos.class);
        } catch (Sql2oException e) {
            throw new Sql2oException(e);
        }
    }

    @Override
    public List<Todos> ofStatus(String statusString) {
        return (statusString == null || statusString.isEmpty()) ? getAll() : ofStatus(Status.valueOf(statusString.toUpperCase()));
    }

    @Override
    public List<Todos> ofStatus(Status status) {
        return getAll().stream().filter(t -> t.getStatus().equals(status)).collect(Collectors.toList());
    }

    @Override
    public void removeCompleted() {
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
        String sql = "DELETE FROM todos WHERE id = :id;";

        try (Connection connection = daoFactory.getDataSource().open()) {
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException e) {
            throw new Sql2oException(e);
        }
    }

    @Override
    public Todos find(String id) {
        String sql = "SELECT * FROM todos WHERE id = :id;";

        try (Connection connection = daoFactory.getDataSource().open()) {
           return connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Todos.class);
        } catch (Sql2oException e) {
            throw new Sql2oException(e);
        }
    }

    @Override
    public void update(String id, String title) {
        String sql = "UPDATE todos SET title = :title WHERE id = :id;";

        try (Connection connection = daoFactory.getDataSource().open()) {
            connection.createQuery(sql)
                    .addParameter("title", title)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException e) {
            throw new Sql2oException(e);
        }
    }

    @Override
    public void toggleStatus(String id){
        String sql = "UPDATE todos SET status = :status WHERE id = :id;";

        try (Connection connection = daoFactory.getDataSource().open()) {
            connection.createQuery(sql)
                    .addParameter("status", isComplete(find(id)))
                    .addParameter("id", id)
                    .executeUpdate();

        } catch (Sql2oException e) {
            throw new Sql2oException(e);
        }
    }

    private Status isComplete(Todos todo) {
        if (todo.getStatus().equals(Status.ACTIVE))
            return Status.COMPLETE;
        else if (todo.getStatus().equals(Status.COMPLETE))
            return Status.ACTIVE;
        else return null;
    }

    @Override
    public void toggleAll(boolean complete) {
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
