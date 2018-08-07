package vn.toancauxanh.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.zul.Filedownload;

public class ExcelUtil {

	private static ExcelUtil instance;

	public static ExcelUtil getInStance() {
		if (instance == null) {
			instance = new ExcelUtil();
		}
		return instance;
	}
	
	public static String formatDouble(double number) {
		//#.###,##
		final DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
		decimalFormatSymbols.setDecimalSeparator(',');
		decimalFormatSymbols.setGroupingSeparator('.');
		final DecimalFormat decimalFormat = new DecimalFormat("#.###", decimalFormatSymbols);
		return decimalFormat.format(number);
	}
	
	private static void setBorderMore(Workbook wb, Row row, Cell c, int begin, int end, int fontSize) {
		final CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setBorderLeft((short) 1); 
		Font font = wb.createFont();
		font.setFontName("Times New Roman");
		
	}
	
	private static void setBorderMore(int flag, Workbook wb, Row row, Cell c, int begin, int end, int fontSize) {
		final CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setBorderLeft((short) 1); 
		
		Font font = wb.createFont();
		font.setFontName("Times New Roman");
		for (int i = begin; i < end; i++) {
			Cell c1 = row.createCell(i);
			if (flag==1) {
				cellStyle.setBorderTop((short) 2);
				font.setFontHeightInPoints((short) fontSize);
				cellStyle.setFont(font);
			} else {
				cellStyle.setBorderTop((short) 1);
			}
			if (flag==2) {
				cellStyle.setBorderBottom((short) 2); 
				font.setFontHeightInPoints((short) fontSize);
				cellStyle.setFont(font);
			} else {
				cellStyle.setBorderBottom((short) 1); 
			}
			
			if (i == (end-1)) {
				cellStyle.setBorderRight((short) 2);
			} else {
				cellStyle.setBorderRight((short) 1); 
			}
			if (flag==3) {
				cellStyle.setBorderTop((short) 1);
				cellStyle.setBorderBottom((short) 1); 
				font.setFontHeightInPoints((short) fontSize);
				cellStyle.setFont(font);
				cellStyle.setBorderRight((short) 1); 
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			} 
			c1.setCellStyle(cellStyle);
		}
	}
	
	public static CellStyle setBorderAndFont(final Workbook workbook,
			final int borderSize, final boolean isTitle, final int fontSize,
			final String fontColor, final String textAlign) {
		final CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setWrapText(true);
		

		cellStyle.setBorderTop((short) borderSize); // single line border
		cellStyle.setBorderBottom((short) borderSize); // single line border
		cellStyle.setBorderLeft((short) borderSize); // single line border
		cellStyle.setBorderRight((short) borderSize); // single line border
		cellStyle.setAlignment(CellStyle.VERTICAL_CENTER);

		if (textAlign.equals("RIGHT")) {
			cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		} else if (textAlign.equals("CENTER")) {
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		} else if (textAlign.equals("LEFT")) {
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		}
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		final Font font = workbook.createFont();
		font.setFontName("Times New Roman");
		if (isTitle) {
			
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			
		} 
		if (fontColor.equals("RED")) {
			font.setColor(Font.COLOR_RED);
		} else if (fontColor.equals("BLUE")) {
			font.setColor(HSSFColor.BLUE.index);
		} else if (fontColor.equals("ORANGE")){
			font.setColor(HSSFColor.ORANGE.index);
		} else {
			
		}
		font.setFontHeightInPoints((short) fontSize);
		cellStyle.setFont(font);

		return cellStyle;
	}
	
	public static void exportThongKe(String title, String fileName, 
			String sheetname, List<Object[]> list) throws IOException {
		// New Workbook
		Workbook wb = new XSSFWorkbook();
		try {
		Cell c = null;
		// New Sheet
		Sheet sheet1 = null;
		sheet1 = wb.createSheet(sheetname);
		// Row and column indexes
		int idx = 0;
		// Generate column headings
		Row row;
		row = sheet1.createRow(idx);
		c = row.createCell(0);
		c.setCellValue(title);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(1);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(2);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(3);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(4);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
		// set column width
		sheet1.setColumnWidth(0, 30 * 256);
		sheet1.setColumnWidth(1, 16 * 256);
		sheet1.setColumnWidth(2, 16 * 256);
		sheet1.setColumnWidth(3, 16 * 256);
		sheet1.setColumnWidth(4, 16 * 256);
		sheet1.setColumnWidth(5, 16 * 256);
		// Generate rows header of grid
		idx++;
		row = sheet1.createRow(idx);
		c = row.createCell(0);
		c.setCellValue("Tên đơn vị");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		c = row.createCell(1);
		c.setCellValue("Việc đã giao");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		c = row.createCell(2);
		c.setCellValue("Chưa xử lý");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		c = row.createCell(3);
		c.setCellValue("Đã xử lý");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		c = row.createCell(4);
		c.setCellValue("Trễ hạn");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		
		idx++;
		int i = 1;
		CellStyle cellStyleLeft = setBorderAndFont(wb, 1, i == list.size(), 11, "","LEFT");
		CellStyle cellStyleCenter = setBorderAndFont(wb, 1, i == list.size(), 11, "","CENTER");
		for (Object[] ob: list) {
			row = sheet1.createRow(idx);
			c = row.createCell(0);			
			c.setCellValue(ob[0] + "");
			c.setCellStyle(cellStyleLeft);				
			c = row.createCell(1);
			c.setCellValue(ob[1] + "");
			c.setCellStyle(cellStyleCenter);				
			c = row.createCell(2);
			c.setCellValue(ob[2] + "");
			c.setCellStyle(cellStyleCenter);		
			c = row.createCell(3);
			c.setCellValue(ob[3] + "");
			c.setCellStyle(cellStyleCenter);		
			c = row.createCell(4);
			c.setCellValue(ob[4] + "");
			c.setCellStyle(cellStyleCenter);
			i++;
			idx++;
		}
		
		ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
		wb.write(fileOut);
		Filedownload.save(new ByteArrayInputStream(fileOut.toByteArray()),"application/octet-stream", fileName + ".xlsx");
		} finally {
			wb.close();
		}
	}
	
	public static void exportThongKeChiTiet(String title, String fileName, 
			String sheetname, List<Object[]> list) throws IOException {
		// New Workbook
		Workbook wb = new XSSFWorkbook();
		try {
		Cell c = null;
		// New Sheet
		Sheet sheet1 = null;
		sheet1 = wb.createSheet(sheetname);
		// Row and column indexes
		int idx = 0;
		// Generate column headings
		Row row;
		row = sheet1.createRow(idx);
		c = row.createCell(0);
		c.setCellValue(title);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(1);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(2);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(3);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(4);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(5);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
		// set column width
		sheet1.setColumnWidth(0, 7 * 256);
		sheet1.setColumnWidth(1, 30 * 256);
		sheet1.setColumnWidth(2, 16 * 256);
		sheet1.setColumnWidth(3, 16 * 256);
		sheet1.setColumnWidth(4, 16 * 256);
		sheet1.setColumnWidth(5, 16 * 256);
		sheet1.setColumnWidth(6, 16 * 256);
		// Generate rows header of grid
		idx++;
		row = sheet1.createRow(idx);
		c = row.createCell(0);
		c.setCellValue("STT");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		c = row.createCell(1);
		c.setCellValue("Nội dung công việc");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		c = row.createCell(2);
		c.setCellValue("Văn bản chỉ đạo");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		c = row.createCell(3);
		c.setCellValue("Loại công việc");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		c = row.createCell(4);
		c.setCellValue("Thời hạn báo cáo");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		c = row.createCell(5);
		c.setCellValue("Tình trạng");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		
		idx++;
		int i = 1;
		CellStyle cellStyleLeft = setBorderAndFont(wb, 1, i == list.size(), 11, "","LEFT");
		CellStyle cellStyleCenter = setBorderAndFont(wb, 1, i == list.size(), 11, "","CENTER");
		for (Object[] ob: list) {
			row = sheet1.createRow(idx);
			c = row.createCell(0);			
			c.setCellValue(ob[0] + "");
			c.setCellStyle(cellStyleCenter);				
			c = row.createCell(1);
			c.setCellValue(ob[1] + "");
			c.setCellStyle(cellStyleLeft);				
			c = row.createCell(2);
			c.setCellValue(ob[2] + "");
			c.setCellStyle(cellStyleCenter);		
			c = row.createCell(3);
			c.setCellValue(ob[3] + "");
			c.setCellStyle(cellStyleLeft);		
			c = row.createCell(4);
			c.setCellValue(ob[4] + "");
			c.setCellStyle(cellStyleCenter);
			c = row.createCell(5);
			c.setCellValue(ob[5] + "");
			c.setCellStyle(cellStyleCenter);
			i++;
			idx++;
		}
		
		ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
		wb.write(fileOut);
		Filedownload.save(new ByteArrayInputStream(fileOut.toByteArray()),"application/octet-stream", fileName + ".xlsx");
		} finally {
			wb.close();
		}
	}
	
	public static void exportChiDaoDieuHanh(String title, String fileName, 
			String sheetname, List<Object[]> list) throws IOException {
		// New Workbook
		Workbook wb = new XSSFWorkbook();
		try {
		Cell c = null;
		// New Sheet
		Sheet sheet1 = null;
		sheet1 = wb.createSheet(sheetname);
		// Row and column indexes
		int idx = 0;
		// Generate column headings
		Row row;
		row = sheet1.createRow(idx);
		c = row.createCell(0);
		c.setCellValue(title);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(1);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(2);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(3);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(4);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
		// set column width
		sheet1.setColumnWidth(0, 16 * 256);
		sheet1.setColumnWidth(1, 30 * 256);
		sheet1.setColumnWidth(2, 36 * 256);
		sheet1.setColumnWidth(3, 16 * 256);
		sheet1.setColumnWidth(4, 16 * 256);
		// Generate rows header of grid
		idx++;
		row = sheet1.createRow(idx);
		c = row.createCell(0);
		c.setCellValue("STT");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));	
		c = row.createCell(1);
		c.setCellValue("Nội dung chỉ đạo");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		c = row.createCell(2);
		c.setCellValue("Đơn vị thực hiện");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		c = row.createCell(3);
		c.setCellValue("Thời hạn báo cáo");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		c = row.createCell(4);
		c.setCellValue("Tình trạng xử lý");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		
		idx++;
		int i = 1;
		CellStyle cellStyleLeft = setBorderAndFont(wb, 1, i == list.size(), 11, "","LEFT");
		CellStyle cellStyleCenter = setBorderAndFont(wb, 1, i == list.size(), 11, "","CENTER");
		for (Object[] ob: list) {
			row = sheet1.createRow(idx);
			c = row.createCell(0);			
			c.setCellValue(ob[0] + "");
			c.setCellStyle(cellStyleCenter);				
			c = row.createCell(1);
			c.setCellValue(ob[1] + "");
			c.setCellStyle(cellStyleLeft);				
			c = row.createCell(2);
			c.setCellValue(ob[2] + "");
			c.setCellStyle(cellStyleLeft);		
			c = row.createCell(3);
			c.setCellValue(ob[3] + "");
			c.setCellStyle(cellStyleCenter);	
			c = row.createCell(4);
			c.setCellValue(ob[4] + "");
			c.setCellStyle(cellStyleCenter);
			i++;
			idx++;
		}
		
		ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
		wb.write(fileOut);
		Filedownload.save(new ByteArrayInputStream(fileOut.toByteArray()),"application/octet-stream", fileName + ".xlsx");
		} finally {
			wb.close();
		}
	}
	public static void exportDonThu(String title, String fileName, 
			String sheetname, List<Object[]> list) throws IOException {
		// New Workbook
		Workbook wb = new XSSFWorkbook();
		try {
		Cell c = null;
		// New Sheet
		Sheet sheet1 = null;
		sheet1 = wb.createSheet(sheetname);
		// Row and column indexes
		int idx = 0;
		// Generate column headings
		Row row;
		row = sheet1.createRow(idx);
		c = row.createCell(0);
		c.setCellValue(title);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(1);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(2);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(3);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(4);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(5);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(6);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(7);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
		// set column width
		sheet1.setColumnWidth(0, 7 * 256);
		sheet1.setColumnWidth(1, 30 * 256);
		sheet1.setColumnWidth(2, 16 * 256);
		sheet1.setColumnWidth(3, 16 * 256);
		sheet1.setColumnWidth(4, 20 * 256);
		sheet1.setColumnWidth(5, 30 * 256);
		sheet1.setColumnWidth(6, 16 * 256);
		sheet1.setColumnWidth(7, 16 * 256);
		// Generate rows header of grid
		idx++;
		row = sheet1.createRow(idx);
		c = row.createCell(0);
		c.setCellValue("STT");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));	
		c = row.createCell(1);
		c.setCellValue("Nội dung đơn thư");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		c = row.createCell(2);
		c.setCellValue("Ngày nhận");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));	
		c = row.createCell(3);
		c.setCellValue("Người đứng đơn");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));	
		c = row.createCell(4);
		c.setCellValue("Lĩnh vực đơn");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));
		c = row.createCell(5);
		c.setCellValue("Đơn vị");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));	
		c = row.createCell(6);
		c.setCellValue("Thời hạn báo cáo");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		c = row.createCell(7);
		c.setCellValue("Tình trạng xử lý");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		
		idx++;
		CellStyle cellStyleLeft = setBorderAndFont(wb, 1, false, 11, "","LEFT");
		CellStyle cellStyleCenter = setBorderAndFont(wb, 1, false, 11, "","CENTER");
		for (Object[] ob: list) {
			row = sheet1.createRow(idx);
			c = row.createCell(0);			
			c.setCellValue(ob[0] + "");
			c.setCellStyle(cellStyleCenter);				
			c = row.createCell(1);
			c.setCellValue(ob[1] + "");
			c.setCellStyle(cellStyleLeft);				
			c = row.createCell(2);
			c.setCellValue(ob[2] + "");
			c.setCellStyle(cellStyleCenter);		
			c = row.createCell(3);
			c.setCellValue(ob[3] + "");
			c.setCellStyle(cellStyleCenter);	
			c = row.createCell(4);
			c.setCellValue(ob[4] + "");
			c.setCellStyle(cellStyleCenter);
			c = row.createCell(5);
			c.setCellValue(ob[5] + "");
			c.setCellStyle(cellStyleLeft);
			c = row.createCell(6);
			c.setCellValue(ob[6] + "");
			c.setCellStyle(cellStyleCenter);
			c = row.createCell(7);
			c.setCellValue(ob[7] + "");
			c.setCellStyle(cellStyleCenter);
			idx++;
		}
		
		ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
		wb.write(fileOut);
		Filedownload.save(new ByteArrayInputStream(fileOut.toByteArray()),"application/octet-stream", fileName + ".xlsx");
		} finally {
			wb.close();
		}
	}
	public static void exportYKienPhanAnh(String title, String fileName, 
			String sheetname, List<Object[]> list) throws IOException {
		// New Workbook
		Workbook wb = new XSSFWorkbook();
		try {
		Cell c = null;
		// New Sheet
		Sheet sheet1 = null;
		sheet1 = wb.createSheet(sheetname);
		// Row and column indexes
		int idx = 0;
		// Generate column headings
		Row row;
		row = sheet1.createRow(idx);
		c = row.createCell(0);
		c.setCellValue(title);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(1);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(2);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(3);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		c = row.createCell(4);
		c.setCellStyle(setBorderAndFont(wb, 1, true, 14, "BLUE", "CENTER"));
		sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
		// set column width
		sheet1.setColumnWidth(0, 7 * 256);
		sheet1.setColumnWidth(1, 30 * 256);
		sheet1.setColumnWidth(2, 30 * 256);
		sheet1.setColumnWidth(3, 16 * 256);
		sheet1.setColumnWidth(4, 16 * 256);
		// Generate rows header of grid
		idx++;
		row = sheet1.createRow(idx);
		c = row.createCell(0);
		c.setCellValue("STT");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));	
		c = row.createCell(1);
		c.setCellValue("Nội dung kiến nghị");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		c = row.createCell(2);
		c.setCellValue("Đơn vị thực hiện");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		c = row.createCell(3);
		c.setCellValue("Thời hạn báo cáo");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		c = row.createCell(4);
		c.setCellValue("Tình trạng xử lý");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));		
		
		idx++;
		CellStyle cellStyleLeft = setBorderAndFont(wb, 1, false, 11, "","LEFT");
		CellStyle cellStyleCenter = setBorderAndFont(wb, 1, false, 11, "","CENTER");
		for (Object[] ob: list) {
			row = sheet1.createRow(idx);
			c = row.createCell(0);			
			c.setCellValue(ob[0] + "");
			c.setCellStyle(cellStyleCenter);				
			c = row.createCell(1);
			c.setCellValue(ob[1] + "");
			c.setCellStyle(cellStyleLeft);				
			c = row.createCell(2);
			c.setCellValue(ob[2] + "");
			c.setCellStyle(cellStyleLeft);		
			c = row.createCell(3);
			c.setCellValue(ob[3] + "");
			c.setCellStyle(cellStyleCenter);	
			c = row.createCell(4);
			c.setCellValue(ob[4] + "");
			c.setCellStyle(cellStyleCenter);
			idx++;
		}
		
		ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
		wb.write(fileOut);
		Filedownload.save(new ByteArrayInputStream(fileOut.toByteArray()),"application/octet-stream", fileName + ".xlsx");
		} finally {
			wb.close();
		}
	}
}