package com.fileReport.service;

import com.fileReport.utility.ExtractInfo;
import com.fileReport.utility.PrintFinalValues;

import java.util.*;

public class Processor {

    private final String ruleFilePath;

   public Processor(String path){
        this.ruleFilePath = path;
    }

    /**
     *
     */
    public void runExtractProcess(){

        ExtractInfo extractInfo = new ExtractInfo(this.ruleFilePath);
        HashMap<String, ArrayList<AggregatorRule>> ruleMap = extractInfo.ruleExtract();
        ArrayList<HashMap<String, String>> listDataWithSchema =  extractInfo.dataExtract();
        HashMap<String,HashMap<String, List<Collection<String>>  >> analyzedData = new HashMap<>();

        for(HashMap<String,String> dataWithSchema: listDataWithSchema) {
            processRow(ruleMap, analyzedData, dataWithSchema);
        }
        PrintFinalValues.print(analyzedData,ruleMap);

    }

    /*
    * Record processor and insert into map based on the rules define
    * unique values and unique count uses set
    * Average uses array list
     */

    private  void processRow(HashMap<String, ArrayList<AggregatorRule>> ruleMap ,
                                  HashMap<String,HashMap<String, List<Collection<String>>  >> rowrun ,
                                  HashMap< String, String> ruleIntegral ){
        for (Map.Entry<String, ArrayList<AggregatorRule>> map : ruleMap.entrySet()) {
            String aggFieldKey =map.getKey();
            ArrayList<AggregatorRule> currentRuleList = map.getValue();
            String field = ruleIntegral.get(aggFieldKey);
            for (int index = 0; index < currentRuleList.size(); index++) {
                AggregatorRule currentRule = currentRuleList.get(index);
                AggregationValueList aggregationValueList = new AggregationValueList(currentRule.getArgRule());
                String fieldValue = ruleIntegral.get(currentRule.getKeyName());
                if (!rowrun.containsKey(aggFieldKey)) {
                    rowrun.put(aggFieldKey, aggregationValueList.appendKeyWithValues(field,fieldValue));
                } else {
                    aggregationValueList.appendWithValues(rowrun.get(aggFieldKey),field, fieldValue, index);
                }
            }
        }
    }

}
