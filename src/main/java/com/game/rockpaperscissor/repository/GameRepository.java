package com.game.rockpaperscissor.repository;

import com.game.rockpaperscissor.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, String> {
}