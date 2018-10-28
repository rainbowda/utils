package com.utils.grayControl;

import com.utils.grayControl.bean.GrayControlBean;
import com.utils.grayControl.config.GrayControlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GrayControlUtils {

    @Autowired
    private GrayControlConfig grayControlConfig;


    /**
     * true表示拦截
     * false表示不拦截
     * @param grayControlKey
     * @param id
     * @return
     */
    public boolean isControl(String grayControlKey, Long id){

        GrayControlBean grayControlBean = grayControlConfig.getGrayControlBeanMap().get(grayControlKey);


        if (grayControlBean == null){
            return true;
        }

        if (!isOpen(grayControlBean.getOpen())){
            return false;
        }

        if (isInWhiteList(id,grayControlBean.getWhiteList())){
            return false;
        }

        if(isInMod(id, grayControlBean.getTargetMod())){
            return false;
        }


        return true;
    }

    /**
     * 是否开启灰度
     * @param open
     * @return
     */
    private boolean isOpen(Boolean open){
        if(open != null && open){
            return true;
        }

        return false;
    }


    /**
     * 取模
     * @param targetId
     * @param targetMod
     * @return
     */
    private boolean isInMod(Long targetId, Long targetMod){
        if((targetId == null) || (targetMod == null) || (targetId % targetMod != 0)){
            return false;
        }

        return true;
    }

    private boolean isInWhiteList(Long targetId, List<Long> whiteList){
        if (whiteList == null){
            return false;
        }

        if (!whiteList.contains(targetId)){
            return false;
        }

        return true;
    }




}
