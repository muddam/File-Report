package com.fileReport.service;

import java.util.*;

public class AggregationValueList {

    HashMap<String, List<Collection<String>>>  KeyWithValueList;
    List<Collection<String>> valueList ;
    Collection<String> valuesOfSameRule ;


    /*
    * Factory design to derive objects based on unique vs average
     */
    public AggregationValueList(String value){
        KeyWithValueList = new HashMap<>();
        valueList = new ArrayList<>();
        if(value.contains("unique")){
            valuesOfSameRule = new HashSet<>();
        }if(value.equals("average")){
            valuesOfSameRule = new ArrayList<>();
        }

    }

    public boolean isKeyPresent( String key){
        return this.KeyWithValueList.containsKey(key);
    }

    public List<Collection<String>> pullValueList(String key){
        return this.KeyWithValueList.get(key);
    }



    public void appendWithValues( HashMap<String, List<Collection<String>>> internalKeysValue,
                                  String field , String fieldValue, int index){
        this.KeyWithValueList = internalKeysValue;
        if(this.isKeyPresent(field)){
            this.valueList  = this.pullValueList(field);
            if (this.valueList.size() < index + 1) {
                this.valuesOfSameRule.add(fieldValue);
                this.valueList.add(index, this.valuesOfSameRule);
            } else {
                Collection<String> set = this.valueList.get(index);
                set.add(fieldValue);
            }
        }else{
            this.appendKeyWithValues(field,fieldValue);
        }

    }

    public HashMap<String, List<Collection<String>>> appendKeyWithValues(String field, String value){
        this.valuesOfSameRule.add(value);
        this.valueList.add(valuesOfSameRule);
        this.KeyWithValueList.put(field, valueList);
        return this.KeyWithValueList;
    }


    public HashMap<String, List<Collection<String>>> getKeyWithValueList() {
        return KeyWithValueList;
    }

    public void setKeyWithValueList(HashMap<String, List<Collection<String>>> keyWithValueList) {
        KeyWithValueList = keyWithValueList;
    }

    public Collection<String> getValuesOfSameRule() {
        return valuesOfSameRule;
    }

    public void setValuesOfSameRule(Collection<String> valuesOfSameRule) {
        this.valuesOfSameRule = valuesOfSameRule;
    }


    public List<Collection<String>> getValueList() {
        return null;
    }

    public void setValueList(List<Collection<String>> valueList) {

    }
}
