package ru.levchenko.rabbitMQ.app;

import com.rabbitmq.client.ConnectionFactory;
import ru.levchenko.rabbitMQ.rabbitmqJavaClient.Consumer;
import ru.levchenko.rabbitMQ.rabbitmqJavaClient.Producer;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        String queueName = "queue1";
        Producer producer = new Producer(queueName, connectionFactory);
        producer.publishMessage("Hello world");

        Thread thread = new Thread(){
            @Override
            public void run(){
                try {
                    Consumer consumer = new Consumer(connectionFactory,queueName);
                    consumer.subscribeOnMessages();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

        producer.publishMessage("Hello world 1");
        producer.publishMessage("Hello world 2");
        producer.publishMessage("Hello world 3");


    }
}
