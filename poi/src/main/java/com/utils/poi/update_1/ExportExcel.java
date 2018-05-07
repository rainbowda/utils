package com.utils.poi.update_1;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.utils.poi.bean.Book;
import com.utils.poi.bean.Student;

@SuppressWarnings({"rawtypes","unchecked",})
public class ExportExcel<T> {
	private HSSFWorkbook workbook;
	
	public ExportExcel() {
		this(new HSSFWorkbook());
	}

	public ExportExcel(HSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public void exportExcel(Collection<T> dataset, OutputStream out) {
		exportExcel("测试POI导出EXCEL文档", null, dataset, out, "yyyy-MM-dd");
	}

	public void exportExcel(String[] headers, Collection<T> dataset, OutputStream out) {
		exportExcel("测试POI导出EXCEL文档", headers, dataset, out, "yyyy-MM-dd");
	}

	public void exportExcel(String[] headers, Collection<T> dataset, OutputStream out, String pattern) {
		exportExcel("测试POI导出EXCEL文档", headers, dataset, out, pattern);
	}

	public void exportExcel(String title, String[] headers, Collection<T> dataset, OutputStream out, String pattern) {
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		
		// 生成数据标题和数据行样式
		HSSFCellStyle rowTirtleStyle = getRowTitleStyle();
		HSSFCellStyle rowDataStyle = getRowDataStyle();
		
		//创建数据标题和数据行
		createRowTitle(headers, sheet, rowTirtleStyle);
		createRowData(dataset, pattern, sheet, rowDataStyle);
		
		//写入流
		writeExecl(out);
	}

	/**
	 * Description:写入到OutputStream
	 */
	private void writeExecl(OutputStream out) {
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description: 产生数据行
	 */
	private void createRowData(Collection<T> dataset, String pattern, HSSFSheet sheet, HSSFCellStyle rowDataStyle) {
		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			HSSFRow row = sheet.createRow(index);
			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(rowDataStyle);
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					// if (value instanceof Integer) {
					// int intValue = (Integer) value;
					// cell.setCellValue(intValue);
					// } else if (value instanceof Float) {
					// float fValue = (Float) value;
					// textValue = new HSSFRichTextString(
					// String.valueOf(fValue));
					// cell.setCellValue(textValue);
					// } else if (value instanceof Double) {
					// double dValue = (Double) value;
					// textValue = new HSSFRichTextString(
					// String.valueOf(dValue));
					// cell.setCellValue(textValue);
					// } else if (value instanceof Long) {
					// long longValue = (Long) value;
					// cell.setCellValue(longValue);
					// }
					if (value instanceof Boolean) {
						boolean bValue = (Boolean) value;
						textValue = "男";
						if (!bValue) {
							textValue = "女";
						}
					} else if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else {
						// 其它数据类型都当作字符串简单处理
						textValue = value.toString();
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(textValue);
							HSSFFont font3 = workbook.createFont();
							font3.setColor(HSSFColor.BLUE.index);
							richString.applyFont(font3);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}
		}
	}

	/**
	 * Description: 产生表格标题行
	 */
	private void createRowTitle(String[] headers, HSSFSheet sheet, HSSFCellStyle rowTirtleStyle) {
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(rowTirtleStyle);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
	}

	/**
	 * Description:生成数据标题样式
	 */
	private HSSFCellStyle getRowTitleStyle() {
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		// 把字体应用到当前的样式
		style.setFont(font);
		
		return style;
	}
	
	/**
	 * Description:生成数据行样式
	 */
	private HSSFCellStyle getRowDataStyle() {
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		// 生成另一个字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		
		style.setFont(font);
				
		return style;
	}

	public static void main(String[] args) {
		// 测试学生
		ExportExcel<Student> ex = new ExportExcel<Student>();
		String[] headers = { "学号", "姓名", "年龄", "性别", "出生日期" };
		List<Student> dataset = new ArrayList<Student>();
		dataset.add(new Student(10000001, "张三", 20, true, new Date()));
		dataset.add(new Student(20000002, "李四", 24, false, new Date()));
		dataset.add(new Student(30000003, "王五", 22, true, new Date()));
		// 测试图书
		ExportExcel<Book> ex2 = new ExportExcel<Book>();
		String[] headers2 = { "图书编号", "图书名称", "图书作者", "图书价格", "图书ISBN", "图书出版社", "封面图片" };
		List<Book> dataset2 = new ArrayList<Book>();
		try {
			dataset2.add(new Book(1, "jsp", "leno", 300.33f, "1234567", "清华出版社"));
			dataset2.add(new Book(2, "java编程思想", "brucl", 300.33f, "1234567", "阳光出版社"));
			dataset2.add(new Book(3, "DOM艺术", "lenotang", 300.33f, "1234567", "清华出版社"));
			dataset2.add(new Book(4, "c++经典", "leno", 400.33f, "1234567", "清华出版社"));
			dataset2.add(new Book(5, "c#入门", "leno", 300.33f, "1234567", "汤春秀出版社"));

			OutputStream out = new FileOutputStream("E://a.xls");
			OutputStream out2 = new FileOutputStream("E://b.xls");
			ex.exportExcel(headers, dataset, out);
			ex2.exportExcel(headers2, dataset2, out2);
			out.close();
			out2.close();
			JOptionPane.showMessageDialog(null, "导出成功!");
			System.out.println("excel导出成功！");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}