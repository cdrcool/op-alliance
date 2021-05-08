package com.op.samples;

import com.op.samples.sender.OrderMessageSender;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用启动类
 *
 * @author cdrcool
 */
@SpringBootApplication
public class RabbitmqSamplesApplication implements CommandLineRunner {
    private final OrderMessageSender orderMessageSender;

    public RabbitmqSamplesApplication(OrderMessageSender orderMessageSender) {
        this.orderMessageSender = orderMessageSender;
    }

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqSamplesApplication.class, args);
    }

    @Override
    public void run(String... args) {
        orderMessageSender.sendTtlMessage();
    }
}
