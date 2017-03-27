package com.beaver.drools.example.start;

import com.beaver.drools.example.service.impl.PersonServiceImpl;
import com.beaver.drools.util.KieSessionUtil;
import org.kie.api.runtime.StatelessKieSession;

import java.nio.charset.StandardCharsets;

/**
 * Created by beaver on 2017/3/27.
 */
public class GlobalVariableJavaServiceExample {
    private static PersonServiceImpl personService = new PersonServiceImpl();
    
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/test/GlobalVariableJavaServiceExample.drl", StandardCharsets.UTF_8.name());
        StatelessKieSession statelessKieSession = kieSessionUtil.build().newStatelessKieSession();
        
        statelessKieSession.setGlobal("personService", personService);
        statelessKieSession.execute(new Object());
    }
}
