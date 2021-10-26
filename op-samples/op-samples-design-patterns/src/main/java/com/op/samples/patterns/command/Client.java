package com.op.samples.patterns.command;

/**
 * 客户端：负责创建一个命令对象，并设置其接收者
 *
 * @author cdrcool
 */
public class Client {

    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        ConcreteCommandA command = new ConcreteCommandA(receiver);

        Invoker invoker = new Invoker();
        invoker.setCommand(command);

        invoker.invoke();
    }
}
