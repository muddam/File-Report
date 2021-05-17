package com.fileReport.utility;

import com.fileReport.service.AggregatorRule;

import java.util.*;

public class PrintFinalValues {


    /*
    * prints the elements
    * unique count is calculated as per size of set
    * unique values are all the values in set
    * average is sum /size of array list
    */
    public static void print(HashMap<String,HashMap<String, List<Collection<String>>  >> rowrun , HashMap<String, ArrayList<AggregatorRule>> ruleMap){
        for(Map.Entry<String, HashMap<String, List<Collection<String>>>>call : rowrun.entrySet()  ){

            System.out.println(call.getKey());
            for(Map.Entry<String,List<Collection<String>>> inCall : call.getValue().entrySet()){
                System.out.println(inCall.getKey());

                for( int i =0; i< ruleMap.get(call.getKey()).size(); i++){
                    System.out.print(ruleMap.get(call.getKey()).get(i).getKeyName() +" ");
                    if(ruleMap.get(call.getKey()).get(i).getArgRule().equals("unique-values")){
                        System.out.println("unique-values " + inCall.getValue().get(i));
                    }
                    if(ruleMap.get(call.getKey()).get(i).getArgRule().equals("unique-count")){
                        System.out.println("unique-count "+inCall.getValue().get(i).size());
                    }
                    if(ruleMap.get(call.getKey()).get(i).getArgRule().equals("average")){
                        int sum =0;
                        for(String k : inCall.getValue().get(i)){
                            sum = sum +Integer.parseInt(k);
                        }
                        System.out.print("average ");
                        System.out.println(sum * 1.0 / inCall.getValue().get(i).size());

                    }


                }


            }

        }
    }
}
