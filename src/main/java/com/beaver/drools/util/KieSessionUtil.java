package com.beaver.drools.util;

import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.builder.conf.KnowledgeBuilderOption;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by beaver on 2017/3/14.
 * <p>
 * <p>
 * KieSessionUtil kieSessionUtil = new KieSessionUtil();<br>
 * <p>
 * KieHelper kieHelper = kieSessionUtil.addRuleFilesFromClassPathDirectory("rules");<br>
 * <p>
 * KieBase kieBase = kieSessionUtil.build();<br>
 * <p>
 * StatelessKieSession kSession = kieBase.newStatelessKieSession();<br>
 * <p>
 * <p>
 */
public final class KieSessionUtil extends KieHelper {
    private static final Message.Level[] ERROR = new Message.Level[]{Message.Level.WARNING, Message.Level.ERROR};
    
    public KieSessionUtil() {
        super();
    }
    
    public KieSessionUtil(KnowledgeBuilderOption... options) {
        super(options);
    }
    
    public KieSessionUtil addRuleFilesFromClassPathDirectory(String directoryName)
            throws URISyntaxException, IOException {
        
        URI uri = KieSessionUtil.class.getClassLoader().getResource(directoryName).toURI();
        
        List<Path> pathList = new ArrayList<>();
        Files.walkFileTree(Paths.get(uri), new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }
            
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                pathList.add(file);
                return FileVisitResult.CONTINUE;
            }
            
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
            
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });
        
        pathList.stream()
                .map(path -> ResourceFactory.newFileResource(path.toFile()))
                .forEach(this::addResource);
        
        return this;
    }
    
    public void verifyRules() {
        Results results = super.verify();
        
        if (results.hasMessages(ERROR)) {
            List<Message> messages = results.getMessages(ERROR);
            StringBuilder sb = new StringBuilder();
            for (Message message : messages) {
                sb.append("\n");
                sb.append("Error: ");
                sb.append(message);
            }
            throw new IllegalStateException("Compilation errors were found. Check the logs.\n" + sb);
        }
    }
    
    
    public static <T>  void excuteInsertObject(StatelessKieSession ksession, T... object) {
        ksession.execute(Arrays.asList(object));
    }
}