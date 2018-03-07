[![][jwt img]][jwt]

A simple JWT spike with Undertow and Resteasy

[jwt]:jwt
[jwt img]:https://github.com/ParosSrl/jwt-spike/blob/master/logo_jwt.png

Once the webapp has been started, it will reply at http://localhost:8080/api/jwt

LOGIN: (It will reply with the jwt token)
```
curl -d "login=foo&password=bar" -X POST http://localhost:8080/api/jwt/login
```

USE IT: In order to make a call with the authorization header you can run this command:
```
curl -H "Authorization: Bearer <<TOKEN>>" -X GET http://localhost:8080/api/jwt
```
