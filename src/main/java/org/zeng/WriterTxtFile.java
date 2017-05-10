package org.zeng;

import java.io.*;

public class WriterTxtFile {

    void writerTxt() {
        BufferedWriter fw = null;
        try {
            File file = new File("D:/RFXS_9994_20170509_USD_20170509A.txt");
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "GBK")); // 指定编码格式，以免读取时中文字符异常

            fw.append("9994|20170509A|20170509|1200|120000|USD|\n" +
                    "Payment order start\n");
            int payOrderId = 1;
            for (int i = 0; i < 1200; i++) {
                fw.append(String.format("20170509N%s|0|440902198608270069|CCB|张莹|6217003110014723421|18219340978|USD|100||01122030\n", payOrderId++));
            }
            fw.append("Payment order end");
            fw.newLine();
            fw.append("Order detail start");
            fw.newLine();
            payOrderId = 1;
            int orderId = 1;
            for (int i = 0; i < 120000; i++) {
                if (i != 0 && i % 100 == 0) {
                    fw.append(String.format("20170509N%s|%s|20160718|204125|20160202|204206|USD|1.00|Baby Shampoo|1\n", payOrderId, orderId++));
                    payOrderId++;
                } else {
                    fw.append(String.format("20170509N%s|%s|20160718|204125|20160202|204206|USD|1.00|Baby Shampoo|1\n", payOrderId, orderId++));
                }
            }
            fw.append("Order detail end");
            System.out.println("---------------------------");
            System.out.println("file: %s write finished");
            System.out.println("---------------------------");
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
        }
    }

}
