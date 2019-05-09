package org.vors.vkbot.user.oauth;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.vors.vkbot.VkbotApp;

@Component
public class UserAuthEndpointInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(UserAuthEndpointInitializer.class);

    @Value("${client.id}")
    private int clientId;

    @Value("${client.secret}")
    private String clientSecret;

    @Value("${server.host}")
    private String host;

    @Value("${server.port}")
    private int port;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) throws Exception {
        initUserAuthServer();
    }

    private void initUserAuthServer() throws Exception {
        HandlerCollection handlers = new HandlerCollection();

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        resourceHandler.setResourceBase(VkbotApp.class.getResource("/static").getPath());

        VkApiClient vk = new VkApiClient(new HttpTransportClient());
        handlers.setHandlers(new Handler[]{resourceHandler, new RequestHandler(vk, clientId, clientSecret, host, port)});

        Server server = new Server(port);
        server.setHandler(handlers);

        server.start();
        server.join();
    }

}
