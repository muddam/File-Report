package com.fileReport.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {

    @Value("${folderPath}")
    public String folderPath;

    public String getfolderPath() {
        return folderPath;
    }
}
