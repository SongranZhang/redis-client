package com.linkedkeeper.redis.client.base;

import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @Author: zhangsr
 */
public abstract class BaseTest {

    private static ApplicationContext appContext;

    @BeforeClass
    public static void setUp() throws Exception {
        try {
            long start = System.currentTimeMillis();
            System.out.println("loading config file...");

            appContext = new ClassPathXmlApplicationContext(new String[]{"spring-test.xml"});

            System.out.println("loaded config file,take time : " + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void autoSetBean() throws Exception {
        if (appContext != null)
            appContext.getAutowireCapableBeanFactory().autowireBeanProperties(this, DefaultListableBeanFactory.AUTOWIRE_BY_NAME, false);
    }
}
