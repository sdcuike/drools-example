package com.beaver.drools.exampl.drools_operations;

import com.beaver.drools.example.fact.Person;
import com.beaver.drools.util.KieSessionUtil;
import org.kie.api.runtime.StatelessKieSession;

import java.nio.charset.StandardCharsets;

/**
 * Created by beaver on 2017/5/24.
 */
public class StartsWithOpeartionExample {
    
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/drools_operations/StartsWithOpeartionExample.drl", StandardCharsets.UTF_8.name());
    
        kieSessionUtil.verifyRules();
        StatelessKieSession statelessKieSession = kieSessionUtil.build().newStatelessKieSession();
    
        Person person = new Person();
        person.putExGirlfriend("g1","doctorwho");
        statelessKieSession.execute(person);
    
    }
}
