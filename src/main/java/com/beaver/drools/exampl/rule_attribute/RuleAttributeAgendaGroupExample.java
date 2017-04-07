package com.beaver.drools.exampl.rule_attribute;

import com.beaver.drools.example.fact.Person;
import com.beaver.drools.util.KieSessionUtil;
import org.kie.api.runtime.KieSession;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * Created by beaver on 2017/3/28.
 */
public class RuleAttributeAgendaGroupExample {
    
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/rule_attribute/RuleAttributeAgendaGroupExample.drl", StandardCharsets.UTF_8.name());
        
        kieSessionUtil.verifyRules();
        KieSession kieSession = kieSessionUtil.build().newKieSession();
        kieSession.getAgenda().getAgendaGroup("agenda-group-2").setFocus();
        Person person = new Person();
        person.setAge(88);
        kieSession.insert(person);
        kieSession.dispose();
     }
}
