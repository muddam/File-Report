package com.fileReport.service;

public class AggregatorRule {
    String keyName;
    String argRule;

   public AggregatorRule(String keyName, String argRule){
        this.keyName = keyName;
        this.argRule = argRule;
    }

    public String getKeyName() {
        return keyName;
    }

    public String getArgRule() {
        return argRule;
    }

    public void setArgRule(String argRule) {
        this.argRule = argRule;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    @Override
    public String toString() {
        return "AggregatorRule{" +
                "keyName='" + keyName + '\'' +
                ", argRule='" + argRule + '\'' +
                '}';
    }
}
