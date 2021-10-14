# Introduction

The application provides HTTP endpoints for downloading files:

* from the local machine
* forwarded from another HTTP server


# Requirements

* JDK 8+
* Gradle 7+


# Run

```bash
gradle bootRun
```


# API

## Local files

HTTP GET /servlet/files/bytearray/{fileName}

HTTP GET /servlet/files/servlet-response/{fileName}


## Remote files

HTTP GET /servlet/forward/servlet-response/{fileName}

