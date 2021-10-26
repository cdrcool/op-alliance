package com.op.samples.patterns.command;

/**
 * 命令接收者：知道如何进行必要的工作，实现命令接口的请求。任何类都可以当接收者。
 *
 * @author cdrcool
 */
public class Receiver {

    public void action1() {
        System.out.println("do action1");
    }

    public void action2() {
        System.out.println("do action2");
    }
}
