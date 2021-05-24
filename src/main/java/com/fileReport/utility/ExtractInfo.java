package com.fileReport.utility;

import com.fileReport.service.AggregatorRule;
import com.fileReport.service.RowDoc;

import java.util.*;

public class ExtractInfo {

    private final String path;
    private ArrayList<RowDoc> row;

    public ArrayList<RowDoc> getRow() {
        return row;
    }


    public ExtractInfo(String path){
        this.path = path;
        this.row = new ArrayList<>();
        this.row = this.metaExtract();
    }


    public HashMap<String , ArrayList<AggregatorRule>> ruleExtract(){
        HashMap<String, ArrayList<AggregatorRule>> ruleMap = new HashMap<>();
        ArrayList<AggregatorRule> aggr;
        ReadFile rule = new ReadFile(this.path);

        for(String row : rule.read()) {
            String[] current = row.split("\\|");

            if (current[1].equals("arggregate")) {
                CommonHash.hashInsert(ruleMap,current[0], new AggregatorRule(current[2], current[3]));
            }

        }
        return  ruleMap;

    }

    /*

     */
    public ArrayList<RowDoc> metaExtract() {

        ReadFile meta = new ReadFile(metaLocation(this.path));
        for (String cMeta : meta.read()) {
            String[] current = cMeta.split("\\|");
            //System.out.println(Arrays.toString(current));
            RowDoc rowdoc = new RowDoc(current[0], current[1]);
            row.add(rowdoc);
        }
        return row;
    }

    public ArrayList<HashMap< String, String>> dataExtract() {
        ArrayList<HashMap<String, String>> DataMappedWithSchema = new ArrayList<>();
        HashMap<String, String> ruleIntegral ;
        ReadFile dat = new ReadFile(datLocation(path));
        for (String cdat : dat.read()) {
            ruleIntegral = new HashMap<>();
            String[] current = cdat.split(",");
            for (int index = 0; index < row.size(); index++) {
               // row.get(index).setValue(current[index]);
                ruleIntegral.put(row.get(index).getName(),
                        extract(row.get(index).getName(),current[index]));
            }
            DataMappedWithSchema.add(ruleIntegral);
        }
        return DataMappedWithSchema;
    }


     private  String extract( String field, String value){
        if (field.equals("buildduration")){
            return value.substring(0,value.length() -1);
        }
        return value;

    }


    private boolean validateMeta(ArrayList<RowDoc> row){
        for(RowDoc field : row){
            if(field.getType().equals("Integer")){
                try {
                    Integer.parseInt(field.getValue());
                } catch(NumberFormatException | NullPointerException e) {
                    return false;
                }
            }
        }
        return true;
    }

    private String metaLocation(String path){
        return path.replace(".rule",".meta");
    }
    private String datLocation(String path){
        return path.replace(".rule",".dat");
    }

}
