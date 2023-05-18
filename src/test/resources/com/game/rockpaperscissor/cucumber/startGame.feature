Feature: A New Game Is Created
    Scenario: A new game is created successfully
    When the request is sent to url "/game/start"
    Then the response status should be 201
    And a new game is created
