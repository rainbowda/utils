package com.utils.stateMachine;

import com.utils.stateMachine.enums.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.*;

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
     * 获取当前状态可以扭转的下一个状态
     * @param currentState
     * @return
     */
    private List<StateEnum> getNextStateList(StateEnum currentState){
        if (currentState == null){
            throw  new NullPointerException();
        }

        return stateMachine.get(currentState);
    }

    /**
     * 判断状态是否可以扭转
     * @param currentState
     * @param nextState
     * @return
     */
    public boolean canOperate(StateEnum currentState, StateEnum nextState){
        if (currentState == null || nextState == null){
            throw new NullPointerException();
        }

        List<StateEnum> nextStateList = getNextStateList(currentState);
        if (nextStateList == null || nextStateList.size() == 0){
            return Boolean.FALSE;
        }

        for (StateEnum stateEnum:nextStateList){
            if (stateEnum.equals(nextState)){
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
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
