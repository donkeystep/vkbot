package org.vors.vkbot;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Bot {

    @Value("${bot.group.id}")
    private int GROUP_ID;
    @Value("${bot.group.token}")
    private String GROUP_TOKEN;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) throws ClientException, ApiException {
        GroupActor groupActor = createGroupActor();

        HttpTransportClient httpClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(httpClient);

        vk.groups().setLongPollSettings(groupActor, groupActor.getGroupId())
                .enabled(true)
                .wallPostNew(true)
                .wallRepost(true)
                .wallReplyNew(true)
                .execute();

        CallbackApiLongPollHandler handler = new CallbackApiLongPollHandler(vk, groupActor);
        handler.run();
    }

    private GroupActor createGroupActor() {
        return new GroupActor(GROUP_ID, GROUP_TOKEN);
    }

}
