package com.game.rockpaperscissor.repository;

import com.game.rockpaperscissor.models.GameStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameStepRepository extends JpaRepository<GameStep, Long> {
}