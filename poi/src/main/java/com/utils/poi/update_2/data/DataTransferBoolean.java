package com.utils.poi.update_2.data;

public class DataTransferBoolean implements DataTransfer {

    @Override
    public String transfer(Object data) {
        if ((Boolean) data) {
            return "是";
        }
        return "否";
    }
}
