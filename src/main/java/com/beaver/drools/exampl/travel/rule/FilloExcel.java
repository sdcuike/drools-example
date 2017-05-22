package com.beaver.drools.exampl.travel.rule;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Field;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Created by beaver on 2017/5/22.
 */
public class FilloExcel {
    static String sheetName = "s1";
    static String fileName = "tengxun-travle-rule.xlsx";
    public static void main(String[] args) throws Throwable {
    
        String filePath = FilloExcel.class.getClassLoader().getResource(fileName).getFile();
        System.out.println(filePath);
        Fillo fillo=new Fillo();
    
        Connection connection=fillo.getConnection(filePath);
        String strQuery="Select * from s1 ";
        Recordset recordset=connection.executeQuery(strQuery);
    
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
        while(recordset.next()){
            Field field = recordset.getField(1);
            System.out.println(gson.toJson(field));
        }
    
        recordset.close();
        connection.close();
    }
}
