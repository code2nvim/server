# Server

## Launching

```sh
./mvnw spring-boot:run
```

## Change API

Default value is: `/api`

```
api=${API:/api}
```

For `localhost:8080`, API will be `localhost:8080/api` (of course)


If you wanna change it to `/foo`, set environment variable:

```sh
api=/foo ./mvn spring-boot:run
```
