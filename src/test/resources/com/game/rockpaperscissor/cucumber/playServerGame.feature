Feature: Server Always Wins the Game

  Scenario: Server will always win the game
    Given a new game
    When user plays "rock"
    And user plays "rock"
    And user plays "rock"
    Then server wins the game