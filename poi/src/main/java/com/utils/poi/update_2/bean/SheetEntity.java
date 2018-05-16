package com.utils.poi.update_2.bean;

import java.util.Collection;
import java.util.List;

public class SheetEntity {
    private String sheetName;

    private String[] headers;

    private String[] fieldNames;

    private List<CellEntity> cellEntitys;

    private Collection dataset;

    public List<CellEntity> getCellEntitys() {
        return cellEntitys;
    }

    public void setCellEntitys(List<CellEntity> cellEntitys) {
        this.cellEntitys = cellEntitys;
        //设置headers和fieldNames
        this.headers = new String[cellEntitys.size()];
        this.fieldNames = new String[cellEntitys.size()];
        for (int i = 0;i<cellEntitys.size();i++){
            this.headers[i] = cellEntitys.get(i).getTitle();
            this.fieldNames[i] = cellEntitys.get(i).getFiledName();
        }
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String[] getHeaders() {
        return headers;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public Collection getDataset() {
        return dataset;
    }

    public void setDataset(Collection dataset) {
        this.dataset = dataset;
    }
}
