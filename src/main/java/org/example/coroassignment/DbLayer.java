package org.example.coroassignment;

import java.time.Instant;
import java.util.Collection;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class DbLayer {
  private DetectionRepository detectionRepository;

  public void saveDetection(DetectionInstance detection) {
    log.info("Saving detection: {}", detection);
    detectionRepository.save(detection);
  }

  public Collection<DetectionInstance> getDetections(Instant start, Instant end) {
    log.info("Getting detections between {} and {}", start, end);
    return detectionRepository.findDetectionInstancesByTimeBetween(start, end);
  }
}
