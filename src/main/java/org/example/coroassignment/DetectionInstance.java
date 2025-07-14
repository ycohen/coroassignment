package org.example.coroassignment;

import java.time.Instant;

import org.springframework.data.annotation.Id;

public class DetectionInstance {

  @Id
  private String id;

  public String sender;
  public Instant time;
  public int detections;

  public DetectionInstance(String sender, Instant time, int detections) {
    this.sender = sender;
    this.time = time;
    this.detections = detections;
  }
}
