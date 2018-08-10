package com.utils.excelImport.data;


import com.utils.excelImport.dataConversion.DataImportConversion;

public class CellParam {
    private String fieldName;
    private DataImportConversion conversion;

    public CellParam(String fieldName) {
        this(fieldName,null);
    }

    public CellParam(String fieldName, DataImportConversion conversion) {
        this.fieldName = fieldName;
        this.conversion = conversion;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public DataImportConversion getConversion() {
        return conversion;
    }

    public void setConversion(DataImportConversion conversion) {
        this.conversion = conversion;
    }
}
