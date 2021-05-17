package com.fileReport.service;

/*

 */
public class RowDoc {
    private final String name;
    private final String type;
    private String value;

    public RowDoc(String name, String type){
        this.name = name;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "RowDoc{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
