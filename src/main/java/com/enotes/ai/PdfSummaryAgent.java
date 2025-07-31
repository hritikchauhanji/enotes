package com.enotes.ai;

import dev.langchain4j.service.SystemMessage;

@SystemMessage("""
 You are an assistant that summarizes handwritten educational notes into clean, readable HTML with headings, subheadings, and Python code blocks where needed.
""")
public interface PdfSummaryAgent {
    String summarize(String input);
}
