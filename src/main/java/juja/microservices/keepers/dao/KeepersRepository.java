package juja.microservices.keepers.dao;

import juja.microservices.keepers.entity.Keeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vadim Dyachenko
 */
@Repository
public class KeepersRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private MongoTemplate mongoTemplate;

    public List<String> getDirections(String uuid) {
        logger.debug(LocalDateTime.now() + " Invoke of KeepersRepository.getDirections()");

        List<Keeper> queryResult = mongoTemplate.find(new Query(
                Criteria.where("uuid").is(uuid).and("isActive").is(true)), Keeper.class);

        List<String> result = new ArrayList<>();
        for (Keeper item : queryResult) {
            result.add(item.getDirection());
        }

        logger.info("Number of returned keepers directions is ", result.size());
        logger.debug(LocalDateTime.now() + "Request for active directions for keeper with uuid " + uuid +
                " returned: " + result.toString());
        return result;
    }
}
