package com.op.samples.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 订单消息发送类
 *
 * @author cdrcool
 */
@Slf4j
@Service
public class OrderMessageSender {
    private final RabbitTemplate rabbitTemplate;

    public OrderMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 发送订单延迟消息（基于过期时间TTL与死信队列）
     * 由于队列的先进先出特性，死信队列不适合为不同消息设置不同的过期时间时
     */
    public void sendTtlMessage() {
        String messageId = UUID.randomUUID().toString().replace("-", "");
        Message message = MessageBuilder
                .withBody("123456".getBytes(StandardCharsets.UTF_8))
                // 设置消息过期时间（10s）
                .andProperties(MessagePropertiesBuilder.newInstance().setExpiration("10000").build())
                .build();
        rabbitTemplate.convertAndSend(
                "order_create_ttl_exchange",
                "order_create_ttl",
                message,
                new CorrelationData(messageId));
        log.info("发送订单ttl消息，消息id：{}，消息内容：{}", messageId, message);
    }
}
