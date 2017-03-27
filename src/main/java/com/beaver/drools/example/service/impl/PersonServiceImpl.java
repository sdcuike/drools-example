package com.beaver.drools.example.service.impl;

import com.beaver.drools.example.fact.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beaver on 2017/3/27.
 */
public class PersonServiceImpl {
    
    public List<Person> queryPersons() {
        List<Person> personList = new ArrayList<>();
        Person person = new Person();
        person.setAge(55);
        personList.add(person);
        Person person2 = new Person();
        person2.setAge(30);
        
        return personList;
    }
    
    public int savePerson(List<Person> personList) {
        System.out.println("save persons" + personList);
        return personList.size();
    }
    
}
