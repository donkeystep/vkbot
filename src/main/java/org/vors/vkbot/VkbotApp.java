package org.vors.vkbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class VkbotApp {
    private static final Logger LOG = LoggerFactory.getLogger(VkbotApp.class);

    public static void main(String[] args) {
        SpringApplication.run(VkbotApp.class, args);
    }
}
