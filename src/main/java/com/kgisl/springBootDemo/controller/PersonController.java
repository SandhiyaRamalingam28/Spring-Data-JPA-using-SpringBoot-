package com.kgisl.springBootDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.kgisl.springBootDemo.model.Person;
import com.kgisl.springBootDemo.repository.PersonRepository;


@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @GetMapping("/person")
    public @ResponseBody ResponseEntity<List<Person>> getAll() {
        // Pageable pageable = PageRequest.of(0, 5,Sort.by("age").descending());
        // Page<Person> personPage = personRepository.findAll(pageable);

        
        return new ResponseEntity<>(personRepository.findAll(), HttpStatus.OK);
    }

    

    @GetMapping(value = "/person/{id}")
    public ResponseEntity<Person> getTeamById(@PathVariable("id") int id) {
        Person user = personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found"));
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/personByEmail/{email}")

	public  ResponseEntity<?> findByEmail(@PathVariable("email") String email) {
		try {
			Person user = personRepository.findByEmail(email);

			if (user == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @PostMapping(value = "/person")
    public ResponseEntity<Person> createTeam(@RequestBody Person user) {
        Person actualUser = personRepository.save(user);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(actualUser, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/person/{id}", headers = "Accept=application/json")
    public ResponseEntity<Person> updateUser(@PathVariable("id") int id, @RequestBody Person currentUser) {
        Person user = personRepository.save(currentUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/person/{id}", headers = "Accept=application/json")
    public ResponseEntity<Person> deleteUser(@PathVariable("id") int id) {
        personRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/queryName/{name}")
    public ResponseEntity<?> findByName(@PathVariable("name") String name){
        return new ResponseEntity<>(personRepository.findByName(name), HttpStatus.OK);
    }

    @GetMapping(value="/findByNameCount/{name}")
    public ResponseEntity<?> findByNameCount(@PathVariable("name") String name){
      return new ResponseEntity<>(personRepository.findByNameCount(name),HttpStatus.OK);

    }

    @GetMapping(value="/findByCount/")
    public ResponseEntity<?> findByCount(){
      return new ResponseEntity<>(personRepository.findByCount(),HttpStatus.OK);

    }

    // only sorting
    @GetMapping("/person/{field}")
    public ResponseEntity<List<Person>> getSortedBy(@PathVariable String field){
        List<Person> getSize = personRepository.findAll(Sort.by(Sort.Direction.DESC,field));
        return new ResponseEntity<>(getSize, HttpStatus.OK);
    } 

    //only pagination
    @GetMapping("/person/pagination/{offSet}/{pageSize}")
    public ResponseEntity<Page<Person>> getDetailsWithPagination(@PathVariable int offSet,@PathVariable int pageSize){
        Page<Person> person = personRepository.findAll(PageRequest.of(offSet, pageSize));
        return new ResponseEntity<>(person,HttpStatus.ACCEPTED);
        
    }
    // sorting and pagination 
    @GetMapping("/person/PaginationAndSorting/{offSet}/{pageSize}/{field}")
    public ResponseEntity<Page<Person>> getDetailsWithPaginationAndSorting(@PathVariable int offSet,@PathVariable int pageSize,@PathVariable String field){
        Page<Person> person = personRepository.findAll(PageRequest.of(offSet, pageSize).withSort(Sort.by(Sort.Direction.DESC,field)));
        return new ResponseEntity<>(person,HttpStatus.OK);
        
    }


}
