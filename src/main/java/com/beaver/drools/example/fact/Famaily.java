package com.beaver.drools.example.fact;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beaver on 2017/4/10.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Famaily {
    List<Person> personList = new ArrayList<>();
    
    public void addPerson(Person person){
        personList.add(person);
    }
    
}
