# Introduction

This application provides HTTP endpoints for downloading files from:

* The machine running the application
* A remote HTTP server


# Requirements

* JDK 8+
* Gradle 7+


# Configuration

The configuration file is `src/main/resources/application.properties`

## Parameters

Local directory which files are exposed for download:
```
local.source.path=${HOME}/Documents
```

Proxied HTTP File Server:
```
remote.source.url=http://localhost:8000
```


# Run

## This application

```bash
gradle bootRun
```

## Demo remote HTTP File Server

Python provides an HTTP server that serves files from the directory where the server is started from.

The following example starts a server and gives access to files under the /tmp directory:

```bash
cd /tmp
python -m http.server
```

Then opening http://localhost:8000 in a web browser will list the `/tmp` directory.


# API

## Download files from the machine running the application

HTTP GET /local/response-entity/{fileName}

HTTP GET /local/servlet-response/{fileName}


## Download files from a proxied server

HTTP GET /proxy/servlet-response/{fileName}


# See Also

https://spring.io/guides/gs/testing-web/
