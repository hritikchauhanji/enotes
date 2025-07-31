package com.enotes.config;

import com.enotes.ai.PdfSummaryAgent;
import com.enotes.ai.PdfSummaryAgentFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LangchainAgentConfig {

    @Value("${openai.api-key}")
    private String openAiApiKey;

    @Bean
    public PdfSummaryAgent pdfSummaryAgent() {
        return PdfSummaryAgentFactory.create(openAiApiKey);
    }
}
