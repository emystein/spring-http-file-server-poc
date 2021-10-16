# Introduction

The application provides HTTP endpoints for downloading files:

* from the local machine
* forwarded from another HTTP server


# Requirements

* JDK 8+
* Gradle 7+


# Configuration

In `src/main/resources/application.properties`:

```
local.source.path=${HOME}/Documents
remote.source.url=http://localhost:8000
```

# Run

```bash
gradle bootRun
```

## Local HTTP Server

Python provides an HTTP server for files in the start directory.

For example, when execute:

```bash
cd /tmp
python -m http.server
```

then opening http://localhost:8000 in a web browser will list files found in the `/tmp` directory.


# API

## Download local files

HTTP GET /files/bytearray/{fileName}

HTTP GET /files/servlet-response/{fileName}


## Download remote files

HTTP GET /proxy/servlet-response/{fileName}

