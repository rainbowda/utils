package com.utils.stateMachine.enums;

/**
 * 状态枚举
 */
public enum StateEnum {

    THREAD_NEW(1, "线程新建"),
    THREAD_READY(2, "线程就绪"),
    THREAD_RUNNABLE(3, "线程运行中"),
    THREAD_TIMED_WAITING(4, "线程超时等待"),
    THREAD_WAITING(5, "线程等待"),
    THREAD_BLOCKED(6, "线程阻塞"),
    THREAD_TERMINATED(7, "线程终止");


    private int code;
    private String desc;

    StateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
