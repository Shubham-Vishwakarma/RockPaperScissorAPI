# Rock Paper Scissors API

## Vocera Software Hiring Challenge

## Problem Statement
### Rules
* Rock defeats Scissors
* Scissors defeats Paper
* Paper defeats Rock
* +1 on win
* 0 on loose/tie

### Requirement
* GET request to /start should return response as READY and a random token
* GET request to v1/{token}/rock should return response of random server move and total score
* GET request to v2/{token}/rock should server always wins strategically.
* GET request to /{token}/results return JSON response of the game results

### Setup Database

database.sql can be found in database folder

```
mysql> use vocera_game;
mysql> source database.sql;
```

### Run the JAR file
Download the rockpaperscissors.jar file from jar folder
```
java -jar rockpaperscissors.jar
```
