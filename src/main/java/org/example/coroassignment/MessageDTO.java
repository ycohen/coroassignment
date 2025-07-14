package org.example.coroassignment;


import java.util.List;

public record MessageDTO(String id,
                         String sender, List<String> recipients,
                         String subject, String body,
                         long sentTime
                         ) {
}
