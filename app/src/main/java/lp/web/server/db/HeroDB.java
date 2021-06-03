package lp.web.server.db;

import com.google.inject.Singleton;
import lp.web.server.entities.HeroEntity;
import lp.web.server.repositories.HeroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class HeroDB implements HeroRepository {
    private static final Logger logger = LoggerFactory.getLogger(HeroDB.class);

    private static final List<HeroEntity> HEROS = Stream.of(
            new HeroEntity(1L, "R2D2"),
            new HeroEntity(2L, "Han Solo"),
            new HeroEntity(3L, "Luke Skywalker"),
            new HeroEntity(4L, "Leia Organa"),
            new HeroEntity(5L, "C-3P0")
    ).collect(Collectors.toList());

    @Override
    public List<HeroEntity> getByIds(List<Long> ids) {
        logger.info("SELECT * FROM heros WHERE id in (" + ids + ")");
        return HEROS.stream()
                .filter(hero -> ids.contains(hero.getId()))
                .collect(Collectors.toList());
    }
}
