package com.beaver.drools.example.ruletrigger;

import com.beaver.drools.util.KieSessionUtil;
import org.kie.api.runtime.StatelessKieSession;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * 注意死循环的发生
 * Created by beaver on 2017/3/28.
 */
public class RuleDeleteExample {
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/ruletrigger/RuleDeleteExample.drl", StandardCharsets.UTF_8.name());
        kieSessionUtil.verifyRules();
        StatelessKieSession statelessKieSession = kieSessionUtil.build().newStatelessKieSession();
        
        statelessKieSession.execute(Arrays.asList(Calendar.getInstance(),new Date()));
    }
}
