# stored procedures in VK https://vk.com/dev/execute
- get user comments to 10 posts
```
var owner_id = Args.owner_id;
var user_id = Args.user_id;
var offset = Args.offset;
var post_count = Args.post_count;

if (post_count == null)
    post_count = 10;

// Получаем список постов
var posts = API.wall.get({
    "owner_id": owner_id,
    "offset": offset,
    "count" : 100,
});

var i = 0;
var userComments = {};

while(i < posts.items.length && i < post_count)
{
    var post_id = posts.items[i].id;
    var comments = API.wall.getComments({
        "owner_id": owner_id,
        "post_id": post_id,

        "count" : 100,
    });

    var j = 0;
    while(j < comments.items.length)
    {
        if (user_id == null || comments.items[j].from_id == user_id)
            userComments.push(comments.items[j]);

        j = j + 1;
    }

    i = i + 1;
}

return userComments;
```

- get group #1
API.groups.get({"count": 1});
API.groups.getById({"group_id": 181704638});
- get wall post #1
API.wall.get({"count": 1});