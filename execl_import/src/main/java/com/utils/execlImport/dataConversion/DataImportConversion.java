package com.utils.execlImport.dataConversion;

public interface DataImportConversion<T> {
    T transferData(Object data);
}