package com.fileReport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.fileReport.utility"})
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

     //   FileWatcher sample = new FileWatcher();

    }

}