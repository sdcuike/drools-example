package com.beaver.drools.example.ruletrigger;

import com.beaver.drools.util.KieSessionUtil;
import org.kie.api.runtime.StatelessKieSession;

import java.nio.charset.StandardCharsets;

/**
 * Created by beaver on 2017/3/28.
 */
public class RuleInsertExample {
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/ruletrigger/RuleInsertExample.drl", StandardCharsets.UTF_8.name());
        kieSessionUtil.verifyRules();
        StatelessKieSession statelessKieSession = kieSessionUtil.build().newStatelessKieSession();
        
        statelessKieSession.execute(new Object());
    }
}
