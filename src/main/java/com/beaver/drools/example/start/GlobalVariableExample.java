package com.beaver.drools.example.start;

import com.beaver.drools.example.service.impl.EmailServiceImpl;
import com.beaver.drools.util.KieSessionUtil;
import org.kie.api.runtime.StatelessKieSession;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 通过global ，为rule提供java代码中的数据或服务接口，或rule中的结果提供给java使用。
 * <p>
 * Created by beaver on 2017/3/23.
 * <p>
 * With global you define global variables. They are used to make application objects available to the rules. Typically, they are used to provide data or services
 * that the rules use, especially application services used in rule consequences, and to return data from the rules, like logs or values added in rule consequences,
 * or for the rules to interact with the application, doing callbacks. Globals are not inserted into the Working Memory, and therefore a global should never be used
 * to establish conditions in rules except when it has a constant immutable value. The engine cannot be notified about value changes of globals and does not track
 * their changes. Incorrect use of globals in constraints may yield surprising results - surprising in a bad way.
 * <p>
 * If multiple packages declare globals with the same identifier they must be of the same type and all of them will reference the same global value.
 * <p>
 * Globals are not designed to share data between rules and they should never be used for that purpose.
 * Rules always reason and react to the working memory state, so if you want to pass data from rule to rule,
 * assert the data as facts into the working memory.
 * Care must be taken when changing data held by globals because the rule engine is not aware of those changes, hence cannot react to them.
 * </p>
 */
public class GlobalVariableExample {
    private static EmailServiceImpl emailService = new EmailServiceImpl();
    
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/test/GlobalVariablesExample.drl", StandardCharsets.UTF_8.name());
        StatelessKieSession statelessKieSession = kieSessionUtil.build().newStatelessKieSession();
        
        List<UUID> list = new ArrayList<>();
        statelessKieSession.setGlobal("list", list);
        statelessKieSession.setGlobal("emailService", emailService);
        
        System.out.println(list);
        
        KieSessionUtil.excuteInsertObject(statelessKieSession, new Object());
        KieSessionUtil.excuteInsertObject(statelessKieSession, new Object());
        System.out.println(list);
    }
}
