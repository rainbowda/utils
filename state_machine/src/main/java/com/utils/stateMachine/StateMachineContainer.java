package com.utils.stateMachine;


import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 存放状态机实现的容器
 */
@Component
public class StateMachineContainer {

    private final static Map<String, AbstractStateMachine> STATE_MACHINE_MAP = new HashMap<>();

    /**
     * 往容器中注册状态机
     * @param stateMachineKey
     * @param stateMachine
     */
    public void registerStateMachine(String stateMachineKey, AbstractStateMachine stateMachine){
        if (StringUtils.isBlank(stateMachineKey) || stateMachine == null){
            throw new NullPointerException();
        }

        //是否已有老状态机
        AbstractStateMachine oldStateMachine = STATE_MACHINE_MAP.get(stateMachineKey);
        if (oldStateMachine != null){
            System.out.println("覆盖"+stateMachineKey+"的状态机");
        }

        STATE_MACHINE_MAP.put(stateMachineKey, stateMachine);
    }

    /**
     * 根据stateMachineKey获取状态机
     * @param stateMachineKey
     * @return
     */
    public AbstractStateMachine getStateMachine(String stateMachineKey){
        if (StringUtils.isBlank(stateMachineKey)){
            return null;
        }
        return STATE_MACHINE_MAP.get(stateMachineKey);
    }

}
