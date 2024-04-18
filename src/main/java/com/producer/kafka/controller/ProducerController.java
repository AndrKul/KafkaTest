package com.producer.kafka.controller;

import com.producer.kafka.dto.User;
import com.producer.kafka.service.TopicProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.StreamSupport;

@RestController
@Slf4j
public class ProducerController {

    @Autowired
    TopicProducer producer;

    @PostMapping(path = "producer/{id}",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendOneMessage(@PathVariable String id) {
             producer.sendMessage(id);
             return ResponseEntity.status(HttpStatus.OK)
                     .body("Producer ---> sent message  ") ;
    }


    @KafkaListener(topics = "testTopic", groupId = "group_id",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenAsObject(ConsumerRecord<String, User> cr,
                               @Payload User payload) {
        log.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), payload, cr.toString());

    }

    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }

}

