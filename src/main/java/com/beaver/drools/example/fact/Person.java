package com.beaver.drools.example.fact;

import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * Created by beaver on 2017/3/24.
 */
@Data
@NoArgsConstructor
public class Person {
    private String name;
    private int age;
    private List<String> address;
    private Map<String, String> exGirlfriend;
    private String message;
}
