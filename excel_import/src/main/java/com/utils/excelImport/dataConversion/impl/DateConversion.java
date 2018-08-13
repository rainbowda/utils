package com.utils.excelImport.dataConversion.impl;

import com.utils.excelImport.dataConversion.DataImportConversion;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConversion implements DataImportConversion<Date> {
    private SimpleDateFormat format;

    public DateConversion(String pattern) {
        this.format = new SimpleDateFormat(pattern);
    }

    @Override
    public Date transferData(Object data) {
        //如果data原本就是日期类型，则直接返回
        if(data instanceof Date){
            return (Date)data;
        }

        try {
            return format.parse(data.toString());
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
