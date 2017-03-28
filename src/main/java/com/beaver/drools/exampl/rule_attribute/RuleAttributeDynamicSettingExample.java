package com.beaver.drools.exampl.rule_attribute;

import com.beaver.drools.example.fact.Person;
import com.beaver.drools.util.KieSessionUtil;
import org.kie.api.runtime.StatelessKieSession;

import java.nio.charset.StandardCharsets;

/**
 * 利用全局变量或者fact的数据设置规则的属性，以salience 为例，规则文件RuleAttributeDynamicSettingExample.drl中salience相同。<br>
 * <p>
 * 按先后顺序执行这两个匹配的规则。输出：<br>
 * age > 20 Person{name='null', age=88, address=null, exGirlfriend=null, message='null', ruleResultDetail=RuleResultDetail{ruleResultDetailInfo={}}}<br>
 * age > 30 Person{name='null', age=101, address=null, exGirlfriend=null, message='null', ruleResultDetail=RuleResultDetail{ruleResultDetailInfo={}}}<br>
 * <p>
 * 如果第二个规则的属性，用fact person的年龄动态设置，输出结果：<p>
 * age > 30 Person{name='null', age=88, address=null, exGirlfriend=null, message='null', ruleResultDetail=RuleResultDetail{ruleResultDetailInfo={}}}<br>
 * age > 20 Person{name='null', age=88, address=null, exGirlfriend=null, message='null', ruleResultDetail=RuleResultDetail{ruleResultDetailInfo={}}}<br>
 * age > 30 Person{name='null', age=101, address=null, exGirlfriend=null, message='null', ruleResultDetail=RuleResultDetail{ruleResultDetailInfo={}}}<br>
 * <p>
 * 第二规则的salience比第一个规则高了，动态设置后先执行第二个规则，然后再执行第一个规则，第一个规则修改了fact，规则引擎重新匹配规则，由于第一个规则no-loop 属性，它不会被规则引擎放到<br>
 *
 * 执行列表里。所以，第二个规则又重新被激活执行了，而年龄为101，是被第一个规则改变的。<br>
 * <p>
 * <p>
 * 你可以利用全局变量或fact中的属性动态设置规则的其2属性值。<br>
 * <p>
 * Created by beaver on 2017/3/28.
 */
public class RuleAttributeDynamicSettingExample {
    
    public static void main(String[] args) {
        KieSessionUtil kieSessionUtil = new KieSessionUtil();
        kieSessionUtil.addFromClassPath("/rules/rule_attribute/RuleAttributeDynamicSettingExample.drl", StandardCharsets.UTF_8.name());
        
        kieSessionUtil.verifyRules();
        StatelessKieSession statelessKieSession = kieSessionUtil.build().newStatelessKieSession();
        
        Person person = new Person();
        person.setAge(88);
        
        statelessKieSession.execute(person);
    }
}
