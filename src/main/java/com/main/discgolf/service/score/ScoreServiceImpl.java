package com.main.discgolf.service.score;

import com.main.discgolf.model.Round;
import com.main.discgolf.model.Score;
import com.main.discgolf.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScoreServiceImpl implements ScoreService{

    @Autowired
    private ScoreRepository scoreRepository;


    @Override
    public List<Score> getAllScores() {
        return (List<Score>) this.scoreRepository.findAll();
    }

    @Override
    public Score getScoreById(Long id) {
        Optional<Score> optional = scoreRepository.findById(id);
        Score score;
        if (optional.isPresent()) {
            score = optional.get();
        } else {
            throw new RuntimeException("Score not found for id :: " + id);
        }
        return score;
    }

    @Override
    public Score saveScore(Score score) {
        return this.scoreRepository.save(score);
    }

    @Override
    public void deleteScoreById(Long id) {
        this.scoreRepository.deleteById(id);
    }
}
