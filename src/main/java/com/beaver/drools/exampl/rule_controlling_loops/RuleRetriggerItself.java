package com.beaver.drools.exampl.rule_controlling_loops;

import com.beaver.drools.example.fact.Person;
import com.beaver.drools.util.KieSessionUtil;
import org.kie.api.runtime.StatelessKieSession;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * It's worth mentioning that no-loop only prevents this rule from refiring for the <br>
 * same data if it was the last rule to fire. If another rule changes the working memory<br>
 * in a way that matches this rule again, the no-loop condition won't prevent it from<br>
 * executing a second time.<br>
 * </p>
 *
 * Created by beaver on 2017/4/7.
 */
public class RuleRetriggerItself {
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/rule_controlling_loops/RuleRetriggerItself.drl", StandardCharsets.UTF_8.name());
        
        kieSessionUtil.verifyRules();
        StatelessKieSession statelessKieSession = kieSessionUtil.build().newStatelessKieSession();
        
        Person person = new Person();
        person.setAge(88);
        
        statelessKieSession.execute(person);
    }
}
