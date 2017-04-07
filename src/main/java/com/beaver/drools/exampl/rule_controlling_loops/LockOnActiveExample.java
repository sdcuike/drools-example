package com.beaver.drools.exampl.rule_controlling_loops;

import com.beaver.drools.example.fact.Person;
import com.beaver.drools.util.KieSessionUtil;
import org.kie.api.runtime.StatelessKieSession;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * These rules have the no-loop attribute so that even if they modify the order object, <br>
 * they won't retrigger themselves. However, nothing is stopping them from activating  <br>
 * each other. Therefore, the first rule will trigger the second one, which will trigger the  <br>
 * first one again, and so on and so forth. This type of infinite loop requires something a  <br>
 * bit stronger than the no-loop attribute.<br>
 * One quick way of making sure that a rule doesn't get retriggered for the same objects  <br>
 * is to add an attribute called lock-on-active to the troublesome rules. Whenever a  <br>
 * ruleflow-group becomes active or an agenda-group receives the focus, any rule  <br>
 * within this group that has lock-on-active set to true will not be activated any more  <br>
 * for the same objects. Irrespective of the origin of the update, the activation of a   <br>
 * matching rule is discarded.
 * <p>
 * </p>
 * 不加no-loop，只执行第一个规则，第一个规则匹配之后，修改了fact，又重新被激活。第二个规则没机会执行。<br>
 * 加no-loop，两个规则交替执行，以为no-loop只对本规则起作用，所以造成了这两个规则的交替执行。<br>
 * 加lock-on-active，两个规则顺序执行，而且都执行一次。
 * Created by beaver on 2017/4/7.
 */
public class LockOnActiveExample {
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/rule_controlling_loops/LockOnActiveExample.drl", StandardCharsets.UTF_8.name());
        
        kieSessionUtil.verifyRules();
        StatelessKieSession statelessKieSession = kieSessionUtil.build().newStatelessKieSession();
        
        Person person = new Person();
        person.setAge(88);
        
        statelessKieSession.execute(person);
    }
}
