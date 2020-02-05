package pl.piomin.services.account.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.piomin.services.account.model.Expense;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ExpenseRepository extends GeneralRepository<Expense, String, String>{

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseRepository.class);

    @Autowired
    private DynamoDBMapper mapper;

    @Override
    public Expense getModelDetails(String id, String category) {
        return mapper.load(Expense.class, id, category);
    }

    @Override
    public DynamoDBSaveExpression buildDynamoDBSaveExpression(Expense expense) {
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expected = new HashMap<>();
        expected.put("id", new ExpectedAttributeValue(new AttributeValue(expense.getId()))
                .withComparisonOperator(ComparisonOperator.EQ));
        saveExpression.setExpected(expected);
        return saveExpression;
    }
}
