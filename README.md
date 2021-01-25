# todo-spring-boot

## Dev

### Mysql

```
docker run --name mysql -p3306:3306 -e MYSQL_ROOT_PASSWORD=rockmyroot -e MYSQL_USER=todouser -e MYSQL_PASSWORD=todopass -e MYSQL_DATABASE=todo_list -v E:\Workspace\java\todo-spring-boot\data\mysql:/var/lib/mysql -d mysql:latest --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
```

### PhpMyAdmin

```
docker run --name myadmin -d --link mysql:db -p 8888:80 phpmyadmin
```

### Docker window

shutdown "vmmem" :

```
wsl --shutdown
```