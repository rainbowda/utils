package com.utils.stateMachine;

import com.utils.stateMachine.enums.StateEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StateMachineApplicationTests {

    @Autowired
    private StateMachineUtils stateMachineUtils;

    @Test
    public void contextLoads() {

        boolean flag = stateMachineUtils.canOperate("thread", StateEnum.THREAD_NEW, StateEnum.THREAD_TERMINATED);
        System.out.println(flag);
    }

}
