package org.vors.vkbot.user.oauth;

import com.google.gson.JsonElement;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RequestHandler extends AbstractHandler {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private final String clientSecret;
    private final int clientId;
    private final String host;
    private final int port;
    private final VkApiClient vk;

    public RequestHandler(VkApiClient vk, int clientId, String clientSecret, String host, int port) {
        this.vk = vk;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.host = host;
        this.port = port;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        switch (target) {
            case "/info":
                try {
                    callUserApi(baseRequest, response);

                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                    response.getWriter().println("error");
                    response.setContentType("text/html;charset=utf-8");
                    e.printStackTrace();
                }

                baseRequest.setHandled(true);
                break;

            case "/callback":
                try {
                    UserAuthResponse authResponse = vk.oAuth().userAuthorizationCodeFlow(clientId, clientSecret, getRedirectUri(), baseRequest.getParameter("code")).execute();

                    response.sendRedirect("/info?token=" + authResponse.getAccessToken() + "&user=" + authResponse.getUserId());
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                    response.getWriter().println("error");
                    response.setContentType("text/html;charset=utf-8");
                    e.printStackTrace();
                }

                baseRequest.setHandled(true);
                break;

            case "/login":
                response.sendRedirect(getOAuthUrl());
                baseRequest.setHandled(true);
                break;
        }
    }

    private void callUserApi(Request baseRequest, HttpServletResponse response) throws com.vk.api.sdk.exceptions.ApiException, com.vk.api.sdk.exceptions.ClientException, IOException {
        UserActor actor = new UserActor(Integer.parseInt(baseRequest.getParameter("user")), baseRequest.getParameter("token"));

        String code = "return API.wall.get({\"count\": 1});";
        JsonElement execResult = executeCode(actor, vk, code);
        LOG.info("Executed user code: {}, \n{}", code, execResult);


        List<UserXtrCounters> getUsersResponse = vk.users().get(actor).userIds(baseRequest.getParameter("user")).execute();
        UserXtrCounters user = getUsersResponse.get(0);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        System.out.println(getInfoPage(user));
    }


    private JsonElement executeCode(UserActor actor, VkApiClient vk, String code) throws ApiException, ClientException {
        return vk.execute().code(actor, code)
                .unsafeParam("user_id", 19382170)
                .execute();
    }

    private String getOAuthUrl() {
        return "https://oauth.vk.com/authorize?client_id=" + clientId + "&display=page&redirect_uri=" + getRedirectUri() + "&scope=groups&response_type=code";
    }

    private String getRedirectUri() {
        return "http://localhost:8080/callback";
//        return "https://oauth.vk.com/blank.html";
    }

    private String getInfoPage(UserXtrCounters user) {
        return "Hello <a href='https://vk.com/id" + user.getId() + "'>" + user.getFirstName() + "</a>";
    }
}
