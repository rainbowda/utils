package com.utils.poi.update_2.bean;

import com.utils.poi.update_2.data.DataTransfer;
import com.utils.poi.update_2.data.DataTransferString;

public class CellEntity {
    private String title;
    private String filedName;
    private DataTransfer dataTransfer;

    public CellEntity(String title, String filedName) {
        this(title, filedName, new DataTransferString());
    }

    public CellEntity(String title, String filedName, DataTransfer dataTransfer) {
        this.title = title;
        this.filedName = filedName;
        this.dataTransfer = dataTransfer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

}
