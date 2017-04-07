package com.beaver.drools.exampl.drools_operations;

import com.beaver.drools.util.KieSessionUtil;
import org.kie.api.runtime.StatelessKieSession;

import java.nio.charset.StandardCharsets;

/**
 * Created by beaver on 2017/4/7.
 */
public class RegexOperationsExample {
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/drools_operations/RegexOperationsExample.drl", StandardCharsets.UTF_8.name());
        
        kieSessionUtil.verifyRules();
        StatelessKieSession statelessKieSession = kieSessionUtil.build().newStatelessKieSession();
        
        String str = "121a21";
    
        boolean matches = str.matches("^[121].*");
        System.out.println(matches);
        statelessKieSession.execute(str);
    }
}
