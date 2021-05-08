package com.op.samples.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 配置 {@link RabbitTemplate}，以确认消息是否发送到 exchange 及 exchange 对应的 queue 中
 * 需要配合设置属性 publisher-confirm-type(true) 和 publisher-returns(ture)/mandatory(true)
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class RabbitTemplateConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {
    private final RabbitTemplate rabbitTemplate;

    public RabbitTemplateConfig(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    /**
     * 消息到达 exchange 后，触发该方法执行
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("消息到达了exchange，消息id：{}, ack: {}, cause: {}", correlationData, ack, cause);
    }

    /**
     * 消息没有被路由到指定的 queue 时，触发该方法执行
     */
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.info("消息未能路由到队列：{}", returned);
    }
}
