package com.utils.poi.update_2;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.utils.poi.update_2.bean.CellEntity;
import com.utils.poi.update_2.bean.SheetEntity;
import com.utils.poi.update_2.defaultDataHandle.DataHandlerFactory;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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
		createRowTitle(sheetEntity.getCellEntitys(), sheet, rowTirtleStyle);
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
					String textValue = null;

					CellEntity cellEntity = cellEntitys.get(i);
					Object value = PropertyUtils.getProperty(t, cellEntity.getFiledName());

					if (cellEntity.getConversion() == null)
						textValue = DataHandlerFactory.dataHandle(value);
					else
						textValue = cellEntity.getConversion().transferData(value);

					cell.setCellValue(textValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Description: 产生表格标题行
	 */
	private void createRowTitle(List<CellEntity> cellEntitys, HSSFSheet sheet, HSSFCellStyle rowTirtleStyle) {
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < cellEntitys.size(); i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(rowTirtleStyle);
			HSSFRichTextString text = new HSSFRichTextString(cellEntitys.get(i).getTitle());
			cell.setCellValue(text);
		}
	}

}