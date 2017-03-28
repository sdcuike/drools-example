package com.beaver.drools.example.ruletrigger;

import com.beaver.drools.util.KieSessionUtil;
import org.kie.api.runtime.StatelessKieSession;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;

/**
 * 注意死循环的发生
 * Created by beaver on 2017/3/28.
 */
public class RuleModifyExample {
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/ruletrigger/RuleModifyExample.drl", StandardCharsets.UTF_8.name());
        kieSessionUtil.verifyRules();
        StatelessKieSession statelessKieSession = kieSessionUtil.build().newStatelessKieSession();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,2016);
        calendar.set(Calendar.MONTH,3);
        statelessKieSession.execute(calendar);
    }
}
