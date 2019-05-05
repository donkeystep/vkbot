# links
- vk bot api https://github.com/ekonda/kutana
- vk java sdk https://github.com/VKCOM/vk-java-sdk
    - usage https://vk.com/dev/Java_SDK

# logging
-Dlogging.level.com.vk.api.sdk.httpclient=DEBUG
-Dlogging.level.org.eclipse.jetty=INFO

# features
v get update: new wall post
* send sample message as group
    - community auth https://vk.com/dev/Java_SDK?f=7.2.%20Authorization%20Code%20Flow%20for%20Community
        - community token - acquired https://vk.com/dev/access_token?f=2.%20Community%20Token
    - youtrack bot auth
    - long poll bot https://vk.com/dev/bots_longpoll
    - chat bot api https://vk.com/dev/bots_docs
    - discussion on group auth https://ru.stackoverflow.com/questions/737286/authorization-code-flow-%D0%92%D0%9A%D0%BE%D0%BD%D1%82%D0%B0%D0%BA%D1%82%D0%B5-%D0%B4%D0%BB%D1%8F-%D1%81%D0%B5%D1%80%D0%B2%D0%B5%D1%80%D0%BD%D0%BE%D0%B3%D0%BE-%D0%BF%D1%80%D0%B8%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D1%8F-%D0%BD%D0%B0-java
    v groups.getLongPollServer.
* get replies to specific post https://ru.stackoverflow.com/questions/604524/%D0%9A%D0%B0%D0%BA-%D0%BF%D0%BE%D0%BB%D1%83%D1%87%D0%B8%D1%82%D1%8C-%D0%BA%D0%BE%D0%BC%D0%BC%D0%B5%D0%BD%D1%82%D0%B0%D1%80%D0%B8%D0%B8-%D0%BF%D0%BE%D0%BB%D1%8C%D0%B7%D0%BE%D0%B2%D0%B0%D1%82%D0%B5%D0%BB%D1%8F-%D0%BE%D1%81%D1%82%D0%B0%D0%B2%D0%BB%D0%B5%D0%BD%D0%BD%D1%8B%D0%B5-%D0%B2-%D0%BA%D0%BE%D0%BD%D0%BA%D1%80%D0%B5%D1%82%D0%BD%D0%BE%D0%BC-%D1%81%D0%BE%D0%BE%D0%B1%D1%89%D0%B5%D1%81%D1%82%D0%B2%D0%B5-%D0%B2-vk
    - get 100 replies to specific post https://ru.stackoverflow.com/questions/604524/%D0%9A%D0%B0%D0%BA-%D0%BF%D0%BE%D0%BB%D1%83%D1%87%D0%B8%D1%82%D1%8C-%D0%BA%D0%BE%D0%BC%D0%BC%D0%B5%D0%BD%D1%82%D0%B0%D1%80%D0%B8%D0%B8-%D0%BF%D0%BE%D0%BB%D1%8C%D0%B7%D0%BE%D0%B2%D0%B0%D1%82%D0%B5%D0%BB%D1%8F-%D0%BE%D1%81%D1%82%D0%B0%D0%B2%D0%BB%D0%B5%D0%BD%D0%BD%D1%8B%D0%B5-%D0%B2-%D0%BA%D0%BE%D0%BD%D0%BA%D1%80%D0%B5%D1%82%D0%BD%D0%BE%D0%BC-%D1%81%D0%BE%D0%BE%D0%B1%D1%89%D0%B5%D1%81%D1%82%D0%B2%D0%B5-%D0%B2-vk
    - get 2500 replies by post id https://ru.stackoverflow.com/questions/780304/%D0%9F%D0%BE%D0%BB%D1%83%D1%87%D0%B5%D0%BD%D0%B8%D0%B5-%D0%BA%D0%BE%D0%BC%D0%BC%D0%B5%D0%BD%D1%82%D0%B0%D1%80%D0%B8%D0%B5%D0%B2-%D0%B8-%D1%83%D1%87%D0%B0%D1%81%D1%82%D0%BD%D0%B8%D0%BA%D0%BE%D0%B2-%D1%81%D0%BE%D0%BE%D0%B1%D1%89%D0%B5%D1%81%D1%82%D0%B2%D0%B0-vk-com-%D0%BC%D0%B5%D1%82%D0%BE%D0%B4%D0%BE%D0%BC-execute-vk-api
    - wall.getComments https://vk.com/dev/wall.getComments
    - wall.getReposts https://vk.com/dev/wall.getReposts
    - stored procedures https://vk.com/dev/execute
    - execute Code
        vk.execute().code(groupActor, "return \"Hello World\";").execute();
    - error on get wall
        - ApiRequestException: Invalid request (8): Invalid request: this method is unavailable with group tokens
    - look https://toster.ru/q/504919

- Пока основной вопрос - посчитать сообщения в 999 флудилках , в которых есть страница одна и та же
  В ВК апи ограничен доступ к категории *сообщения* для страниц и получить доступ можно только с помощью групп