package org.kdjx.entity;


import java.io.File;
import java.io.IOException;

/**
 * ADB 类
 */
public class ADB {

    public ADB(){

    }

    public Process getProcess(String... str) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(str);
        return builder.start();
    }
}
