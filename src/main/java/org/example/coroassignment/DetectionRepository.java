package org.example.coroassignment;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface DetectionRepository extends MongoRepository<DetectionInstance, String> {
  List<DetectionInstance> findDetectionInstancesByTimeBetween(Instant start, Instant end);
}
