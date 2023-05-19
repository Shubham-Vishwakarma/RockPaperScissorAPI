Feature: Create Game Of Different Level
    A new game is created based on input level provided

    Scenario Outline: A new game is created of defined level
        When the level is passed as "<level>"
        Then a new game of "<level>" level is created
        Examples:
            | level |
            | easy  |
            | medium |
            | hard   |
