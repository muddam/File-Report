package com.fileReport.controller;

import com.fileReport.service.AggregatorRule;
import com.fileReport.service.Processor;
import com.fileReport.service.RowDoc;
import com.fileReport.utility.ExtractInfo;
import com.fileReport.utility.PrintFinalValues;
import com.fileReport.utility.ReadFile;
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
                    System.out.println(changedFile.getFile().toString());

                    Processor processor = new Processor(changedFile.getFile().toString());
                    processor.runExtractProcess();

                }
            }
        }
    }



    private boolean isRuleFile(ChangedFile file){
        return file.getRelativeName().endsWith("rule");
    }

    private boolean isLocked(Path path) {
        try (FileChannel ch = FileChannel.open(path, StandardOpenOption.WRITE); FileLock lock = ch.tryLock()) {
            return lock == null;
        } catch (IOException e) {
            return true;
        }
    }


}
