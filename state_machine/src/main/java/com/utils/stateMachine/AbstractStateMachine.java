package com.utils.stateMachine;

import com.utils.stateMachine.enums.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象状态机
 */
public abstract class AbstractStateMachine {

    private Map<StateEnum,List<StateEnum>> stateMachine;

    @Autowired
    private StateMachineContainer stateMachineContainer;

    @PostConstruct
    public void register(){
        stateMachine = new HashMap<>();
        buildStateMachine();
        registerStatusMachine();

    }

    /**
     * 添加状态
     * @param currentState
     * @param nextState
     */
    protected void addState(StateEnum currentState, StateEnum nextState){
        if (currentState == null || nextState == null){
            throw new NullPointerException();
        }

        if (stateMachine.get(currentState) == null){
            stateMachine.put(currentState, new ArrayList<>());
        }
        stateMachine.get(currentState).add(nextState);
    }

    /**
     * 向状态机容器中注册该状态机
     */
    private void registerStatusMachine() {
        String stateMachineKey = getStateMachineKey();
        stateMachineContainer.registerStateMachine(stateMachineKey, this);
    }


    /**
     * 组装状态机
     */
    public abstract void buildStateMachine();

    /**
     * 获取状态机key
     */
    public abstract String getStateMachineKey();


}
