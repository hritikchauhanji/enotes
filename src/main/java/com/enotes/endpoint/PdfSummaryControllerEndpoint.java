package com.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/summary")
public interface PdfSummaryControllerEndpoint {

    @GetMapping("/{noteId}")
    ResponseEntity<String> summarizeNote(@PathVariable("noteId") Integer noteId);
}
