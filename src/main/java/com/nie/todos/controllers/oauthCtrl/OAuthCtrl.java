package com.nie.todos.controllers.oauthCtrl;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.Gson;
import com.nie.todos.controllers.todosCtrl.TodosCtrl;
import com.nie.todos.models.factory.DaoServiceFactory;
import com.nie.todos.models.pojos.dtos.userDTO.googleDTO.UserGoogle;
import com.nie.todos.models.pojos.entities.User;
import com.nie.todos.models.repositories.OAuthRepository;
import lombok.Getter;
import spark.Route;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by inna on 29.05.17.
 */
public class OAuthCtrl {

    private static final OAuthRepository oAuthService = DaoServiceFactory.getOAuthService();
    private static final String clientId = "386906918374-0eb02rdmcnhlnpsm5op7ckr5a1d42kg7.apps.googleusercontent.com";
    private static final String clientSecret = "ZsaXmD5CKBNWLBvYhMc0OS6B";
    private static final String secretState = "secret" + new Random().nextInt(999_999);
    private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/plus/v1/people/me";
    private static final String redirectUrl = "http://localhost:8080/auth/todos";
    private static OAuth20Service service;
    private static Gson gson = new Gson();

    @Getter
    private static final Route oauth = (request, response) -> {
        service = new ServiceBuilder()
                .apiKey(clientId)
                .apiSecret(clientSecret)
                .scope("profile") // replace with desired scope
                .state(secretState)
                .callback(redirectUrl)
                .build(GoogleApi20.instance());
        final Map<String, String> additionalParams = new HashMap<>();
        additionalParams.put("access_type", "offline");
        additionalParams.put("prompt", "consent");
        final String authorizationUrl = service.getAuthorizationUrl(additionalParams);

        response.redirect(authorizationUrl);
        return "null";
    };

    @Getter
    private static final Route todos = (request, response) -> {
        final String code = request.queryParams("code");
        OAuth2AccessToken accessToken = service.getAccessToken(code);

        final OAuthRequest requestURL = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, requestURL);
        final Response responseURL = service.execute(requestURL);

        UserGoogle user = gson.fromJson(responseURL.getBody(), UserGoogle.class);

        if (!oAuthService.isExistUserWithToken(user.getEtag())) {
            request.session().attribute("iduser", oAuthService.auth(user, user.getEtag()));
        }else request.session().attribute("iduser", oAuthService.getUserByToken(user.getEtag()).getId());

        return TodosCtrl.renderTodos(request);
    };

}
