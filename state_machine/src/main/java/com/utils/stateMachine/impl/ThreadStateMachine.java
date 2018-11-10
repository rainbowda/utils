package com.utils.stateMachine.impl;

import com.utils.stateMachine.AbstractStateMachine;
import com.utils.stateMachine.enums.StateEnum;

/**
 * 线程状态机实现
 */
public class ThreadStateMachine extends AbstractStateMachine {
    @Override
    public void buildStateMachine() {
        //线程创建
        addState(StateEnum.THREAD_NEW, StateEnum.THREAD_READY);
        //就绪
        addState(StateEnum.THREAD_READY, StateEnum.THREAD_RUNNABLE);
        //线程运行中
        addState(StateEnum.THREAD_RUNNABLE, StateEnum.THREAD_WAITING);
        addState(StateEnum.THREAD_RUNNABLE, StateEnum.THREAD_BLOCKED);
        addState(StateEnum.THREAD_RUNNABLE, StateEnum.THREAD_TERMINATED);
        addState(StateEnum.THREAD_RUNNABLE, StateEnum.THREAD_TIMED_WAITING);

        //线程等待
        addState(StateEnum.THREAD_WAITING, StateEnum.THREAD_READY);
        //线程阻塞
        addState(StateEnum.THREAD_BLOCKED, StateEnum.THREAD_READY);
        //线程超时等待
        addState(StateEnum.THREAD_TIMED_WAITING, StateEnum.THREAD_READY);
    }
}
