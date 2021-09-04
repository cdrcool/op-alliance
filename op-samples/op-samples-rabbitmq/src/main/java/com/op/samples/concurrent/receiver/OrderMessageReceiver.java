package com.op.samples.concurrent.receiver;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 订单消息接收类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class OrderMessageReceiver {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = "order_create_dl_queue"),
            exchange = @Exchange(
                    value = "order_create_dl_exchange",
                    type = "direct"
            ),
            key = "order_create_dl"
    ))
    public void onReceiveDeadLetterMessage(Message message, Channel channel, @Header("spring_returned_message_correlation") String correlationId,
                                           @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        log.info("接收订单ttl消息，消息id：{}，消息内容：{}，消息属性：{}", correlationId, new String(message.getBody()), message.getMessageProperties());

        channel.basicAck(deliveryTag, false);
    }

    @RabbitListener(queues = "order_create_delay_queue")
    public void onReceiveDelayMessage(Message message, Channel channel, @Header("spring_returned_message_correlation") String correlationId,
                                      @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        log.info("接收订单delay消息，消息id：{}，消息内容：{}，消息属性：{}", correlationId, new String(message.getBody()), message.getMessageProperties());

        channel.basicAck(deliveryTag, false);
    }
}
