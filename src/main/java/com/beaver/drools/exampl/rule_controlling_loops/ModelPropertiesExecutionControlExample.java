package com.beaver.drools.exampl.rule_controlling_loops;

import com.beaver.drools.example.fact.Person;
import com.beaver.drools.util.KieSessionUtil;
import org.kie.api.runtime.StatelessKieSession;

import java.nio.charset.StandardCharsets;

/**
 * 通过在fact中加入规则是否激活标志位，来判断规则是否激活来控制规则的循环执行行为。<br>
 * 缺点：对fact加入了标志，引入了其他属性。<br>
 * <p>
 * Created by beaver on 2017/4/7.
 */
public class ModelPropertiesExecutionControlExample {
    
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/rule_controlling_loops/ModelPropertiesExecutionControlExample.drl", StandardCharsets.UTF_8.name());
        
        kieSessionUtil.verifyRules();
        StatelessKieSession statelessKieSession = kieSessionUtil.build().newStatelessKieSession();
        
        Person person = new Person();
        person.setAge(88);
        
        statelessKieSession.execute(person);
    }
}
