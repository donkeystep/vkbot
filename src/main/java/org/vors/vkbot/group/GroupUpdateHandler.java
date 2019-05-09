package org.vors.vkbot.group;

import com.vk.api.sdk.callback.longpoll.CallbackApiLongPoll;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;


/**
 * Gets group updates.
 */
public class GroupUpdateHandler extends CallbackApiLongPoll {

    private static final Logger LOG = LoggerFactory.getLogger(GroupUpdateHandler.class);

    private Map<Integer, Integer> messageCounts = new HashMap<>();

    @Autowired
    private GroupActor groupActor;

    public GroupUpdateHandler(VkApiClient client, GroupActor actor) {
        super(client, actor);
    }

    public void messageNew(Integer groupId, Message message) {
        LOG.info("messageNew: " + message.toString());

        countAndReply(message);
    }

    private void countAndReply(Message message) {
        Integer peerId = message.getPeerId();
        Integer messageCount = increaseMessageCount(peerId);

        try {
            getClient().messages().send(groupActor)
                    .randomId(new Random().nextInt(10000))
                    .message("count = " + messageCount)
                    .peerId(peerId)
                    .execute();
        } catch (ApiException | ClientException e) {
            LOG.error("Can't send count message", e);
        }
    }

    private Integer increaseMessageCount(Integer peerId) {
        Integer messageCount = Optional.ofNullable(messageCounts.get(peerId)).orElse(0);
        messageCount++;
        messageCounts.put(peerId, messageCount);
        return messageCount;
    }

}
