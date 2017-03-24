package com.beaver.drools.example.start;

import com.beaver.drools.example.fact.Person;
import com.beaver.drools.util.KieSessionUtil;
import org.kie.api.runtime.StatelessKieSession;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by beaver on 2017/3/24.
 */
public class RuleSyntaxNotExample {
    
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/test/RuleSyntaxNotExample.drl", StandardCharsets.UTF_8.name());
        StatelessKieSession statelessKieSession = kieSessionUtil.build().newStatelessKieSession();
        List<String> message = new ArrayList<>();
        
        statelessKieSession.setGlobal("message", message);
        Person person = new Person();
        person.setAge(25);
        person.setAddress(new ArrayList<>(Arrays.asList("北京", "上海")));
        Map<String, String> exGirlfriend = new HashMap<>();
        exGirlfriend.put("one", "宋美龄O");
        exGirlfriend.put("two", "are");
        person.setExGirlfriend(exGirlfriend);
        
        KieSessionUtil.excuteInsertObject(statelessKieSession, person);
        System.out.println(message);
    }
}
