package com.utils.stateMachine;

import com.utils.stateMachine.enums.StateEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象状态机
 */
public abstract class AbstractStateMachine {

    private Map<StateEnum,List<StateEnum>> stateMachine;

    public void register(){
        stateMachine = new HashMap<>();
        buildStateMachine();

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
     * 交给子类实现
     */
    public abstract void buildStateMachine();


}
