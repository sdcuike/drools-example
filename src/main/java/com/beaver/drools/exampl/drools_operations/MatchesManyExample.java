package com.beaver.drools.exampl.drools_operations;

import com.beaver.drools.util.KieSessionUtil;
import org.kie.api.runtime.StatelessKieSession;

import java.nio.charset.StandardCharsets;

/**
 * Created by beaver on 2017/5/24.
 */
public class MatchesManyExample {
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/drools_operations/MatchesManyExample.drl", StandardCharsets.UTF_8.name());
        
        kieSessionUtil.verifyRules();
        StatelessKieSession statelessKieSession = kieSessionUtil.build().newStatelessKieSession();
        
        String str = "121ass";
        
        boolean matches = str.matches("^(22|121a).*");
        System.out.println(matches);
        statelessKieSession.execute(str);
    }
}
