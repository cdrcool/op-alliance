package com.op.samples.patterns.command;

/**
 * 命令接口：调用命令对象的 execute 方法，就可以让接收者进行相关的动作。命令接口也可以具备一个 undo() 方法
 *
 * @author cdrcool
 */
public interface Command {

    /**
     * 执行命令
     */
    void execute();

    /**
     * 撤销命令
     */
    void undo();
}
