package com.utils.poi.update_2.style;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public abstract class AbstractCellStyle {
	HSSFWorkbook workbook;
	HSSFCellStyle style;
	HSSFFont font;

	public AbstractCellStyle(HSSFWorkbook workbook) {
		this.workbook = workbook;
		style = workbook.createCellStyle();
		font = workbook.createFont();
	}
	
	public abstract void setStyle();
	
	public abstract void setFont();

	
	public HSSFCellStyle getCellStyle() {
		style.setFont(font);
		return style;
	}
}
