package com.fileReport.service;

import java.util.*;

public class Processor {

    /*
    * Record processor and insert into map based on the rules define
    * unique values and unique count uses set
    * Average uses array list
     */
    public static void processRow(HashMap<String, ArrayList<AggregatorRule>> ruleMap ,
                                  HashMap<String,HashMap<String, List<Collection<String>>  >> rowrun ,
                                  HashMap< String, String> ruleIntegral ){
        for (Map.Entry<String, ArrayList<AggregatorRule>> map : ruleMap.entrySet()) {
            //System.out.println(map.getKey() + map.getValue());

            for (int i = 0; i < map.getValue().size(); i++) {
                if (!rowrun.containsKey(map.getKey())) {

                    if (map.getValue().get(i).getArgRule().equals("unique-count") ||
                            map.getValue().get(i).getArgRule().equals("unique-values") ) {
                       initialUnique(map,rowrun,ruleIntegral ,i);
                    }
                    if (map.getValue().get(i).getArgRule().equals("average")) {
                        HashMap<String, List<Collection<String>>> k = new HashMap<>();
                        Collection<String> set = new HashSet<>();
                        set.add(ruleIntegral.get(map.getValue().get(i).getKeyName())
                                .substring(0,ruleIntegral.get(map.getValue().get(i).getKeyName()).length() -1));
                        List<Collection<String>> initial = new ArrayList<>();
                        initial.add(set);
                        k.put(ruleIntegral.get(map.getKey()), initial);
                        rowrun.put(map.getKey(), k);
                    }

                } else {

                    if (map.getValue().get(i).getArgRule().equals("unique-count") ||
                            map.getValue().get(i).getArgRule().equals("unique-values")) {
                        unique(map,rowrun,ruleIntegral ,i);
                    }

                    if (map.getValue().get(i).getArgRule().equals("average")) {
                        HashMap<String, List<Collection<String>>> k = rowrun.get(map.getKey());
                        if (k.containsKey(ruleIntegral.get(map.getKey()))) {
                            List<Collection<String>> listset = k.get(ruleIntegral.get(map.getKey()));

                            if (listset.size() < i + 1) {
                                Collection<String> set = new ArrayList<>();
                                set.add(ruleIntegral.get(map.getValue().get(i).getKeyName())
                                        .substring(0,ruleIntegral.get(map.getValue().get(i).getKeyName()).length() -1));
                                listset.add(i, set);
                            } else {

                                Collection<String> set = listset.get(i);
                                set.add( ruleIntegral.get(map.getValue().get(i).getKeyName())
                                        .substring(0,ruleIntegral.get(map.getValue().get(i).getKeyName()).length() -1));

                            }

                        }
                        else{

                            Collection<String> set = new ArrayList<>();
                            set.add(ruleIntegral.get(map.getValue().get(i).getKeyName())
                                    .substring(0,ruleIntegral.get(map.getValue().get(i).getKeyName()).length() -1));
                            List<Collection<String>> initial = new ArrayList<>();
                            initial.add(set);
                            k.put(ruleIntegral.get(map.getKey()),initial );
                        }
                    }


                }


            }
        }
    }


    private static void initialUnique(Map.Entry<String, ArrayList<AggregatorRule>> map,
                                      HashMap<String,HashMap<String, List<Collection<String>>  >> rowRun ,
                                      HashMap< String, String> ruleIntegral , int index  ){
        HashMap<String, List<Collection<String>>> k = new HashMap<>();
        Collection<String> set = new HashSet<>();
        set.add(ruleIntegral.get(map.getValue().get(index).getKeyName()));
        List<Collection<String>> initial = new ArrayList<>();
        initial.add(set);
        k.put(ruleIntegral.get(map.getKey()), initial);
        rowRun.put(map.getKey(), k);
    }

    private static void unique(Map.Entry<String, ArrayList<AggregatorRule>> map,
                               HashMap<String,HashMap<String, List<Collection<String>>  >> rowRun ,
                               HashMap< String, String> ruleIntegral , int index ){
        HashMap<String, List<Collection<String>>> k = rowRun.get(map.getKey());
        if (k.containsKey(ruleIntegral.get(map.getKey()))) {
            List<Collection<String>> listset = k.get(ruleIntegral.get(map.getKey()));
            if (listset.size() < index + 1) {
                Set<String> set = new HashSet<>();
                set.add(ruleIntegral.get(map.getValue().get(index).getKeyName()));
                listset.add(index, set);
            } else {
                Collection<String> set = listset.get(index);
                set.add(ruleIntegral.get(map.getValue().get(index).getKeyName()));
            }

        }else{

            Collection<String> set = new HashSet<>();
            set.add(ruleIntegral.get(map.getValue().get(index).getKeyName()));
            List<Collection<String>> initial = new ArrayList<>();
            initial.add(set);
            k.put(ruleIntegral.get(map.getKey()),initial );
        }
    }
}
