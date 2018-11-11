package com.utils.stateMachine;

import com.utils.stateMachine.enums.StateEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 状态机工具
 */
@Component
public class StateMachineUtils {

    @Autowired
    private StateMachineContainer stateMachineContainer;

    /**
     * 判断当前的状态是否能扭转到传入的下一个状态
     * @param stateMachineKey
     * @param currentState
     * @param nextState
     * @return
     */
    public boolean canOperate(String stateMachineKey, StateEnum currentState, StateEnum nextState){
        if (StringUtils.isBlank(stateMachineKey) || currentState == null || nextState == null){
            throw new NullPointerException();
        }

        AbstractStateMachine stateMachine = stateMachineContainer.getStateMachine(stateMachineKey);
        if (stateMachine == null){
            throw new IllegalArgumentException("找不到对应的状态机,stateMachineKey:"+stateMachineKey);
        }

        return stateMachine.canOperate(currentState, nextState);
    }
}
