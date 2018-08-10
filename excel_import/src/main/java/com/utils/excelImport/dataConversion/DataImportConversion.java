package com.utils.excelImport.dataConversion;

public interface DataImportConversion<T> {
    T transferData(Object data);
}