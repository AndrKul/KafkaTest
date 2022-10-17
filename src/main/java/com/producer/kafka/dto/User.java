package com.producer.kafka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class
User implements Serializable {

    public User() {

    }
    public User (String userid,String transactionID) {
        this.transactionID=transactionID;
        this.userid=userid;
    }
  @JsonProperty("userid")
  private String userid;

  @JsonProperty("transactionID")
  private String transactionID;
}
