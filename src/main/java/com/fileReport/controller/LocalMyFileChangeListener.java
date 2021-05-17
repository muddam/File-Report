package com.fileReport.controller;

import com.fileReport.service.AggregatorRule;
import com.fileReport.service.Processor;
import com.fileReport.service.RowDoc;
import com.fileReport.utility.PrintFinalValues;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.PrivateKey;
import java.util.*;

@Controller
public class LocalMyFileChangeListener implements FileChangeListener {

    /*
    *Runs on change of change or addition of file .rule
     */
    @Override
    public void onChange(Set<ChangedFiles> changeSet) {
        for(ChangedFiles changedFiles : changeSet) {
            for(ChangedFile changedFile: changedFiles.getFiles()) {
                if(!isLocked(changedFile.getFile().toPath())
                        && isRuleFile(changedFile)
                ) {
                    System.out.println(changedFile.getFile());
                    ArrayList<RowDoc> row = metaExtract(changedFile);
                    HashMap<String, ArrayList<AggregatorRule>> ruleMap = ruleExtract(changedFile);
                    HashMap< String, String> ruleIntegral = new HashMap<>();

                    try {
                        FileInputStream dat = new FileInputStream(changedFile.getFile().toString().
                                replace(".rule",".dat"));

                    Scanner sc=new Scanner(dat);
                    HashMap<String,HashMap<String, List<Collection<String>>  >> rowrun = new HashMap<>();

                    while(sc.hasNextLine()) {

                        String[] current = sc.nextLine().split(",");
                        for(int i =0; i < row.size();i++){
                            row.get(i).setValue(current[i]);
                            ruleIntegral.put(row.get(i).getName(),current[i]);
                        }


                        if(validateMeta(row)) {
                            Processor.processRow(ruleMap,rowrun,ruleIntegral);
                        }else{
                            System.out.println("Failed for record"+row);
                        }

                    }
                        PrintFinalValues.print(rowrun,ruleMap);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }




                }
            }
        }
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
    private boolean isRuleFile(ChangedFile file){
        return file.getRelativeName().endsWith("rule");
    }

    private HashMap<String , ArrayList<AggregatorRule> > ruleExtract(ChangedFile file){
        HashMap<String, ArrayList<AggregatorRule>> ruleMap = new HashMap<>();
        ArrayList<AggregatorRule> aggr;
        Scanner sc = null;
        try {
            FileInputStream meta =
                    new FileInputStream(file.getFile().toString());
             sc=new Scanner(meta);
            while(sc.hasNextLine()){
                String[] current = sc.nextLine().split("\\|");
                if(current[1].equals("arggregate")){
                    if(!ruleMap.containsKey(current[0])){
                        aggr = new ArrayList<>();
                        aggr.add(new AggregatorRule(current[2],current[3]));
                        ruleMap.put(current[0],aggr);
                    }else{
                        aggr = new ArrayList<>();
                        aggr.addAll(ruleMap.get(current[0]));
                        aggr.add(new AggregatorRule(current[2],current[3]));
                        ruleMap.put(current[0],aggr);
                    }
                }
            }

            sc.close();
        } catch (FileNotFoundException e) {
            // Issue while reading .rules file
            e.printStackTrace();
            if(null != sc){
                sc.close();
            }
        }
        return  ruleMap;

    }

    private ArrayList<RowDoc> metaExtract(ChangedFile file){
        ArrayList<RowDoc> row = new ArrayList<>();
        Scanner sc = null;
        try {
            FileInputStream meta =
                    new FileInputStream(file.getFile().toString().
                            replace(".rule",".meta") );
            sc=new Scanner(meta);
            while(sc.hasNextLine()){
                String[] current = sc.nextLine().split("\\|");
               // System.out.println(Arrays.toString(current));
                RowDoc rowdoc = new RowDoc(current[0],current[1]);

                row.add(rowdoc);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            // No meta file found
            e.printStackTrace();
            if(null != sc){
                sc.close();
            }
        }
        return row;

    }



    private boolean isLocked(Path path) {
        try (FileChannel ch = FileChannel.open(path, StandardOpenOption.WRITE); FileLock lock = ch.tryLock()) {
            return lock == null;
        } catch (IOException e) {
            return true;
        }
    }


}
