Feature: User plays a valid game

  A valid game is provided, user is playing the game
  with different valid move

  Scenario: User plays a easy level game
    Given a "easy" level new game
    When user plays "rock"
    And user plays "paper"
    And user plays "scissor"
    Then "user" wins the game

  Scenario: User plays a medium level game
    Given a "medium" level new game
    When user plays "rock"
    And user plays "paper"
    And user plays "scissor"
    Then winner is unpredictable

  Scenario: User plays a hard level game
    Given a "hard" level new game
    When user plays "rock"
    And user plays "paper"
    And user plays "scissor"
    Then "server" wins the game