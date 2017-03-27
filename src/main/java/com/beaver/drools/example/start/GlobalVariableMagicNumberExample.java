package com.beaver.drools.example.start;

import com.beaver.drools.example.fact.Person;
import com.beaver.drools.util.KieSessionUtil;
import org.kie.api.runtime.StatelessKieSession;

import java.nio.charset.StandardCharsets;

/**
 * Created by beaver on 2017/3/27.
 */
public class GlobalVariableMagicNumberExample {
    private static final int MAX_AGE = 88;
    
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/test/GlobalVariableMagicNumberExample.drl", StandardCharsets.UTF_8.name());
        StatelessKieSession statelessKieSession = kieSessionUtil.build().newStatelessKieSession();
        
        statelessKieSession.setGlobal("MAX_AGE",MAX_AGE );
    
        Person person = new Person();
        person.setAge(89);
        System.out.println(person);
        KieSessionUtil.excuteInsertObject(statelessKieSession, person);
        System.out.println(person);
        
    }
}
