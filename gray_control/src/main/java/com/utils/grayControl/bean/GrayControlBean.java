package com.utils.grayControl.bean;

import java.util.List;

public class GrayControlBean {

    private Boolean open;

    private Long targetMod;

    private List<Long> whiteList;


    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Long getTargetMod() {
        return targetMod;
    }

    public void setTargetMod(Long targetMod) {
        this.targetMod = targetMod;
    }

    public List<Long> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<Long> whiteList) {
        this.whiteList = whiteList;
    }
}
