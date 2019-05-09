package org.vors.vkbot.group;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GroupInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(GroupInitializer.class);

    @Value("${bot.group.id}")
    private int GROUP_ID;
    @Value("${bot.group.token}")
    private String GROUP_TOKEN;

    @Autowired
    private VkApiClient vk;
    @Autowired
    private GroupActor groupActor;
    @Autowired
    private GroupUpdateHandler groupUpdateHandler;


    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        runGroupUpdateHandler();
    }

    private void runGroupUpdateHandler() {
        Runnable groupUpdateHandlerRunner = new GroupUpdateHandlerRunnable(groupUpdateHandler);
        Thread t = new Thread(groupUpdateHandlerRunner);
        t.start();
    }

    @Bean
    private GroupUpdateHandler createGroupUpdateHandler() {
        return new GroupUpdateHandler(vk, groupActor);
    }


    @Bean
    private VkApiClient createVkApiClient() {
        HttpTransportClient httpClient = HttpTransportClient.getInstance();
        return new VkApiClient(httpClient);
    }

    @Bean
    private GroupActor createGroupActor() {
        return new GroupActor(GROUP_ID, GROUP_TOKEN);
    }

}
