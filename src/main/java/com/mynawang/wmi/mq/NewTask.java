package com.mynawang.wmi.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewTask {
    private static Connection connection = null;
    private static Channel channel = null;
    private static final String WMI_QUEUE_NAME = "wmi_queue1";

    public NewTask() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("123456");
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(WMI_QUEUE_NAME, true, false, false, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

  public static void sendMsg(String message) throws IOException {
        channel.basicPublish("", WMI_QUEUE_NAME,
              MessageProperties.PERSISTENT_TEXT_PLAIN,
              message.getBytes("UTF-8"));
    }


    public static void main(String[] args) {
        NewTask newTask = new NewTask();
        try {
          for (int i = 0; i < 40; i++) {
              newTask.sendMsg("test msg" + i);

              System.out.println(i);
              Thread.sleep(1000);
          }
        } catch (IOException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
    }
}
