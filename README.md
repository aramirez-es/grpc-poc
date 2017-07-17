## GRPC Proof of Concept

1. Gradle Multi project with following sub-projects:
    * Server application + Interface Definition Language (IDL).
    * Client application.

## Run the server

```bash
$ cd server
$ ../gradlew run
```

## Run the client

```bash
$ cd client
$ ../gradlew run
```

## Run tests

```bash
$ ./gradlew test
```