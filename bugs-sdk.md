Same error as in #82 , but operation is wallReplyNew, not wallPostNew
Regression of
To reproduce, run com.vk.api.examples.group.bot.Application with group ID & token and reply to wall post.
```
Exception in thread "main" java.lang.IllegalArgumentException: class com.vk.api.sdk.callback.objects.wall.CallbackWallComment declares multiple JSON fields named post_id
	at com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.getBoundFields(ReflectiveTypeAdapterFactory.java:172)
	at com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.create(ReflectiveTypeAdapterFactory.java:102)
	at com.google.gson.Gson.getAdapter(Gson.java:458)
	at com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.createBoundField(ReflectiveTypeAdapterFactory.java:117)
	at com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.getBoundFields(ReflectiveTypeAdapterFactory.java:166)
	at com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.create(ReflectiveTypeAdapterFactory.java:102)
	at com.google.gson.Gson.getAdapter(Gson.java:458)
	at com.google.gson.Gson.fromJson(Gson.java:926)
	at com.google.gson.Gson.fromJson(Gson.java:994)
	at com.vk.api.sdk.callback.CallbackApi.parse(CallbackApi.java:476)
	at com.vk.api.sdk.callback.longpoll.CallbackApiLongPoll.run(CallbackApiLongPoll.java:66)
	at com.vk.api.examples.group.bot.Application.main(Application.java:32)
```