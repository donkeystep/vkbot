package org.vors.vkbot.longpoll;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GroupLongPollInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(GroupLongPollInitializer.class);

    @Value("${bot.group.id}")
    private int GROUP_ID;
    @Value("${bot.group.token}")
    private String GROUP_TOKEN;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) throws Exception {
        GroupActor groupActor = createGroupActor();

        VkApiClient vk = initVkApiClient(groupActor);

        CallbackApiLongPollHandler handler = new CallbackApiLongPollHandler(vk, groupActor);
        handler.run();
    }

    private VkApiClient initVkApiClient(GroupActor groupActor) throws ApiException, ClientException {
        HttpTransportClient httpClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(httpClient);

        return vk;
    }

    private GroupActor createGroupActor() {
        return new GroupActor(GROUP_ID, GROUP_TOKEN);
    }

}
