package org.example.coroassignment;

import java.time.Instant;
import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class AppRestController {
  private DbLayer dbLayer;
  private MessageProcessorService messageProcessorService;

  @PostMapping(value = "/message", consumes = "application/json")
  public ResponseEntity<?> message(@RequestBody MessageDTO message) {
    log.info("Received message: {}", message);
    messageProcessorService.processMessage(message);
    return ResponseEntity.ok().build();
  }

  @GetMapping(value = "/detections")
  public ResponseEntity<?> detections(@RequestParam(name = "timeFrom") long timeFrom,
      @RequestParam(name = "timeTo") long timeTo) {
    log.info("Received detections request");

    Collection<DetectionInstance> instances = messageProcessorService.getDetections(timeFrom, timeTo);

    return ResponseEntity.ok(instances);
  }
}
