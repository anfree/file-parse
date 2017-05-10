package org.zeng;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ReaderTxtFile {

	public static void main(String[] args) throws Exception {
        //记时
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");
		TimeZone t = sdf.getTimeZone();
		t.setRawOffset(0);
		sdf.setTimeZone(t);
		Long startTime = System.currentTimeMillis();

		// 开始读取文件
		File file;
		// List<Integer> lstIndex = new ArrayList<Integer>();
		// String atoi = "abc";
		BufferedReader br = null;
		List<String> list; // 放置读取的内容
		try {
			file = new File("C:/Users/john/Desktop/直邮海外+.txt");
			br = new BufferedReader(new FileReader(file));
			list = new ArrayList<>();
			String s;
			int index = 0;
			while (null != (s = br.readLine())) {
				index++;
				// if (s.contains(atoi)) {
				System.out.println(index+" start");
				// lstIndex.add(index);
				// }
				System.out.println(s);
				list.add(s);
				String[] columns = s.split("\\|", -1);
				System.out.println(columns.length);
                if (columns.length <= 1) {// 排除空行
                    continue;
                }
                for (String column : columns) {
                    System.out.println(column + "=============");
                }
                System.out.println("-------------");

			}

			Long endTime = System.currentTimeMillis();
			System.out.println("======跨境付款解析文件结束====== 解析文件耗时 ：" + sdf.format(new Date(endTime - startTime)));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
