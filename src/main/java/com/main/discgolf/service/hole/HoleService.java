package com.main.discgolf.service.hole;


import com.main.discgolf.model.Hole;

import java.util.List;

public interface HoleService {
    List<Hole> getAllHoles();

    List<Hole> getAllHolesByCourseId(Long id);

    Hole getHoleById(Long id);

    Hole saveHole(Hole hole);

    void deleteHoleById(Long id);
}
