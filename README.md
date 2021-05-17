##### FileReport

Generates report based on rules that are sent via rules files

Requires three files in below sequence
 <FileName>.dat
 <Filename>.meta
 <Filename>.rule
 
 .dat
 has data with comma delimiter
 
 .meta
 has schema information feildName spearated by | feildType
 
 .rule
 has rules for what has to be done for specified column
 .rule file the trigger file 
  on finding the change or new .rule file code will try to search for meta file to read and then data file to process
  
  
  sample files are present under src/main/java/resources/input
  
**How to run** 

Give a valid folder path in application.properties and run the application
place .dat and .meta files in folder
then place .rule file in folder
 
 
 
