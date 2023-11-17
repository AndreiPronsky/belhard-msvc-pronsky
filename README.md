# Microservices introduction course
### Student: Andrei Pronsky

## Overview
Sample project with basic interacting services developed in order to provide a sandbox for applying specific tools according to the course program.

Project consists of resource-service and songs-service. Databases for each service are located in docker containers. Interaction between services is performed via web-client.

## Deploy

Ports to be exposed
* '7000' - song-service
* '7001' - docker container with songs DB
* '7002' - resource-service
* '7003' - docker container with resources DB

To start song-service:
````
docker run --name songs-db -e POSTGRES_PASSWORD=root -e POSTGRES_DB=songs -d -p 7001:5432 postgres

gradlew song-service:bootRun
````
To start resource-service:
````
docker run --name resources-db -e POSTGRES_PASSWORD=root -e POSTGRES_DB=resources -d -p 7003:5432 postgres

gradlew resource-service:bootRun
````

## API

### Resource-service

POST /resources 

Upload new resource.

Request
* Body example: Audio data binary
* Description: Content type - audio/mpeg
* Restriction: MP3 audio data

Response
* Body: { "id":11 } 
* Description: integer id - ID of the created resource
* Codes:
    
  200 - OK
  
  400 - Request body is invalid MP3 
  
  500 - Internal server error


GET /resources/{id} 

Get the binary audio data of a resource.

Request 
* Parameter: integer id
* Description: ID of the resource to get
* Restriction: ID of an existing resource

Response
* Body: audio bytes
* Codes:

    200 - OK

    404 - Resource with such id doesn't exist

    500 - Internal server error


DELETE /resources?id=1,2

Delete resources by id. Ignore not existing resources.

Request
* Parameter: String id
* Description: Comma separated values of resource IDs to remove

Response
* Body: { "ids": [1,2] }
* Description: Integer [] ids - IDs of the deleted resources
* Codes:

    200 - OK
    
    500 - Internal server error

### Song-service

POST /songs

Create a new song metadata record in the database.

Request
* Body example:  
{  
"name": "We are the champions",  
"artist": "Queen",  
"album": "News of the world",  
"length": "2:59",  
"resourceId": "123",  
"year": "1977"   
}


* Description: Song metadata record, referencing to resource id (mp3 file itself)
* Restriction: MP3 audio data

Response
* Body: { "id":11 }
* Description: integer id - ID of the created song metadata
* Codes:

  200 - OK

  400 - Song metadata missing validation error

  500 - Internal server error


GET /songs/{id}

Get song metadata.

Request
* Parameter: integer id
* Description: ID of song metadata to get
* Restriction: ID of an existing song metadata

Response
* Body:   
  {  
  "name": "We are the champions",  
  "artist": "Queen",  
  "album": "News of the world",  
  "length": "2:59",  
  "resourceId": "123",  
  "year": "1977"   
  }


* Codes:

  200 - OK

  404 - Song metadata with such id doesn't exist

  500 - Internal server error


DELETE /songs?id=1,2

Delete song metadata by id. Ignore not existing song metadata.

Request
* Parameter: String id
* Description: Comma separated values of song metadata IDs to remove

Response
* Body: { "ids": [1,2] }
* Description: Integer [] ids - IDs of the deleted song metadata
* Codes:

  200 - OK

  500 - Internal server error