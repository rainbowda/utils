package com.utils.execl.export.style;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public class DefaultTitleCellStyle extends AbstractCellStyle {

    public DefaultTitleCellStyle(Workbook workbook) {
        super(workbook);
    }

    @Override
    protected void setStyle() {
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
    }

    @Override
    protected void setFont() {
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
    }

}