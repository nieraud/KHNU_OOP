package com.nie.todos.models.services;

import com.nie.todos.models.factory.AbstractDaoFactory;
import com.nie.todos.models.pojos.dtos.userDTO.googleDTO.UserGoogle;
import com.nie.todos.models.pojos.entities.User;
import com.nie.todos.models.repositories.OAuthRepository;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.UUID;

/**
 * Created by inna on 29.05.17.
 */
public class OAuthDaoService extends AbstractDaoService implements OAuthRepository {
    public OAuthDaoService(AbstractDaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public String auth(UserGoogle user, String token) {
        String sql = "INSERT INTO users VALUES(:id, :fname, :lname, :imageurl, :accesstoken, DEFAULT);";
        String id = UUID.randomUUID().toString();
        try (Connection connection = daoFactory.getDataSource().open()) {
            connection.createQuery(sql)
                    .addParameter("id",id)
                    .addParameter("fname", user.getName().getGivenName())
                    .addParameter("lname", user.getName().getFamilyName())
                    .addParameter("imageurl", user.getImage().getUrl())
                    .addParameter("accesstoken", token)
                    .executeUpdate();
            return id;
        } catch (Sql2oException e) {
            throw new Sql2oException(e);
        }
    }

    @Override
    public User getUserByToken(String token) {
        String sql = "SELECT * FROM users WHERE accesstoken = :token;";

        try (Connection connection = daoFactory.getDataSource().open()) {
            return connection.createQuery(sql)
                    .addParameter("token", token)
                    .executeAndFetchFirst(User.class);
        } catch (Sql2oException e) {
            throw new Sql2oException(e);
        }
    }

    @Override
    public boolean isExistUserWithToken(String token) {
        return getUserByToken(token) != null;
    }
}
