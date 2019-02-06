package com.pay.restapp.resources;


import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/todos")
public class TodosResource {

	// Requesting Spring to provide us a reference to the repository object.
	// TodoRepository.java enables the creation of the repository object.
	// We will use the repository object to access the H2 database that comes
	// with SpringBoot.
	@Autowired
	private TodoRepository todoRepository;

	// This is a bonus example you can use to configure the rest of the APIs
	// you need to work on, given below.
	// To test: http://localhost:7070/paypal/restapp/todos
	//   HTTP method: GET
    //   Accept:  application/json
	@RequestMapping(method = RequestMethod.GET, produces = { "application/json", "application/xml"  })
	public List<Todo> retrieveAllTodos() {
		return todoRepository.findAll();
	}
	
	//  Use the @RequestMapping annotation:
	//       Define method for a GET request
	//  	 Define the path so that the todo id can be passed as parameter in the URL
	//       Define the attribute so that the Todo returned by
	//         this method is converted to either JSON or XML, 
	//         depending on client request. 
	// To test: http://localhost:7070/paypal/restapp/todos/1
	//   HTTP method: GET
    //   Accept:  application/json   or application/xml
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Todo retrieveTodo(@PathVariable long id) {
		// Retrieve a row from the todo table in the H2 database
		// using the id passed by client via URL. Replace the null value below by a call
		// to the TodoRepository class.
		Optional<Todo> todo = todoRepository.findById(id);
		return todo.get();
	}
	
	//  Use the @RequestMapping annotation:
	// 		 Define method for a POST request
	// 		 Define the attribute so that the client 
	//		 passes a JSON object in the body of the POST request
	// To test: http://localhost:7070/paypal/restapp/todos
	// HTTP method: POST
	// Body:  {"title":"To Kill a Mockingbird", "status":"OUT", "dueDate":"2018-11-15", "comment":"on-line checkout", "assignee":"Katie Crick"}
	//         Note that id will be auto-generated 
	//            in the Todos database table
	// Accept: application/json
	@RequestMapping(method = RequestMethod.POST)
	public Todo createTodo(@RequestBody Todo todo) {
		// Create a row in the todo table in the H2 database
		// using the Todo object passed by client. Replace the null value below by a call
		// to the TodoRepository class.
		Todo savedTodo = todoRepository.save(todo);
		return savedTodo;
	}
	
	//  Use the @RequestMapping annotation:
	//       Define method for a DELETE request
	//  	 Define the path so that the todo id can be passed as parameter in the URL
	// To test: http://localhost:7070/paypal/restapp/todos/<id>
	//    Note: Make sure the id you pass exists in the Todo table
	// HTTP method: DELETE
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String deleteTodo(@PathVariable long id) {
		// Delete a row from the todo table in the H2 database
		// using the id passed by client via URL. Do this by calling the corresponding
		// method in the TodoRepository class.
		return "row " + id + " deleted";
	}
	
	//  Use the @RequestMapping annotation:
	//       Define method for a PUT request
	//  	 Define the path so that the todo id can be passed as parameter in the URL
	// 		 Define the attribute so that the client 
	//		 passes a JSON object in the body of the POST request
	// To test: http://localhost:7070/paypal/restapp/todos
	// HTTP method: PUT
	// Body:  {"id":6,"title":"To Kill a Mockingbird","status":"OUT","dueDate":"2018-10-22","comment":"on-line checkout","assignee":"Robin Hood"}
	//        This assumes there is a row in the Todos database table
	//        with id = 6
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateTodo(@RequestBody Todo todo, @PathVariable long id) {
		// Retrieve a row from the todo table in the H2 database
		// using the id passed by client via URL. Replace the null value below by a call
		// to the TodoRepository class.
		
		Optional<Todo> todoOptional = todoRepository.findById(id);

		if (!todoOptional.isPresent())
			return ResponseEntity.notFound().build();

		todo.setId(id);
		
		todoRepository.save(todo);

		return ResponseEntity.noContent().build();
	}
	
	  @RequestMapping(value = "/getDues/{dueDate}", method = RequestMethod.GET,produces = { "application/json", "application/xml" })
	  public List<Todo> retrieveDueDateTodos(@PathVariable Date dueDate) {
		 return todoRepository.findbyDueDateLessThan(dueDate);
	  }
}
