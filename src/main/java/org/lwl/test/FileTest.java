package org.lwl.test;

import java.io.File;

public class FileTest {

    public static void main(String[] args) {
        String newFileName = String.valueOf(System.currentTimeMillis());
        File source = new File("temp", newFileName).getAbsoluteFile();
        System.out.println("source = " + source);

        boolean isExist = source.getParentFile().exists();
        System.out.println("isExist = " + isExist);
        if (!isExist) {
            boolean isMk = source.getParentFile().mkdir();
            System.out.println("isMk = " + isMk);
        }
    }
    
}
