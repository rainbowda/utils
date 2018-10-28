package com.utils.grayControl.config;

import com.google.gson.Gson;
import com.utils.grayControl.bean.GrayControlBean;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class GrayControlConfig {

    private Map<String, GrayControlBean> grayControlBeanMap = new HashMap<>();

    public Map<String, GrayControlBean> getGrayControlBeanMap() {
        return grayControlBeanMap;
    }

    public void setGrayControlBeanMap(Map<String, GrayControlBean> grayControlBeanMap) {
        this.grayControlBeanMap = grayControlBeanMap;
    }

    /**
     * 模拟数据来源
     * 初始化数据
     */
    @PostConstruct
    public void init(){
//        String json = "{\"gray1\":{\"targetMod\":3,\"whiteList\":[100232],\"open\":true},\"gray2\":{\"targetMod\":3,\"whiteList\":[100232],\"open\":true}}";
//        Gson gson = new Gson();
//        Map<String, GrayControlBean> map = gson.fromJson(json, Map.class);


        GrayControlBean grayControlBean1 = new GrayControlBean();
        GrayControlBean grayControlBean2 = new GrayControlBean();

        grayControlBean1.setOpen(true);
        grayControlBean2.setOpen(true);

        grayControlBean1.setTargetMod(30000L);
        grayControlBean2.setTargetMod(40000L);

        grayControlBean1.setWhiteList(Arrays.asList(100232L));
        grayControlBean2.setWhiteList(Arrays.asList());

        Map<String, GrayControlBean> grayControlBeanMap = new HashMap<>();
        grayControlBeanMap.put("gray1",grayControlBean1);
        grayControlBeanMap.put("gray2",grayControlBean2);

        setGrayControlBeanMap(grayControlBeanMap);


        System.out.println(new Gson().toJson(grayControlBeanMap));
    }
}
