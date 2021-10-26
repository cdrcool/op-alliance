package com.op.samples.patterns.command;

/**
 * 具体命令 A
 *
 * @author cdrcool
 */
public class ConcreteCommandA implements Command {
    /**
     * 命令接收者：不同命令中，可以是相同的接收者执行不同的操作，也可以是不同的接收者执行各自的操作
     */
    private final Receiver receiver;

    public ConcreteCommandA(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.action1();
    }

    @Override
    public void undo() {
        // TODO
    }
}
