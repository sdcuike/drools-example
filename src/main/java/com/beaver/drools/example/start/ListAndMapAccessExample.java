package com.beaver.drools.example.start;

import com.beaver.drools.example.fact.Person;
import com.beaver.drools.util.KieSessionUtil;
import org.kie.api.runtime.StatelessKieSession;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by beaver on 2017/3/24.
 */
public class ListAndMapAccessExample {
    
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/test/ListAndMapAccessExample.drl", StandardCharsets.UTF_8.name());
        StatelessKieSession statelessKieSession = kieSessionUtil.build().newStatelessKieSession();
        
        Person person = new Person();
        person.setAge(25);
        person.setAddress(new ArrayList<>(Arrays.asList("北京", "上海")));
        Map<String, String> exGirlfriend = new HashMap<>();
        exGirlfriend.put("one", "宋美龄");
        exGirlfriend.put("two", "are");
        person.setExGirlfriend(exGirlfriend);
        
        KieSessionUtil.excuteInsertObject(statelessKieSession,person);
        System.out.println(person.getMessage());
    }
}
