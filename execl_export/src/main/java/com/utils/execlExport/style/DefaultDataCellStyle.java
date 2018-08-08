package com.utils.execlExport.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class DefaultDataCellStyle extends AbstractCellStyle{

    public DefaultDataCellStyle(Workbook workbook) {
        super(workbook);
    }

    @Override
    protected void setStyle() {
        style.setAlignment(CellStyle.ALIGN_CENTER);
    }

    @Override
    protected void setFont() {

    }

}
