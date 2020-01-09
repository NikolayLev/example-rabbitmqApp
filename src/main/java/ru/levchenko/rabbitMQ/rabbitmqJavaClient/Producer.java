package ru.levchenko.rabbitMQ.rabbitmqJavaClient;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {


    private ConnectionFactory factory;
    private Connection conn;
    private Channel channel;
    private String queueName;


    public Producer(String queueName, ConnectionFactory connectionFactory) {
        this.factory = connectionFactory;
        this.queueName = queueName;
        this.createExchangeAndQueue(queueName);

    }

    private void createExchangeAndQueue(String queueName) {
        try {
            conn = factory.newConnection();
            channel = conn.createChannel();
            channel.queueDeclare(queueName, false, false, false, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

    public synchronized void publishMessage(String message) {
        byte[] messageBodyBytes = message.getBytes();
        try {
            channel.basicPublish("",queueName,null, messageBodyBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
