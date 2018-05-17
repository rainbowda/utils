package com.utils.poi.update_2.bean;

import java.util.Collection;
import java.util.List;

public class SheetEntity {
    private String sheetName;

    private List<CellEntity> cellEntitys;

    private Collection dataset;

    public SheetEntity(String sheetName, List<CellEntity> cellEntitys, Collection dataset) {
        this.sheetName = sheetName;
        this.cellEntitys = cellEntitys;
        this.dataset = dataset;
    }

    public String getSheetName() {
        return sheetName;
    }

    public List<CellEntity> getCellEntitys() {
        return cellEntitys;
    }

    public Collection getDataset() {
        return dataset;
    }
}
