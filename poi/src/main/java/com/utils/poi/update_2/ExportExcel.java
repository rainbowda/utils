package com.utils.poi.update_2;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.utils.poi.update_2.bean.CellEntity;
import com.utils.poi.update_2.bean.SheetEntity;
import org.apache.commons.beanutils.PropertyUtils;
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

	/**
	 * 这里可以定义两个常量，但是这里需要workbook，所以就没有抽取出来
	 * @param workbook
	 */
	public ExportExcel(HSSFWorkbook workbook) {
		this(workbook,new DefaultTitleCellStyle(workbook),new DefaultDataCellStyle(workbook));
	}

	public ExportExcel(HSSFWorkbook workbook, AbstractCellStyle titleCellStyle, AbstractCellStyle dataCellStyle) {
		this.workbook = workbook;
		this.titleCellStyle = titleCellStyle;
		this.dataCellStyle = dataCellStyle;
	}

	public void exportExcel(SheetEntity sheetEntity, OutputStream out) {
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(sheetEntity.getSheetName());
		// 生成数据标题和数据行样式
		HSSFCellStyle rowTirtleStyle = titleCellStyle.getCellStyle();
		HSSFCellStyle rowDataStyle = dataCellStyle.getCellStyle();
		
		//创建数据标题和数据行
		createRowTitle(sheetEntity.getHeaders(), sheet, rowTirtleStyle);
		createRowData(sheetEntity.getCellEntitys(),sheetEntity.getDataset(), sheet, rowDataStyle);
		
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
	private void createRowData(List<CellEntity> cellEntitys, Collection<T> dataset, HSSFSheet sheet, HSSFCellStyle rowDataStyle) {
		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			HSSFRow row = sheet.createRow(index);
			T t = (T) it.next();
			for (int i = 0; i < cellEntitys.size(); i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(rowDataStyle);
				try {
					CellEntity cellEntity = cellEntitys.get(i);
					Object value = PropertyUtils.getProperty(t, cellEntity.getFiledName());
					if (value == null) {
						continue;
					}
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