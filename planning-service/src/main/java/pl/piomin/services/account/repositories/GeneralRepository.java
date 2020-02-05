package pl.piomin.services.account.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class GeneralRepository<T, HashKey, RangeKey> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralRepository.class);

    @Autowired
    private DynamoDBMapper mapper;

    public void insertIntoDynamoDB(T t) {
        mapper.save(t);
    }

    public  abstract T getModelDetails(HashKey hashKey, RangeKey rangeKey);

    public void updateModel(T t) {
        try {
            mapper.save(t, buildDynamoDBSaveExpression(t));
        } catch (ConditionalCheckFailedException exception) {
            LOGGER.error("invalid data - " + exception.getMessage());
        }
    }

    public void deleteModel(T t) {
        mapper.delete(t);
    }

    public abstract DynamoDBSaveExpression buildDynamoDBSaveExpression(T t);

}
