import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;

public class Test {
	public static String add(String date, int gap) {
		String[] time = date.split("-");
		int year = Integer.parseInt(time[0]);
		int month = Integer.parseInt(time[1]);
		int day = Integer.parseInt(time[2]);
		year = year + gap;
		StringBuilder result = new StringBuilder();
		result.append(year);
		result.append("-");
		if(month < 10) {
			result.append("0");
		}
		result.append(month);
		result.append("-");
		if(day < 10) {
			result.append("0");
		}
		result.append(day);
		return result.toString();
	}
	private static void setSecondHead(HSSFRow head, HSSFCellStyle cellStyle) {
		
		HSSFCell cell = null;

		cell = head.createCell(0);
		cell.setCellValue("订单日期");
		cell.setCellStyle(cellStyle);

		cell = head.createCell(1);
		cell.setCellValue("合同号");
		cell.setCellStyle(cellStyle);

		cell = head.createCell(2);
		cell.setCellValue("型号");
		cell.setCellStyle(cellStyle);

		cell = head.createCell(3);
		cell.setCellValue("规格");
		cell.setCellStyle(cellStyle);

		cell = head.createCell(4);
		cell.setCellValue("产品编号");
		cell.setCellStyle(cellStyle);

		cell = head.createCell(5);
		cell.setCellValue("剩余数量");
		cell.setCellStyle(cellStyle);

		cell = head.createCell(6);
		cell.setCellValue("销售员");
		cell.setCellStyle(cellStyle);

		cell = head.createCell(7);
		cell.setCellValue("客户");
		cell.setCellStyle(cellStyle);

		cell = head.createCell(8);
		cell.setCellValue("交付日期");
		cell.setCellStyle(cellStyle);
	}
	
private static void setFirstHead(HSSFRow head, HSSFCellStyle cellStyle) {
		
		HSSFCell cell = null;

		cell = head.createCell(0);
		cell.setCellValue("订单日期");
		cell.setCellStyle(cellStyle);

		cell = head.createCell(1);
		cell.setCellValue("合同号");
		cell.setCellStyle(cellStyle);

		cell = head.createCell(2);
		cell.setCellValue("型号");
		cell.setCellStyle(cellStyle);

		cell = head.createCell(3);
		cell.setCellValue("规格");
		cell.setCellStyle(cellStyle);

		cell = head.createCell(4);
		cell.setCellValue("产品编号");
		cell.setCellStyle(cellStyle);

		cell = head.createCell(5);
		cell.setCellValue("剩余数量");
		cell.setCellStyle(cellStyle);

		cell = head.createCell(6);
		cell.setCellValue("销售员");
		cell.setCellStyle(cellStyle);

		cell = head.createCell(7);
		cell.setCellValue("客户");
		cell.setCellStyle(cellStyle);

		cell = head.createCell(8);
		cell.setCellValue("交付日期");
		cell.setCellStyle(cellStyle);
	}

	public static String minus(String date) {
		String[] time = date.split("-");
		int year = Integer.parseInt(time[0]);
		int month = Integer.parseInt(time[1]);
		int day = Integer.parseInt(time[2]);
		
		boolean isLeapYear = (year % 400 == 0) ||( (year % 4 == 0) && (year % 100 != 0));
		int[] map = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		if(isLeapYear) {
			map[2] += 1;
		}
		if(day == 1) {
			if(month == 1) {
				day = map[12];
				month = 12;
				year--;
			} else {
				month--;
				day = map[month];
			}
		} else {
			day--;
		}
		StringBuilder result = new StringBuilder();
		result.append(year);
		result.append("-");
		if(month < 10) {
			result.append("0");
		}
		result.append(month);
		result.append("-");
		if(day < 10) {
			result.append("0");
		}
		result.append(day);
		return result.toString();
	}
	public static void main(String[] args) throws IOException {
	    Scanner cin = new Scanner(System.in);
	    while(cin.hasNext()) {
	    	String date = cin.nextLine();
	    	System.out.println(minus(date));
	    }
	}

}
