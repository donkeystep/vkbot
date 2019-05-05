package org.vors.vkbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class VkbotApplication {
    private static final Logger LOG = LoggerFactory.getLogger(VkbotApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(VkbotApplication.class, args);
    }
}
