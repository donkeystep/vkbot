package org.vors.vkbot;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.groups.GroupsGetLongPollServerQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.vors.vkbot.Constants.COMMUNITY_ID;
import static org.vors.vkbot.Constants.COMMUNITY_TOKEN;

@SpringBootApplication
public class VkbotApplication {

    public static void main(String[] args) throws ClientException, ApiException {
        final Logger LOG = LoggerFactory.getLogger(HttpTransportClient.class);


        SpringApplication.run(VkbotApplication.class, args);

        GroupActor groupActor = createGroupActor();

        HttpTransportClient httpClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(httpClient);

//        GroupsGetLongPollServerQuery longPollServerQuery = vk.groups().getLongPollServer(groupActor, COMMUNITY_ID);
//        longPollServerQuery.execute();
//		if (!vk.groups().getLongPollSettings(groupActor, groupActor.getGroupId()).execute().getIsEnabled()) {
        vk.groups().setLongPollSettings(groupActor, groupActor.getGroupId())
                .enabled(true)
                .wallPostNew(true)
                .execute();
//    }
        vk.groups().getLongPollSettings(groupActor, groupActor.getGroupId()).execute();

        CallbackApiLongPollHandler handler = new CallbackApiLongPollHandler(vk, groupActor);
        handler.run();
    }

    private static GroupActor createGroupActor() {
        return new GroupActor(COMMUNITY_ID, COMMUNITY_TOKEN);
    }

}
