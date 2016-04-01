# spray-slick-polymer

Example project with Spray + Slick + Polymer.

## Install

Run the DB migrations:

    cd server
    sbt
    > liquibase-update

## Run

Server:

    cd server
    sbt
    > re-start

Client:

    cd client
    gulp serve

The application is available at http://localhost:5000/.
