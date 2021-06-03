package lp.web.server.services;

import lp.web.server.entities.HeroEntity;

import java.util.List;

public interface HeroService {
    HeroEntity get(Long id);
    List<HeroEntity> getMany(List<Long> ids);
}
