package com.main.discgolf.service.score;

import com.main.discgolf.model.Hole;
import com.main.discgolf.model.Score;

import java.util.List;

public interface ScoreService {

    List<Score> getAllScores();

    Score getScoreById(Long id);

    Score saveScore(Score score);

    void deleteScoreById(Long id);
}
