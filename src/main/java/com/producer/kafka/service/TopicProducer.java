package com.producer.kafka.service;

import com.producer.kafka.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
//@RequiredArgsConstructor
public class TopicProducer {

  @Value("${topic.name.producer}")
  private String topicName;

  @Autowired
  private KafkaTemplate<String, Object> kafkaTemplate;


  public void  sendMessage(String id ) {
    Random random = new Random();
    String transID = String.valueOf(random.nextInt(1000));
    log.debug("transactionID :" + transID);
    var user = new User(id, transID);
    log.debug("  SENDING  ID  :" + id);
    kafkaTemplate.send(topicName,transID,user);
  }

}
