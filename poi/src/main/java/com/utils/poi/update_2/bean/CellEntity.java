package com.utils.poi.update_2.bean;


import com.utils.poi.update_2.dataConversion.DataConversion;

public class CellEntity {
    private String title;
    private String fieldName;
    private DataConversion conversion;

    public CellEntity(String title, String fieldName) {
        this(title, fieldName, null);
    }

    public CellEntity(String title, String fieldName, DataConversion conversion) {
        this.title = title;
        this.fieldName = fieldName;
        this.conversion = conversion;
    }

    public String getTitle() {
        return title;
    }

    public String getFiledName() {
        return fieldName;
    }

    public DataConversion getConversion() {
        return conversion;
    }
}
