package pl.piomin.services.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piomin.services.account.model.Student;
import pl.piomin.services.account.repositories.DynamoDbRepository;

@RestController
@RequestMapping("/dynamoDb")
public class DynamoDbController {

	@Autowired
	private DynamoDbRepository repository;

	@PostMapping
	public String insertIntoDynamoDB(@RequestBody Student student) {
		repository.insertIntoDynamoDB(student);
		return "Successfully inserted into DynamoDB table";
	}

	@GetMapping
	public ResponseEntity<Student> getOneStudentDetails(@RequestParam String studentId, @RequestParam String lastName) {
		Student student = repository.getOneStudentDetails(studentId, lastName);
		return new ResponseEntity<Student>(student, HttpStatus.OK);
	}

	@PutMapping
	public void updateStudentDetails(@RequestBody Student student) {
		repository.updateStudentDetails(student);
	}

	@DeleteMapping(value = "{studentId}/{lastName}")
	public void deleteStudentDetails(@PathVariable("studentId") String studentId,
			@PathVariable("lastName") String lastName) {
		Student student = new Student();
		student.setStudentId(studentId);
		student.setLastName(lastName);
		repository.deleteStudentDetails(student);
	}
}
