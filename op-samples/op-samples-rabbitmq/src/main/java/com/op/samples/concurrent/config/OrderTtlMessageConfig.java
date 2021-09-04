package com.op.samples.concurrent.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单ttl消息配置类（只是建立绑定关系，不会有消费者）
 *
 * @author cdrcool
 */
@Configuration
public class OrderTtlMessageConfig {

    @Bean
    public DirectExchange orderTtlExchange() {
        return new DirectExchange("order_create_ttl_exchange");
    }

    @Bean
    public Queue orderTtlQueue() {
        Map<String, Object> args = new HashMap<>(2);
        args.put("x-dead-letter-exchange", "order_create_dl_exchange");
        args.put("x-dead-letter-routing-key", "order_create_dl");
        return new Queue("order_create_ttl_queue", true, false, false, args);
    }

    @Bean
    public Binding orderTtlBinding() {
        return BindingBuilder.bind(orderTtlQueue()).to(orderTtlExchange()).with("order_create_ttl");
    }
}
