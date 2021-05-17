package com.fileReport.utility;

import com.fileReport.controller.LocalMyFileChangeListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;


import javax.annotation.PreDestroy;
import java.io.File;
import java.time.Duration;
@Component
public class FileWatcher {

    @Autowired
    Config conf ;

    @Bean
    public FileSystemWatcher fileSystemWatcher() {
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true, Duration.ofMillis(500L), Duration.ofMillis(300L));
        fileSystemWatcher.addSourceDirectory(new File(conf.getfolderPath()));
        fileSystemWatcher.addListener(new LocalMyFileChangeListener());
        fileSystemWatcher.start();
        System.out.println("started fileSystemWatcher");
        return fileSystemWatcher;
    }

    @PreDestroy
    public void onDestroy() throws Exception {
        fileSystemWatcher().stop();
    }
}
