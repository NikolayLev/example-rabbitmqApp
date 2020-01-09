package ru.levchenko.rabbitMQ.rabbitmqJavaClient;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    private Connection connection;
    private Channel channel;
    private String queueName;

    public Consumer(ConnectionFactory connectionFactory, String queueName) {
        try {
            connection=connectionFactory.newConnection();
            channel = connection.createChannel();
            this.queueName = queueName;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

    private DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), "UTF-8");
        System.out.println("Consumer ПОЛУЧИЛ СООБЩЕНИЕ : " + message);
    };

    public void subscribeOnMessages() throws IOException {
        channel.basicConsume(queueName,true,deliverCallback,consumerTag->{});
    }
}
