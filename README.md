## GRPC Test

1. Gradle Multi project with following sub-projects:
    * Interface Definition Language (IDL).
    * Server application.
    * Client application.

## Build and Install Interface Definition Language

```bash
$ cd idl
$ ../gradlew build install
```

## Run the server

```bash
$ cd server
$ ../gradle run
```