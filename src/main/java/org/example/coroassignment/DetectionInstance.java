package org.example.coroassignment;

import java.time.Instant;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DetectionInstance {
  @Id
  private String id;

  public String sender;
  public Instant time;
  public int detections;
}
