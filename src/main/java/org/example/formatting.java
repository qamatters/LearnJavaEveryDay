package org.example;

import org.apache.commons.lang3.StringUtils;

public class formatting {
    public static void main(String[] args) {

        String value1 = "0.05";
        String value2 = ".05";

        String value3 = "-0.17";
        String value4 = "-.17";

        String value5 = "0.98";
        String value6 = ".98";

        String value7 = "-0.90";
        String value8 = "2.90";

        String[] values = {value1,value2,value3,value4,value5,value6, value7, value8};

        for(String value : values) {
            Double val1 = Double.parseDouble(value);
            System.out.println("value is :" + val1);
        }



//        try{
//            if(!valueBeforeDecimal.isEmpty()) {
//                if(Integer.parseInt(valueBeforeDecimal)==0) {
////                  String updatedValue = StringUtils.
//                }
//            }
//        } catch (Exception ignored){
//        }



    }
}
