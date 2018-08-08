package com.utils.execlExport.style;


import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class AbstractCellStyle {
    Workbook workbook;
    CellStyle style;
    Font font;

    public AbstractCellStyle(Workbook workbook) {
        this.workbook = workbook;
        style = workbook.createCellStyle();
        font = workbook.createFont();
    }

    protected abstract void setStyle();

    protected abstract void setFont();


    public CellStyle getCellStyle() {
        style.setFont(font);
        return style;
    }
}
