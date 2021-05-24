package com.fileReport.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile {

    Scanner scanner;

    public ReadFile(String path)  {
        try {
            this.scanner = new Scanner(new FileInputStream(path));
        } catch (IOException e){
            System.out.println("File not found" + e.getMessage());
        }
    }

    public ArrayList<String> read() {
        ArrayList<String> output = new ArrayList<>();
            while (scanner.hasNextLine()) {
                output.add(scanner.nextLine());
            }
        return output;

    }
}
