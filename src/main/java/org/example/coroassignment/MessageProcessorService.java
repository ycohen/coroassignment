package org.example.coroassignment;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MessageProcessorService {
  public static final Pattern creditCardPattern = Pattern.compile("\\d{4}-\\d{4}-\\d{4}-\\d{4}");

  private DbLayer dbLayer;

  public void processMessage(MessageDTO message) {
    log.info("Processing message: {}", message);
    int instances = countCreditCards(message.body());
    instances += countCreditCards(message.subject());

    if (instances > 0) {
      log.info("Detected {} credit card(s) in message", instances);

      dbLayer.saveDetection(
          new DetectionInstance("1", message.sender(), Instant.ofEpochMilli(message.sentTime()),
              instances));
    } else {
      log.info("No credit cards detected in message");
    }
  }

  private int countCreditCards(String string) {
    int instances = 0;
    Matcher matcher = creditCardPattern.matcher(string);

    while (matcher.find()) {
      instances++;
    }

    return instances;
  }
}
