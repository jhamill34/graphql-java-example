package lp.web.server.repositories;

import lp.web.server.entities.HeroEntity;

import java.util.List;

public interface HeroRepository {
    List<HeroEntity> getByIds(List<Long> id);
}
