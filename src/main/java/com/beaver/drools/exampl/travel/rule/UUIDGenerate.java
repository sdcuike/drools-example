package com.beaver.drools.exampl.travel.rule;

import java.util.UUID;

/**
 * Created by beaver on 2017/5/23.
 */
public class UUIDGenerate {
    
    public static void main(String[] args) {
        for (int i= 0; i< 44;i++){
            System.out.println(UUID.randomUUID().toString());
        }
    }
}
