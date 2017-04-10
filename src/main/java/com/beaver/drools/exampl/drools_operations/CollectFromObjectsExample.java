package com.beaver.drools.exampl.drools_operations;

import com.beaver.drools.example.fact.Person;
import com.beaver.drools.util.KieSessionUtil;
import org.kie.api.runtime.StatelessKieSession;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * The "collect" keyword is used to find all the elements that match a specific condition <br>
 * and group them into a collection. <br>
 * <p>
 * Created by beaver on 2017/4/10.
 */
public class CollectFromObjectsExample {
    
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/drools_operations/CollectFromObjectsExample.drl", StandardCharsets.UTF_8.name());
        
        kieSessionUtil.verifyRules();
        StatelessKieSession statelessKieSession = kieSessionUtil.build().newStatelessKieSession();
        
        Person person = new Person();
        
        person.addAddress("山东");
        person.addAddress("北京");
        person.addAddress("大连");
        person.setAge(88);
        person.setName("doctorwho");
        person.putExGirlfriend("1", "李清照");
        
        Person person1 = new Person();
        person1.setName("李清照");
        statelessKieSession.execute(Arrays.asList(person, person1));
    }
}
