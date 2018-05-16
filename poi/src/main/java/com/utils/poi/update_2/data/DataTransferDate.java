package com.utils.poi.update_2.data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTransferDate implements DataTransfer {

    @Override
    public String transfer(Object data) {
        Date date = (Date) data;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
