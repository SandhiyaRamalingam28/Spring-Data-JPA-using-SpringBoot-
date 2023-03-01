package com.kgisl.springBootDemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kgisl.springBootDemo.model.Person;


@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>{

    Person findByEmail(String email);
    
    @Query("select c from Person c where c.name= :name")
    List<Person> findByName(@Param("name")String name);


    @Query("SELECT COUNT(u) FROM Person u WHERE u.name= :name")
    Long findByNameCount(@Param("name")String name);

    @Query("SELECT COUNT(*) FROM Person ")
    Long findByCount();


    

   


  
}
