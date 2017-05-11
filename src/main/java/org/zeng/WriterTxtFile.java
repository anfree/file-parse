package org.zeng;

import java.io.*;

public class WriterTxtFile {

    void writerTxt() {
        System.out.println("------------- start --------------");
        int payOrderNumber = 1200;
        int proportion = 5;
        int orderDetailNumber = payOrderNumber * proportion;
        BufferedWriter fw = null;
        try {
            File file = new File("D:/RFXS_70510002_20170511_USD_20170511A.txt");
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "GBK")); // 指定编码格式，以免读取时中文字符异常

            fw.append(String.format("70510002|20170511A|20170511|%s|%s|USD|\n", payOrderNumber, orderDetailNumber));
            fw.append("Payment order start\n");
            int payOrderId = 1;
            for (int i = 0; i < payOrderNumber; i++) {
                fw.append(String.format("20170511N%s|0|123456789012345678|CCB|曾哥|1234567890123456789|18212345678|USD|0.05||01122030\n", payOrderId++));
            }
            fw.append("Payment order end");
            fw.newLine();
            fw.append("Order detail start");
            fw.newLine();
            payOrderId = 1;
            int orderId = 1;
            for (int i = 1; i < orderDetailNumber+1; i++) {
                if (i % proportion != 0) {
                    fw.append(String.format("20170511N%s|%s|20160718|204125|20160202|204206|USD|0.01|Baby Shampoo|1\n", payOrderId, orderId++));
                } else {
                    fw.append(String.format("20170511N%s|%s|20160718|204125|20160202|204206|USD|0.01|Baby Shampoo|1\n", payOrderId++, orderId++));
                }
            }
            fw.append("Order detail end");
            System.out.println("file: %s write finished");
            fw.flush(); // 全部写入缓存中的内容
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("-------------- end -------------");
        }
    }

}
