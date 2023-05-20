Feature: User plays a invalid game

  A invalid game is provided, user is trying to play the game
  with different moves

  Scenario: User plays a non existing game
    When a invalid game token is provided
    Then user is unable to play "rock" with errorMessage "No Game Found"

  Scenario: User plays an ended game
    When an ended game token is provided
    Then user is unable to play "paper" with errorMessage "Game Over"
