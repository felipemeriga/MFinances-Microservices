package pl.piomin.services.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piomin.services.account.model.Expense;
import pl.piomin.services.account.repositories.ExpenseRepository;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    ExpenseRepository repository;

    @PostMapping
    public String insertIntoDynamoDB(@RequestBody Expense expense) {
        repository.insertIntoDynamoDB(expense);
        return "Successfully inserted into DynamoDB table";
    }

    @GetMapping
    public ResponseEntity<Expense> getExpenseDetails(@RequestParam String id, @RequestParam String category) {
        Expense expense = repository.getModelDetails(id, category);
        return new ResponseEntity<Expense>(expense, HttpStatus.OK);
    }

    @PutMapping
    public void updateExpenseDetails(@RequestBody Expense expense) {
        repository.updateModel(expense);
    }

    @DeleteMapping(value = "{id}/{category}")
    public void deleteExpenseDetails(@PathVariable("id") String id,
                                     @PathVariable("category") String category) {
        Expense expense = new Expense();
        expense.setId(id);
        expense.setCategory(category);
        repository.deleteModel(expense);
    }
}
