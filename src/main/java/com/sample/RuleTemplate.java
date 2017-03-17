package com.sample;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.drools.template.ObjectDataCompiler;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

public class RuleTemplate {

    public static void main(String[] args) {
        InputStream inputStream = RuleTemplate.class.getResourceAsStream("/rule-template/person-rule.drl");
        ObjectDataCompiler dataCompiler = new ObjectDataCompiler();

        List<Person> personList = new ArrayList<>();
        personList.add(new Person(1, 10));
        personList.add(new Person(20, 50));
        String ruleTemple = dataCompiler.compile(personList, inputStream);
        KieSession kieSession = createKieSessionFromDRL(ruleTemple);

        Person person = new Person(2, 8);
        System.out.println(person);
        kieSession.insert(person);
        kieSession.fireAllRules();
        System.out.println(person);

    }


    private static KieSession createKieSessionFromDRL(String drl) {
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);
        Results results = kieHelper.verify();
        if (results.hasMessages(Message.Level.WARNING, Message.Level.
                ERROR)) {
            List<Message> messages = results.getMessages(Message.
                    Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.println("Error: " + message.getText());
            }
            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }
        return kieHelper.build().newKieSession();
    }






}