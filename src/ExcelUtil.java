
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Font;

public class ExcelUtil {
	private String srcPath;
	private String desPath;
	private static String[][] contents;
	private static int number;
	private static ArrayList<Task> taskset;

	public ExcelUtil() {
		srcPath = "C:/Users/Guanglei Pan/Desktop/orders.xls";
		desPath = "C:/Users/Guanglei Pan/Desktop/" + new SimpleDateFormat("yyyy-MM-dd HH").format(new Date())
				+ "_totaltask.xls";
		taskset = new ArrayList<Task>();
		number = 0;
	}

	public ExcelUtil(String src, String des) {
		srcPath = src;
		desPath = des;
	}

	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public void setDesPath(String desPath) {
		this.desPath = desPath;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public String getDesPath() {
		return desPath;
	}

	public String[][] getContents() {
		return contents;
	}

	// 对contents进行排序
	public void sort() {
		Arrays.sort(contents, new Comparator<String[]>() {
			@Override
			public int compare(String[] o1, String[] o2) {
				String date1 = o1[0];
				String date2 = o2[0];
				Date d1 = null, d2 = null;
				try {
					d1 = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
					d2 = new SimpleDateFormat("yyyy-MM-dd").parse(date2);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (d1.before(d2))
					return -1;
				if (d1.after(d2))
					return 1;
				return 0;
			}
		});
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 * @return
	 */

	public String getCellValue(HSSFCell cell) {
		String s = "";
		if (cell == null)
			return " ";
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				s = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:// 需要判断是数值还是日期
				if (HSSFDateUtil.isCellDateFormatted(cell))
					s = new SimpleDateFormat("yyyy-MM-dd").format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
				else
					s = String.valueOf((int) cell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				s = " ";
				break;
		}
		return s;
	}

	public void read() throws FileNotFoundException, IOException {
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(srcPath));
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFSheet sheet = wb.getSheetAt(0);
		int rows = sheet.getLastRowNum();
		String[][] content = new String[rows][9];
		int j = 0;
		HSSFRow row = null;
		for (int i = 1; i <= rows; i++) {
			row = sheet.getRow(i);
			if (!(getCellValue(row.getCell(16)).equals("0"))) {
				content[j][0] = getCellValue(row.getCell(4));
				content[j][1] = getCellValue(row.getCell(8));
				content[j][2] = getCellValue(row.getCell(10)).substring(9);
				content[j][3] = getCellValue(row.getCell(11)) + "," + getCellValue(row.getCell(12)) + ","
						+ getCellValue(row.getCell(13));
				content[j][4] = getCellValue(row.getCell(2));
				content[j][5] = getCellValue(row.getCell(16));
				content[j][6] = getCellValue(row.getCell(19));
				content[j][7] = getCellValue(row.getCell(20));
				content[j][8] = getCellValue(row.getCell(30));
				j++;
			}
		}
		number = j;
		contents = new String[j][9];
		for (int i = 0; i < j; i++) {
			for (int k = 0; k < 9; k++) {
				contents[i][k] = content[i][k];
			}
		}
		wb.close();
	}

	private void setHead(HSSFRow head, HSSFCellStyle cellStyle) {
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

	private void setRow(HSSFRow row, String[] s, HSSFCellStyle cellStyle) {
		HSSFCell cell = null;
		for (int i = 0; i < s.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(s[i]);
			cell.setCellStyle(cellStyle);
		}
	}

	private void setSheetColAuto(HSSFSheet sheet) {
		for (int i = 0; i < 9; i++)
			sheet.autoSizeColumn(i);
	}

	public void writeTotalTask() throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("总台账");

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
		HSSFRow head = sheet.createRow(0);
		setHead(head, cellStyle);

		int i = 1;
		HSSFRow row = null;
		while ((i - 1) < contents.length) {
			row = sheet.createRow(i);
			setRow(row, contents[i - 1], cellStyle);
			i++;
		}
		setSheetColAuto(sheet);

		sheet.setColumnWidth(2, (int) ((5 + 0.72) * 256));
		sheet.setColumnWidth(6, (int) ((10 + 0.72) * 256));
		sheet.setColumnWidth(7, (int) ((37 + 0.72) * 256));

		OutputStream out = new FileOutputStream(desPath);
		wb.write(out);
		out.close();
		wb.close();
	}

	public void writeSingleTask() throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheetA1 = wb.createSheet("A1");
		HSSFSheet sheetC2 = wb.createSheet("C2");
		HSSFSheet sheetC3 = wb.createSheet("C3");
		HSSFSheet sheetE1 = wb.createSheet("E1");
		HSSFSheet sheetE2 = wb.createSheet("E2");
		HSSFSheet sheetE3 = wb.createSheet("E3");
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
		HSSFRow head = sheetA1.createRow(0);
		setHead(head, cellStyle);
		head = sheetC2.createRow(0);
		setHead(head, cellStyle);
		head = sheetC3.createRow(0);
		setHead(head, cellStyle);
		head = sheetE1.createRow(0);
		setHead(head, cellStyle);
		head = sheetE2.createRow(0);
		setHead(head, cellStyle);
		head = sheetE3.createRow(0);
		setHead(head, cellStyle);
		HSSFRow row = null;
		int a1 = 1, c2 = 1, c3 = 1, e1 = 1, e2 = 1, e3 = 1;
		for (int i = 0; i < contents.length; i++) {
			String s = contents[i][2];
			if (s.equals("A1")) {
				row = sheetA1.createRow(a1++);
				setRow(row, contents[i], cellStyle);
			} else if (s.equals("C2")) {
				row = sheetC2.createRow(c2++);
				setRow(row, contents[i], cellStyle);
			} else if (s.equals("C3")) {
				row = sheetC3.createRow(c3++);
				setRow(row, contents[i], cellStyle);
			} else if (s.equals("E1")) {
				row = sheetE1.createRow(e1++);
				setRow(row, contents[i], cellStyle);
			} else if (s.equals("E2")) {
				row = sheetE2.createRow(e2++);
				setRow(row, contents[i], cellStyle);
			} else {
				row = sheetE3.createRow(e3++);
				setRow(row, contents[i], cellStyle);
			}
		}
		setSheetColAuto(sheetA1);
		setSheetColAuto(sheetC2);
		setSheetColAuto(sheetC3);
		setSheetColAuto(sheetE1);
		setSheetColAuto(sheetE2);
		setSheetColAuto(sheetE3);
		OutputStream out = new FileOutputStream("C:/Users/Guanglei Pan/Desktop/"
				+ new SimpleDateFormat("yyyy-MM-dd HH").format(new Date()) + "_singletask.xls");
		wb.write(out);
		out.close();
		wb.close();
	}

	public static ArrayList<Task> getTask() {
		String orderNo = contents[0][1];
		Product prod = new Product(contents[0][2], contents[0][3], contents[0][4], Integer.parseInt(contents[0][5]), Integer.parseInt(contents[0][5]));
		ArrayList<Product> list = new ArrayList<Product>();
		list.add(prod);
		int i = 1;
		int cnt = 0;
		for (i = 1; i < number; i++) {
			if (orderNo.equals(contents[i][1])) {
				Product temp = new Product(contents[i][2], contents[i][3], contents[i][4],
						Integer.parseInt(contents[i][5]), Integer.parseInt(contents[i][5]));
				list.add(temp);
			} else {
				// public Task(String startTime, String orderNo,String salesMan,
				// String client,String endDate, ArrayList<Product> require)
				Task task = new Task(contents[cnt][0], orderNo, contents[cnt][6], contents[cnt][7], contents[cnt][8],
						list);
				cnt = i;
				taskset.add(task);
				list = new ArrayList<Product>();
				orderNo = contents[i][1];
				Product temp = new Product(contents[i][2], contents[i][3], contents[i][4],
						Integer.parseInt(contents[i][5]), Integer.parseInt(contents[i][5]));
				list.add(temp);
			}
		}
		i--;
		Task task = new Task(contents[i][0], orderNo, contents[i][6], contents[i][7], contents[i][8], list);
		taskset.add(task);
		return taskset;
	}
	/**
	 * @param date
	 * @return 当前日期前一天的日期
	 */
	
	public String minus(String date) {
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
	/**
	 * 
	 * @param date
	 * @return 当前日期加一天后的日期
	 */
	public String add(String date) {
		String[] time = date.split("-");
		int year = Integer.parseInt(time[0]);
		int month = Integer.parseInt(time[1]);
		int day = Integer.parseInt(time[2]);
		
		boolean isLeapYear = (year % 400 == 0) ||( (year % 4 == 0) && (year % 100 != 0));
		int[] map = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		if(isLeapYear) {
			map[2] += 1;
		}
		if(day == map[month]) {
			day = 1;
			if(month == 12) {
				month = 1;
				year += 1;
			} else {
				month += 1;
			}
		} else {
			day++;
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
	public HashMap<String, Moulds> getResource(String currentDate, String endDate) {
		HashMap<String, Moulds> resource = new HashMap<String, Moulds>();
		for(String date = currentDate; date.compareTo(endDate) <= 0; date = add(date)) {
			resource.put(date, new Moulds());
		}
		return resource;
	}
	
	public HSSFSheet getPlanHeaderSheet(HSSFWorkbook wb, HSSFSheet sheet, int rowNumber) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);

		HSSFFont font = wb.createFont();  
		font.setFontHeightInPoints((short) 16);  
		font.setFontName("新宋体");  
		font.setColor(HSSFColor.BLACK.index);  
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);  
		
		//2.生成样式对象  
		HSSFCellStyle style = wb.createCellStyle();  
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
		style.setFont(font); //调用字体样式对象 
		
		sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, 0,  19));    
		HSSFRow row = sheet.createRow(rowNumber);
		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(style);
		
		cell.setCellValue("日计划完成");
		
		//1.生成字体对象  
		HSSFFont font1 = wb.createFont();  
		font1.setFontHeightInPoints((short) 10);  
		font1.setFontName("新宋体");   
		font1.setBoldweight(Font.BOLDWEIGHT_BOLD);  
		//2.生成样式对象  
		HSSFCellStyle style1 = wb.createCellStyle();  
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
		style1.setFont(font1); //调用字体样式对象 
				
		rowNumber++;
		HSSFRow row1 = sheet.createRow(rowNumber);
		HSSFRow row2 = sheet.createRow(rowNumber+1);
		HSSFCell cell1 = null;
		HSSFCell cell2 = null;
		
		
		sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber+1, 0, 0));  
		sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber+1, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber+1, 2, 2));
		cell = row1.createCell(0);
		cell.setCellStyle(style1);
		cell.setCellValue("型号");
		
		cell = row1.createCell(1);
		cell.setCellStyle(style1);
		cell.setCellValue("规格");
		
		cell = row1.createCell(2);
		cell.setCellStyle(style1);
		cell.setCellValue("编号");
		
		cell1 = row1.createCell(3);
		sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, 3,  6));  
		cell1.setCellValue("线圈");
		cell1.setCellStyle(style1);
		
		cell2 = row2.createCell(3);
		cell2.setCellStyle(style1);
		cell2.setCellValue("时间");
		
		cell2 = row2.createCell(4);
		cell2.setCellStyle(style1);
		cell2.setCellValue("计划数");
		
		cell2 = row2.createCell(5);
		cell2.setCellStyle(style1);
		cell2.setCellValue("实绩");
		
		cell2 = row2.createCell(6);
		cell2.setCellStyle(style1);
		cell2.setCellValue("结存");
		
		cell1 = row1.createCell(7);
		sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, 7,  10));  
		cell1.setCellValue("浇注");
		cell1.setCellStyle(style1);
		
		cell2 = row2.createCell(7);
		cell2.setCellStyle(style1);
		cell2.setCellValue("时间");
		
		cell2 = row2.createCell(8);
		cell2.setCellStyle(style1);
		cell2.setCellValue("计划数");
		
		cell2 = row2.createCell(9);
		cell2.setCellStyle(style1);
		cell2.setCellValue("实绩");
		
		cell2 = row2.createCell(10);
		cell2.setCellStyle(style1);
		cell2.setCellValue("结存");
		
		cell1 = row1.createCell(11);
		sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, 11,  14));  
		cell1.setCellValue("检验");
		cell1.setCellStyle(style1);
		
		cell2 = row2.createCell(11);
		cell2.setCellStyle(style1);
		cell2.setCellValue("时间");
		
		cell2 = row2.createCell(12);
		cell2.setCellStyle(style1);
		cell2.setCellValue("计划数");
		
		cell2 = row2.createCell(13);
		cell2.setCellStyle(style1);
		cell2.setCellValue("实绩");
		
		cell2 = row2.createCell(14);
		cell2.setCellStyle(style1);
		cell2.setCellValue("结存");
		
		cell1 = row1.createCell(15);
		sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, 15,  18));  
		cell1.setCellValue("成品入库");
		cell1.setCellStyle(style1);
		
		cell2 = row2.createCell(15);
		cell2.setCellStyle(style1);
		cell2.setCellValue("时间");
		
		cell2 = row2.createCell(16);
		cell2.setCellStyle(style1);
		cell2.setCellValue("计划数");
		
		cell2 = row2.createCell(17);
		cell2.setCellStyle(style1);
		cell2.setCellValue("实绩");
		
		cell2 = row2.createCell(18);
		cell2.setCellStyle(style1);
		cell2.setCellValue("结存");
			
		return sheet;
	}
	
	
	public HSSFSheet getOrderHeaderSheet(HSSFWorkbook wb, String id) {
		HSSFSheet sheet = wb.createSheet("生产计划单" + id);
		
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
//		HSSFRow head = sheet.createRow(0);
//		setFirstHead(head, cellStyle);
//		head = sheet.createRow(1);
//		setSecondHead(head, cellStyle);
		
		/*
      	sheet.addMergedRegion(new CellRangeAddress(     
      	1, //first row (0-based)  from 行     
      	2, //last row  (0-based)  to 行     
      	1, //first column (0-based) from 列     
      	1  //last column  (0-based)  to 列     
		)); 
		*/ 
		//1.生成字体对象  
		HSSFFont font = wb.createFont();  
		font.setFontHeightInPoints((short) 16);  
		font.setFontName("新宋体");  
		font.setColor(HSSFColor.BLACK.index);  
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);  
		
		//2.生成样式对象  
		HSSFCellStyle style = wb.createCellStyle();  
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
		style.setFont(font); //调用字体样式对象 
		
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,  8));  
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell1 = row.createCell(0);
		cell1.setCellStyle(style);
		cell1.setCellValue("客户订单信息");
		
		//1.生成字体对象  
		HSSFFont font1 = wb.createFont();  
		font1.setFontHeightInPoints((short) 10);  
		font1.setFontName("新宋体");   
		font1.setBoldweight(Font.BOLDWEIGHT_BOLD);  
		//2.生成样式对象  
		HSSFCellStyle style1 = wb.createCellStyle();  
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
		style1.setFont(font1); //调用字体样式对象 
				
		HSSFRow row1 = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));  
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 2, 2));
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 3, 3));
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 4, 4));
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 5, 5));
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 6, 6));
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 7, 7));
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 8, 8));
		
		
		HSSFCell cell = row1.createCell(0);
		cell.setCellStyle(style1);
		cell.setCellValue("订单日期");		
				
		cell = row1.createCell(1);	
		cell.setCellStyle(style1);
		cell.setCellValue("合同号");
		
		cell = row1.createCell(2);
		cell.setCellStyle(style1);
		cell.setCellValue("型号");
		
		cell = row1.createCell(3);
		cell.setCellStyle(style1);
		cell.setCellValue("规格");
		
		cell = row1.createCell(4);
		cell.setCellStyle(style1);
		cell.setCellValue("编号");
		
		cell = row1.createCell(5);
		cell.setCellStyle(style1);
		cell.setCellValue("数量");
		
		cell = row1.createCell(6);
		cell.setCellStyle(style1);
		cell.setCellValue("销售员");
		
		cell = row1.createCell(7);
		cell.setCellStyle(style1);
		cell.setCellValue("客户");
		
		cell = row1.createCell(8);
		cell.setCellStyle(style1);
		cell.setCellValue("交货日期");
			
		return sheet;
	}
	public void process(ArrayList<Task> taskset, String currentDate) throws IOException {
		//String startDate = taskset.get(0).getStartTime();
		String endDate = taskset.get(0).getEndDate();
		for(int i = 1; i < taskset.size(); i++) {
			String tempDate = taskset.get(i).getEndDate();
			if(tempDate.compareTo(endDate) >= 0) {
				endDate = tempDate;
			}
		}
		
		HashMap<String, Moulds> resource = new HashMap<String, Moulds>();
		resource = getResource(currentDate, endDate);
		
		/**
		 * 写入excel
		 */
		HSSFWorkbook wb = new HSSFWorkbook();
	
		Iterator<Task> it = taskset.iterator();
		while (it.hasNext()) {
			Task temp = it.next();
			ArrayList<Product> require = temp.getRequire();
			String begin = temp.getStartTime();
			String end = temp.getEndDate();
			LinkedHashMap<String, ArrayList<Product>> taskTab = new LinkedHashMap<String,  ArrayList<Product>>();
			begin = add(begin);// 第二天做线圈
			begin = add(begin);// 改天做线圈
			for(; begin.compareTo(end) < 0; begin = add(begin)) {
				Moulds m = resource.get(begin);
				ArrayList<Product> dayTask = new ArrayList<Product>();
				for(int i = 0; i < require.size(); i++) {
					Product prod = require.get(i);
					m.setType(prod.getModelNo());
					m.setConfigure(prod.getStandard());
					if(prod.getNumber() != 0) {//当所需产品还没完成时
						ArrayList<Capacity> res = m.match(prod.getNumber());
						if (res.size() != 0) {//可以分配，资源减去，任务减去
							
							//public Product(String modelNo, String standard, String serial, int number)
							int sum = 0;
							for(int k = 0; k < res.size(); k++) {
								Capacity capacity = res.get(k);
								sum += capacity.getModelNumber();
							}						
							Product prodTask = new Product(prod.getModelNo(), prod.getStandard(),
											prod.getSerial(), prod.getOriginalNumber(), 
											prod.getNumber(), sum, res);
							//System.out.println(prod.getNumber());
							prod.setNumber(prod.getNumber()-sum);
							dayTask.add(prodTask);
						}
					}
					
				}
				//记录每天的完成情况
				taskTab.put(begin, dayTask);
			}
			
			
			
			//判断任务是否已经完成，如果完成，则写入excel，如果没有，在txt文件中标注。
			if(isCompleted(temp)) {
				//设置客户订单信息的头部
				HSSFSheet sheet = getOrderHeaderSheet(wb, temp.getOrderNo());
				
				//记录该任务
				//输出 客户订单信息
				// 1 订单日期
				System.out.println("订单日期:" + temp.getStartTime());
				// 5 交货日期
				System.out.println("交货日期:" + temp.getEndDate());
				// 2 合同号
				System.out.println("合同号:" + temp.getOrderNo());
				// 3 销售员
				System.out.println("销售员:" + temp.getSalesMan());
				// 4 客户
				System.out.println("客户:" + temp.getClient());
			
				// 6 型号
				// 7 规格
				// 8 编号
				// 9 数量
				ArrayList<Product> req = temp.getRequire();
				
				HSSFRow hssfRow = null;
				HSSFCell hssfCell = null;
				int rowNumber = 3;
				for(int i = 0; i < req.size(); i++) {
					if((hssfRow = sheet.getRow(rowNumber)) == null)
						hssfRow = sheet.createRow(rowNumber);				
					Product product = req.get(i);
					
					//型号
					hssfCell = hssfRow.createCell(2);
					hssfCell.setCellValue(product.getModelNo());
					
					//规格
					hssfCell = hssfRow.createCell(3);
					hssfCell.setCellValue(product.getStandard());
					
					//编号
					hssfCell = hssfRow.createCell(4);
					hssfCell.setCellValue(product.getSerial());
					
					//数量
					hssfCell = hssfRow.createCell(5);
					hssfCell.setCellValue(product.getOriginalNumber());
					
					System.out.println("------------------------------");
					System.out.println("型号:" + product.getModelNo());					
					System.out.println("规格:" + product.getStandard());
					System.out.println("编号:" + product.getSerial());
					System.out.println("数量:" + product.getOriginalNumber());
					rowNumber++;
				}
				
				hssfRow = sheet.getRow(3);
				
				
				rowNumber--;
				if(rowNumber == 3) {
					hssfCell = hssfRow.createCell(0);
					hssfCell.setCellValue(temp.getStartTime());
					
					hssfCell = hssfRow.createCell(1);
					hssfCell.setCellValue(temp.getOrderNo());
					
					hssfCell = hssfRow.createCell(6);
					hssfCell.setCellValue(temp.getSalesMan());
					
					hssfCell = hssfRow.createCell(7);
					hssfCell.setCellValue(temp.getClient());
					
					hssfCell = hssfRow.createCell(8);
					hssfCell.setCellValue(temp.getEndDate());
				}else {
					//订单日期
					sheet.addMergedRegion(new CellRangeAddress(3, rowNumber, 0,  0)); 
					hssfCell = hssfRow.createCell(0);
					hssfCell.setCellValue(temp.getStartTime());
					
					//合同号
					sheet.addMergedRegion(new CellRangeAddress(3, rowNumber, 1,  1)); 
					hssfCell = hssfRow.createCell(1);
					hssfCell.setCellValue(temp.getOrderNo());
					
					//销售员
					sheet.addMergedRegion(new CellRangeAddress(3, rowNumber, 6,  6)); 
					hssfCell = hssfRow.createCell(6);
					hssfCell.setCellValue(temp.getSalesMan());
					
					//客户
					sheet.addMergedRegion(new CellRangeAddress(3, rowNumber, 7,  7)); 
					hssfCell = hssfRow.createCell(7);
					hssfCell.setCellValue(temp.getClient());
					
					//交货日期
					sheet.addMergedRegion(new CellRangeAddress(3, rowNumber, 8,  8)); 
					hssfCell = hssfRow.createCell(8);
					hssfCell.setCellValue(temp.getEndDate());
				
				}
				System.out.println("\n\nTask Table");
				System.out.println("************************************************************");
			
				//日计划完成 空三行
				rowNumber += 4;
				sheet = getPlanHeaderSheet(wb, sheet, rowNumber);
				
				rowNumber += 3;
				for(String date: taskTab.keySet()) {				
					ArrayList<Product> tempTask = taskTab.get(date);
					if(tempTask.size() != 0) {
						System.out.println("日期:" + date);
						hssfRow = sheet.createRow(rowNumber);
						//浇注时间
						hssfCell = hssfRow.createCell(8);
						hssfCell.setCellValue(date);
						for(int i = 0; i < tempTask.size(); i++) {
							hssfRow = sheet.createRow(rowNumber);
							Product p = tempTask.get(i);
							System.out.print("型号:" + p.getModelNo());						
							System.out.print("  规格:" + p.getStandard());
							System.out.print("  编号:" + p.getSerial());
							System.out.println("  计划数:" + p.getSum());
							//型号
							hssfCell = hssfRow.createCell(0);
							hssfCell.setCellValue(p.getModelNo());
							//规格
							hssfCell = hssfRow.createCell(1);
							hssfCell.setCellValue(p.getStandard());
							//编号
							hssfCell = hssfRow.createCell(2);
							hssfCell.setCellValue(p.getSerial());
							
							String dateTemp = minus(date);
							/***线圈***/							
							//时间
							hssfCell = hssfRow.createCell(3);
							hssfCell.setCellValue(dateTemp);
							//计划数
							hssfCell = hssfRow.createCell(4);
							hssfCell.setCellValue(p.getSum());
							//实绩
							hssfCell = hssfRow.createCell(5);
							hssfCell.setCellValue("");
							//结存
							hssfCell = hssfRow.createCell(6);
							hssfCell.setCellValue("");
							
							
							/***浇注***/							
							//时间
							hssfCell = hssfRow.createCell(7);
							hssfCell.setCellValue(date);
							//计划数
							hssfCell = hssfRow.createCell(8);
							hssfCell.setCellValue(p.getSum());
							//实绩
							hssfCell = hssfRow.createCell(9);
							hssfCell.setCellValue("");
							//结存
							hssfCell = hssfRow.createCell(10);
							hssfCell.setCellValue("");
							
							dateTemp = add(date);
							/***检验***/							
							//时间
							hssfCell = hssfRow.createCell(11);
							hssfCell.setCellValue(dateTemp);
							//计划数
							hssfCell = hssfRow.createCell(12);
							hssfCell.setCellValue(p.getSum());
							//实绩
							hssfCell = hssfRow.createCell(13);
							hssfCell.setCellValue("");
							//结存
							hssfCell = hssfRow.createCell(14);
							hssfCell.setCellValue("");
							
							
							/***成品入库***/							
							//时间
							hssfCell = hssfRow.createCell(15);
							hssfCell.setCellValue(dateTemp);
							//计划数
							hssfCell = hssfRow.createCell(16);
							hssfCell.setCellValue(p.getSum());
							//实绩
							hssfCell = hssfRow.createCell(17);
							hssfCell.setCellValue("");
							//结存
							hssfCell = hssfRow.createCell(18);
							hssfCell.setCellValue("");
							rowNumber++;
						}
						rowNumber++;
					}
					
				}
				System.out.println("************************************************************");
				for (int i = 0; i < 9; i++)
					sheet.autoSizeColumn(i);
			} else {
				//
				System.out.print(temp.getOrderNo());
				System.out.println("    估计不能完成");
				for(String date: taskTab.keySet()) {
					ArrayList<Product> dayTask = taskTab.get(date);
					for(int i = 0; i < dayTask.size(); i++) {
						Product prod = dayTask.get(i);
						ArrayList<Capacity> result = prod.getResult();
						Moulds mould = resource.get(date);
						//将相应型号的磨具资源还原
						for(int k = 0; k < result.size(); k++) {
							Capacity capacity = result.get(k);
							if(capacity.getModel().equals("A1")) {
								//System.out.println(capacity.getIndex());
								mould.A1capcity[capacity.getIndex()] += capacity.getModelNumber();
							}
							if(capacity.getModel().equals("C2")) {
								mould.E2capcity[capacity.getIndex()] += capacity.getModelNumber();
							}
							if(capacity.getModel().equals("C3")) {
								mould.C3capcity += capacity.getModelNumber();
							}
							if(capacity.getModel().equals("E1")) {
								mould.E1capcity[capacity.getIndex()] += capacity.getModelNumber();
							}
							if(capacity.getModel().equals("E2")) {
								mould.E2capcity[capacity.getIndex()] += capacity.getModelNumber();
							}
							if(capacity.getModel().equals("E3")) {
								mould.E3capcity[capacity.getIndex()] += capacity.getModelNumber();
							}							
						}
						
					}
				}
			}
			
			System.out.println("--------------------");
		}
		
		OutputStream out = new FileOutputStream("C:/Users/Guanglei Pan/Desktop/"
				+ new SimpleDateFormat("yyyy-MM-dd HH").format(new Date()) + "_TaskTable.xls");
		wb.write(out);
		out.close();
		wb.close();
	}
	/**
	 * 
	 * @param task
	 * @return 如果任务完成，则返回true,否则返回false
	 * 
	 */
	public boolean isCompleted(Task task) {
		ArrayList<Product> require = task.getRequire();
		for (int i = 0; i < require.size(); i++) {
			Product prod = require.get(i);
			if(prod.getNumber() != 0) {
				return false;
			}
		}
		return true;
	}
	public static void main(String[] args) throws FileNotFoundException, IOException {
		ExcelUtil eUtil = new ExcelUtil();
		eUtil.read();
		eUtil.sort();
		eUtil.writeTotalTask();
		eUtil.writeSingleTask();
		ArrayList<Task> taskset = ExcelUtil.getTask();		
		String currentDate = "2016-06-14";//今天的日期
		eUtil.process(taskset, currentDate);
	}
}
