package pl.piomin.services.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piomin.services.account.model.Planning;
import pl.piomin.services.account.repositories.PlanningRepository;

@RestController
@RequestMapping("/planning")
public class PlanningController {

    @Autowired
    PlanningRepository repository;

    @PostMapping
    public String insertIntoDynamoDB(@RequestBody Planning planning) {
        repository.insertIntoDynamoDB(planning);
        return "Successfully inserted into DynamoDB table";
    }

    @GetMapping
    public ResponseEntity<Planning> getPlanningDetails(@RequestParam String id, @RequestParam String category) {
        Planning planning = repository.getModelDetails(id, category);
        return new ResponseEntity<Planning>(planning, HttpStatus.OK);
    }

    @PutMapping
    public void updatePlanningDetails(@RequestBody Planning planning) {
        repository.updateModel(planning);
    }

    @DeleteMapping(value = "{id}/{category}")
    public void deletePlanningDetails(@PathVariable("id") String id,
                                     @PathVariable("category") String category) {
        Planning planning = new Planning();
        planning.setId(id);
        planning.setCategory(category);
        repository.deleteModel(planning);
    }
}
