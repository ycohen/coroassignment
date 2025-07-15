package org.example.coroassignment;

import java.time.Instant;
import java.util.Collection;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MessageProcessorService {

  private DbLayer dbLayer;
  private KafkaTemplate<Object, MessageDTO> kafkaTemplate;

  public void processMessage(MessageDTO message) {
    log.info("Processing message: {}", message);
    kafkaTemplate.send("detections", message);
  }

  public Collection<DetectionInstance> getDetections(long timeFrom, long timeTo) {
    return dbLayer.getDetections(Instant.ofEpochMilli(timeFrom),
        Instant.ofEpochMilli(timeTo));
  }
}
