package com.utils.grayControl.config;

import com.utils.grayControl.bean.GrayControlBean;

import java.util.HashMap;
import java.util.Map;

public class GrayControlConfig {

    private Map<String, GrayControlBean> grayControlBeanMap = new HashMap<>();

    public Map<String, GrayControlBean> getGrayControlBeanMap() {
        return grayControlBeanMap;
    }

    public void setGrayControlBeanMap(Map<String, GrayControlBean> grayControlBeanMap) {
        this.grayControlBeanMap = grayControlBeanMap;
    }
}
