package com.utils.excelExport.dataConversion;

public interface DataExportConversion<T> {
    String transferData(T data);
}
