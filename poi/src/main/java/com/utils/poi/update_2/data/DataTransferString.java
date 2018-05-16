package com.utils.poi.update_2.data;

public class DataTransferString implements DataTransfer {

    @Override
    public String transfer(Object data) {
        return data.toString();
    }
}
