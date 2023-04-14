package com.main.discgolf.service.hole;

import com.main.discgolf.model.Course;
import com.main.discgolf.model.Hole;
import com.main.discgolf.repository.HoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class HoleServiceImpl implements HoleService {

    @Autowired
    private HoleRepository holeRepository;


    @Override
    public List<Hole> getAllHoles() {
        return (List<Hole>) this.holeRepository.findAll();
    }

    @Override
    public List<Hole> getAllHolesByCourseId(Long id) {
        return this.holeRepository.findAllHolesByCourseId(id);
    }

    @Override
    public Hole getHoleById(Long id) {
        Optional<Hole> optional = holeRepository.findById(id);
        Hole hole;
        if (optional.isPresent()) {
            hole = optional.get();
        } else {
            throw new RuntimeException("Hole not found for id :: " + id);
        }
        return hole;
    }

    @Override
    public Hole saveHole(Hole hole) {
        return this.holeRepository.save(hole);
    }

    @Override
    public void deleteHoleById(Long id) {
        this.holeRepository.deleteById(id);
    }
}
