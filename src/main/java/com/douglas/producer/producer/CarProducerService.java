package com.douglas.producer.producer;

import com.douglas.producer.controller.CarDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class CarProducerService {

    private static final Logger logger = LoggerFactory.getLogger(CarProducerService.class);
    private final String topic;
    private final KafkaTemplate<String, CarDTO> kafkaTemplate;

    public CarProducerService(@Value("${topic.name}") String topic, KafkaTemplate<String, CarDTO> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(CarDTO carDTO) {
        ListenableFuture<SendResult<String, CarDTO>> future = (ListenableFuture<SendResult<String, CarDTO>>) kafkaTemplate.send(topic, carDTO);
        future.addCallback(new ListenableFutureCallback<SendResult<String, CarDTO>>() {
            @Override
            public void onSuccess(SendResult<String, CarDTO> result) {
                logger.info("Message send" + result.getProducerRecord().value());
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.info("Message failure" + ex.getMessage());
            }
        });
    }
}
