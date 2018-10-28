package com.utils.grayControl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GrayControlApplicationTests {

    @Autowired
    private GrayControlUtils grayControlUtils;

    @Test
    public void contextLoads() {
        boolean gray = grayControlUtils.isControl("gray1", 100232L);
        System.out.println("是否拦截:"+gray);


        gray = grayControlUtils.isControl("gray2", 100232L);
        System.out.println("是否拦截:"+gray);
    }

}
