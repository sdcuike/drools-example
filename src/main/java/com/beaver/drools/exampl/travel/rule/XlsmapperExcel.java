package com.beaver.drools.exampl.travel.rule;

import com.gh.mygreen.xlsmapper.XlsMapper;
import com.gh.mygreen.xlsmapper.XlsMapperException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by beaver on 2017/5/22.
 */
public class XlsmapperExcel {
    
    static String fileName = "tengxun-travle-rule.xlsx";
    
    public static void main(String[] args) throws IOException, XlsMapperException {
        String filePath = FilloExcel.class.getClassLoader().getResource(fileName).getFile();
        
        XlsMapper xlsMapper = new XlsMapper();
        TravelRuleSheet sheet = xlsMapper.load(new FileInputStream(filePath), TravelRuleSheet.class);
        
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        System.out.println(gson.toJson(sheet));
    }
    
}
