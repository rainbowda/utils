package com.utils.poi.update_2;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.utils.poi.update_2.style.AbstractCellStyle;
import com.utils.poi.update_2.style.DefaultDataCellStyle;
import com.utils.poi.update_2.style.DefaultTitleCellStyle;

@SuppressWarnings({"rawtypes","unchecked",})
public class ExportExcel<T> {
	private HSSFWorkbook workbook;
	private AbstractCellStyle titleCellStyle;//标题行样式
	private AbstractCellStyle dataCellStyle;//数据行样式
	
	public ExportExcel() {
		this(new HSSFWorkbook());
	}

	public ExportExcel(HSSFWorkbook workbook) {
		this(workbook,new DefaultTitleCellStyle(workbook),new DefaultDataCellStyle(workbook));
	}
	
	


	public ExportExcel(HSSFWorkbook workbook, AbstractCellStyle titleCellStyle, AbstractCellStyle dataCellStyle) {
		this.workbook = workbook;
		this.titleCellStyle = titleCellStyle;
		this.dataCellStyle = dataCellStyle;
	}

	public void exportExcel(String title, String[] headers, Collection<T> dataset, OutputStream out, String pattern) {
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		
		// 生成数据标题和数据行样式
		HSSFCellStyle rowTirtleStyle = titleCellStyle.getCellStyle();
		HSSFCellStyle rowDataStyle = dataCellStyle.getCellStyle();
		
		//创建数据标题和数据行
		createRowTitle(headers, sheet, rowTirtleStyle);
		createRowData(dataset, sheet, rowDataStyle);
		
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
	private void createRowData(Collection<T> dataset,  HSSFSheet sheet, HSSFCellStyle rowDataStyle) {
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
					if (value instanceof Boolean) {
						boolean bValue = (Boolean) value;
						textValue = "男";
						if (!bValue) {
							textValue = "女";
						}
					} else if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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

}