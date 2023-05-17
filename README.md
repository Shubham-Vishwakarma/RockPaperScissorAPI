# Rock Paper Scissors API

[!build](https://github.com/Shubham-Vishwakarma/RockPaperScissorsAPI/actions/workflows/maven.yml/badge.svg)

## Problem Statement
### Rules
* Rock defeats Scissors
* Scissors defeats Paper
* Paper defeats Rock
* +1 on win
* 0 on loose/tie

* GET request to /start should return response as READY and a random token
* GET request to v1/{token}/rock should return response of random server move and total score
* GET request to v2/{token}/rock should server always wins strategically.
* GET request to /{token}/results return JSON response of the game results