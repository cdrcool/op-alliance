package com.op.samples.patterns.command;

/**
 * 命令调用者：持有一个命令对象，并在某个时间点调用命令对象的 execute() 方法，将请求付诸实行
 *
 * @author cdrcool
 */
public class Invoker {
    private Command command;

    /**
     * 将命令对象存储在调用者中
     *
     * @param command 命令对象
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    public void invoke() {
        command.execute();
    }
}
