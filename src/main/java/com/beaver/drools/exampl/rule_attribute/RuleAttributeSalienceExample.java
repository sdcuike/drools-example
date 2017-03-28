package com.beaver.drools.exampl.rule_attribute;

import com.beaver.drools.example.fact.Person;
import com.beaver.drools.util.KieSessionUtil;
import org.kie.api.runtime.StatelessKieSession;

import java.nio.charset.StandardCharsets;

/**
 * 默认规则salience 为 0，被匹配的规则被执行的先后顺序不能确定，drools中，同一个drl文件中的被匹配的规则按文件内先后顺序执行，但不同drl
 * 文件之间被匹配的规则的执行顺序是不确定的（规则引擎算法决定），为了定制被匹配规则的执行先后顺序，salience值越高，越先执行。
 *
 * Created by beaver on 2017/3/28.
 */
public class RuleAttributeSalienceExample {
    
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/rule_attribute/RuleAttributeSalienceExample.drl", StandardCharsets.UTF_8.name());
        
        kieSessionUtil.verifyRules();
        StatelessKieSession statelessKieSession = kieSessionUtil.build().newStatelessKieSession();
        
        Person person = new Person();
        person.setAge(88);
        
        statelessKieSession.execute(person);
    }
}
