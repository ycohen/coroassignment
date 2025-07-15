package org.example.coroassignment;

import java.time.Instant;

import org.springframework.data.annotation.Id;

/**
 * This stores information about the credit card detection.
 * We can store anything you'd like from the message,
 * but these seem to be the relevant details
 */
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
