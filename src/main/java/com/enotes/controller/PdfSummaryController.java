package com.enotes.controller;

import com.enotes.endpoint.PdfSummaryControllerEndpoint;
import com.enotes.service.PdfSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PdfSummaryController implements PdfSummaryControllerEndpoint {

    private final PdfSummaryService pdfSummaryService;

    @Autowired
    public PdfSummaryController(PdfSummaryService pdfSummaryService) {
        this.pdfSummaryService = pdfSummaryService;
    }

    @Override
    public ResponseEntity<String> summarizeNote(@PathVariable("noteId") Integer noteId) {
        String summary = pdfSummaryService.summarizePdfByNoteId(noteId);
        return ResponseEntity.ok(summary);
    }
}
