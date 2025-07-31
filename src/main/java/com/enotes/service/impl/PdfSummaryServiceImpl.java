package com.enotes.service.impl;

import com.enotes.ai.PdfSummaryAgent;
import com.enotes.entity.Notes;
import com.enotes.repository.NoteRepository;
import com.enotes.service.PdfSummaryService;
import net.sourceforge.tess4j.Tesseract;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;

@Service
public class PdfSummaryServiceImpl implements PdfSummaryService {

    private final PdfSummaryAgent agent;
    private final NoteRepository noteRepository;

    public PdfSummaryServiceImpl(PdfSummaryAgent agent, NoteRepository noteRepository) {
        this.agent = agent;
        this.noteRepository = noteRepository;
    }

    @Override
    public String summarizePdfByNoteId(Integer noteId) {
        Notes note = noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found"));
        String fileUrl = note.getFileDetails().getUploadFileName(); // Cloudinary PDF URL

        try (InputStream inputStream = new URL(fileUrl).openStream()) {
            PDDocument document = PDDocument.load(inputStream);
            PDFTextStripper stripper = new PDFTextStripper();
            String extractedText = stripper.getText(document);
            document.close();

            // Fallback to OCR if PDFBox text is empty
            if (extractedText == null || extractedText.trim().isEmpty()) {
                System.out.println("Fallback to OCR...");
                extractedText = performOcrFromPdf(fileUrl);
            }

            String cleanedText = cleanText(extractedText);

            if (cleanedText.length() > 4000) {
                cleanedText = cleanedText.substring(0, 4000);
            }

            String summary = agent.summarize(cleanedText);
            return formatSummaryAsHtml(summary);

        } catch (Exception e) {
            throw new RuntimeException("Error summarizing PDF", e);
        }
    }

    private String performOcrFromPdf(String fileUrl) throws Exception {
        try (InputStream is = new URL(fileUrl).openStream(); PDDocument document = PDDocument.load(is)) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            Tesseract tesseract = new Tesseract();

            // ✅ Correct path: point to parent of tessdata
            tesseract.setDatapath("C:/tesseract/tessdata/");
            tesseract.setLanguage("eng");
            tesseract.setOcrEngineMode(1); // LSTM OCR
            tesseract.setPageSegMode(11);   // Automatic layout

            StringBuilder ocrResult = new StringBuilder();
            int totalPages = document.getNumberOfPages();
            int maxPages = Math.min(3, totalPages); // Optional: limit for performance

            for (int i = 0; i < maxPages; i++) {
                BufferedImage image = pdfRenderer.renderImageWithDPI(i, 600);
                String pageText = tesseract.doOCR(image);
                ocrResult.append("---- Page ").append(i + 1).append(" ----\n").append(pageText).append("\n");
            }

            return ocrResult.toString().trim();

        } catch (Exception e) {
            throw new RuntimeException("OCR failed", e);
        }
    }

    private String cleanText(String raw) {
        return raw.replaceAll("\\s+", " ")
                .replaceAll("[^\\x20-\\x7E]", "")
                .trim();
    }

    private String formatSummaryAsHtml(String summaryText) {
        return summaryText
                .replaceAll("(?m)^###\\s*(.+)$", "<h2>$1</h2>")
                .replaceAll("(?m)^-\\s\\*\\*(.+?)\\*\\*:\\s*", "<h3>$1</h3><p>")
                .replaceAll("(?m)^-\\s", "<li>")
                .replaceAll("(?m)^```python\\s*", "<pre><code class=\"language-python\">")
                .replaceAll("(?m)^```\\s*", "</code></pre>")
                .replaceAll("\n{2,}", "</p><p>")
                .replaceAll("(?<!</p>)\\n", "<br/>")
                .replaceAll("(?m)(<li>.*?</li>)", "<ul>$1</ul>");
    }
}
