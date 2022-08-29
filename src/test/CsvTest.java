package test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CsvTest {
    public static void main(String[] args) {
        try {
            test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 替换csv中的字符，然后写入到新到csv中；
     **/
    public static void test() throws Exception {
        String sourceFilePath = "/Users/lwl/work/数据处理/abc.csv";
        String newFilePath = "/Users/lwl/work/数据处理/abc1.csv";
        File sourceCsvFile = new File(sourceFilePath);
        File finalCsvFile = new File(newFilePath);
        final Charset Encoding = StandardCharsets.UTF_8;

        String line = "";
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(finalCsvFile), Encoding))) {
            try (BufferedReader br = new BufferedReader(new FileReader(sourceCsvFile))) {
                while ((line = br.readLine()) != null) {
                    System.out.println("line = " + line);
                    if (line.contains("你好")){
                        line = line.replace("你好","A");
                    }
                    String newFileLine = line;
                    writer.write(newFileLine);
                    writer.newLine();
                }
            }
        }
    }
}
