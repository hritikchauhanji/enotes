package com.enotes.ai;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

public class PdfSummaryAgentFactory {

    public static PdfSummaryAgent create(String apiKey) {
        return AiServices.create(
                PdfSummaryAgent.class,
                OpenAiChatModel.builder()
                        .apiKey(apiKey)
                        .modelName("gpt-4o-mini")
                        .baseUrl("https://api.openai.com/v1")
                        .build()
        );
    }
}
