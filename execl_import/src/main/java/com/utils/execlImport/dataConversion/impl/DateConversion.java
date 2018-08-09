package com.utils.execlImport.dataConversion.impl;

import com.utils.execlImport.dataConversion.DataImportConversion;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConversion implements DataImportConversion<Date> {
    private SimpleDateFormat format;

    public DateConversion(String pattern) {
        this.format = new SimpleDateFormat(pattern);
    }

    @Override
    public Date transferData(Object data) {
        try {
            return format.parse(data.toString());
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
