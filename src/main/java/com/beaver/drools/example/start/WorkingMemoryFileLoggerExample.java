package com.beaver.drools.example.start;

import com.beaver.drools.example.fact.Person;
import com.beaver.drools.util.KieSessionUtil;
import org.drools.core.audit.WorkingMemoryFileLogger;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.event.KnowledgeRuntimeEventManager;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by beaver on 2017/3/27.
 */
public class WorkingMemoryFileLoggerExample {
    
    
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/test/RuleTree.drl", StandardCharsets.UTF_8.name());
        StatelessKieSession statelessKieSession = kieSessionUtil.build().newStatelessKieSession();
        kieSessionUtil.verifyRules();
        WorkingMemoryFileLogger logger = new WorkingMemoryFileLogger((KnowledgeRuntimeEventManager) statelessKieSession);
        logger.setFileName("event-log");
        
        
        List<Person> list = new ArrayList<>();
        Person person = new Person();
        person.setAge(5);
        list.add(person);
        
        statelessKieSession.execute(list);
        logger.writeToDisk();
        System.out.println(list);
    }
}
