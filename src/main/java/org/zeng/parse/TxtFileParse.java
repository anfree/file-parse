package org.zeng.parse;

import org.zeng.constant.Constants;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TxtFileParse {

	public static void main(String[] args) throws Exception {
//        String fileName = "直邮海外+.txt";
//        double rate = 6.5189;
//        parseTxtFile(fileName, rate);
        String fileName = "BF20160315003017699222.txt";
        double rate = 6.5189;
        parseTxtFile(fileName, rate);
    }

    private static void parseTxtFile(String fileName, double rate) {
        //记时
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");
        TimeZone t = sdf.getTimeZone();
        t.setRawOffset(0);
        sdf.setTimeZone(t);
        Long startTime = System.currentTimeMillis();

        // 开始读取文件
        String filePath = "C:/Users/john/Desktop/";
        String writerFileName1 = "BF20160315003017699222-购汇还原申报明细.txt";
        String writerFileName2 = "BF20160315003017699222-付汇还原申报明细.txt";
        BufferedReader br = null;
        File file;
        file = new File(filePath, fileName);
        File file2 = new File(filePath, writerFileName1);
        File file3 = new File(filePath, writerFileName2);
        BufferedWriter bw = null;
        BufferedWriter bw2 = null;
        double totalOrderAmt = 0;
        try {
            br = new BufferedReader(new FileReader(file));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2, true), "UTF-8"));
            bw2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file3, true), "UTF-8"));
            String s;
            int index = 0;
            double orderAmt = 0;
            double orderAmt2 = 0;

            while ((s = br.readLine()) != null) {
                String[] columns = s.split("\\|", -1);
                if (columns.length <= 1) {// 排除空行
                    continue;
                }
                index++;
                System.out.println(index+" start");
                System.out.println(columns.length+" : "+s);
                System.out.println("-------------");
                //
                String orderAmtStr = columns[Constants.TransactionDetails.订单人民币金额];
                double orderAmtRMB = Double.valueOf(orderAmtStr);
                double orderAmtUSD = orderAmtRMB * 0.01 / rate;
                System.out.println("orderAmtUSD = "+orderAmtUSD);
                totalOrderAmt += orderAmtUSD;

                if(orderAmtUSD > 500) {
                    writerGouhuiFile(bw, orderAmtUSD, columns);
                }else {
                    orderAmt += orderAmtUSD;
                }
//                if (index > 1000){
//                    break;
//                }
                if(orderAmtUSD > 3000){
                    writerPaymentFile(rate, bw2, orderAmtUSD, columns);
                }else {
                    orderAmt2 += orderAmtUSD;
                }
            }
            writerFile1(bw, orderAmt);
            writerFile2(bw2, orderAmt2, rate);

            Long endTime = System.currentTimeMillis();
            System.out.println("======跨境付款解析文件结束====== 解析文件耗时 ：" + sdf.format(new Date(endTime - startTime)));

            String gouhuiName = "金额："+(long)Math.floor(totalOrderAmt)+"---购汇还原申报明细.txt";
            String paymentName = "金额："+(long)Math.floor(totalOrderAmt)+"---付汇还原申报明细.txt";
            file2.renameTo(new File(filePath, gouhuiName));
            file3.renameTo(new File(filePath, paymentName));
            System.out.println("totalOrderAmt: "+totalOrderAmt);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bw2 != null){
                try {
                    bw2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void writerGouhuiFile(BufferedWriter bw, double orderAmtUSD, String[] columns) throws IOException {
        String str = "20160315|BF20160315003017699222|3427BF" +
                columns[Constants.TransactionDetails.订单号] +
                columns[Constants.TransactionDetails.支付日期].substring(2) +
                (int) (Math.random() * 9000 + 1000) +
                "|GH|N|D|CHN||0|" +
                columns[Constants.TransactionDetails.支付人身份证号] +
                "|" +
                columns[Constants.TransactionDetails.支付人姓名] +
                "|" +
                columns[Constants.TransactionDetails.订单币种] +
                "|" +
                String.format("%.2f", orderAmtUSD) +
                "|18||跨境电子商务外汇支付业务";
        //插入购汇还原申报明细
        //写入文件
        bw.append(str);
        bw.newLine();
        bw.flush();
    }

    private static void writerPaymentFile(double rate, BufferedWriter bw2, double orderAmt2, String[] columns) throws IOException {
        //插入付汇还原申报明细
        String str = "PF20160315003017699222|3427PF" +
                columns[Constants.TransactionDetails.订单号] +
                columns[Constants.TransactionDetails.支付日期].substring(2) +
                ((int) (Math.random() * 9000 + 1000)) +
                "|||||||||0||C|567403601|||联动优势电子商务有限公司|" +
                columns[Constants.TransactionDetails.支付人姓名] +
                "|" +
                columns[Constants.TransactionDetails.订单币种] +
                "|" +
                String.format("%.2f", orderAmt2) +
                "|HKG|O|" +
                columns[Constants.TransactionDetails.交易编码] +
                "|" + (long) Math.floor(orderAmt2) + "|" +
                columns[Constants.TransactionDetails.交易附言] +
                "||||Y||" +
                rate +
                "|" +
                (long) Math.floor(orderAmt2) +
                "|7117510182600010929|||||T|王思思|58351122";

        //写入文件
        bw2.append(str);
        bw2.newLine();
        bw2.flush();
    }

    private static void writerFile1(BufferedWriter bw, double orderAmt) throws IOException {
        String str = "20160315|BF20160315003017699222|3427BF15093089016373150930" +
                (int) (Math.random() * 9000 + 1000) +
                "|GH|N|D|CHN||0|000000000567403601|联动优势电子商务有限公司|USD|"
                + String.format("%.2f", orderAmt) + "|18||跨境电子商务外汇支付业务";
        //写入文件
        bw.append(str);
        bw.newLine();
        bw.flush();
    }

    private static void writerFile2(BufferedWriter bw2, double orderAmt2, double rate) throws IOException {
        //插入付汇还原申报明细
        String str = ("PF20160315003017699222|3427PF15090681258994150905" +
                (int) (Math.random() * 9000 + 1000) +
                "|||||||||0||C|67403601|||联动优势电子商务有限公司|MITTY ASIA LIMITED|USD|" +
                String.format("%.2f", orderAmt2) +
                "|HKG|O|122030|" +
                (long) Math.floor(orderAmt2) +
                "|网络购物（不报关）||||Y||" +
                rate + "|" +
                (long) Math.floor(orderAmt2) +
                "|7117510182600010929|||||T|王思思|58351122");

        //写入文件
        bw2.append(str);
        bw2.newLine();
        bw2.flush();
    }

}
