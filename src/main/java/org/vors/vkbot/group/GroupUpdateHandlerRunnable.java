package org.vors.vkbot.group;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroupUpdateHandlerRunnable implements Runnable {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private GroupUpdateHandler groupUpdateHandler;

    public GroupUpdateHandlerRunnable(GroupUpdateHandler groupUpdateHandler) {
        this.groupUpdateHandler = groupUpdateHandler;
    }

    @Override
    public void run() {
        try {
            groupUpdateHandler.run();
        } catch (ClientException | ApiException e) {
            LOG.error("Starting group update handler failed!", e);
        }
    }

}
