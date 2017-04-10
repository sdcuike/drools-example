package com.beaver.drools.exampl.drools_operations;

import com.beaver.drools.example.fact.Person;
import com.beaver.drools.util.KieSessionUtil;
import org.kie.api.runtime.StatelessKieSession;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * The from clause is a very versatile tool. It can be used to get data from multiple <br>
 * sources and not only from attributes. You can invoke methods from global variables <br>
 * that return specific values or lists and use it from the from clause. You can basically <br>
 * use it with anything that returns a value. <br>
 * <p>
 * </p>
 * • It is easy to read and break down into multiple conditions <br>
 * • It can take into account the situations where we might have collections of <br>
 * objects to dig into (such as the orderLines attribute in the second part of our<br>
 * rule condition)<br>
 * • It can make a rule easier to read<br>
 * <p>
 * Created by beaver on 2017/4/10.<br>
 */
public class TheFromClauseExample {
    
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/drools_operations/TheFromClauseExample.drl", StandardCharsets.UTF_8.name());
        
        kieSessionUtil.verifyRules();
        StatelessKieSession statelessKieSession = kieSessionUtil.build().newStatelessKieSession();
        
        Person person = new Person();
        
        person.addAddress("山东");
        person.addAddress("北京");
        person.addAddress("大连");
        person.setAge(88);
        person.setName("doctorwho");
        person.putExGirlfriend("1", "李清照");
        
        statelessKieSession.execute(person);
    }
}
