package com.library_system.Library_System.Service;

import com.library_system.Library_System.Model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;

import java.util.Map;


@Service
public class AIService {

    private final ChatClient chatClient;

    @Autowired
    public AIService(ChatClient chatClient)
    {
        this.chatClient = chatClient;

    }

    public String generateInsights(Book book)
    {
        String template = """
            Generate a brief, engaging insight or recommendation for the following book:
            Title: {title}
            Author: {author}
            Description: {description}
            
            Focus on what makes this book unique and why someone might want to read it.
            Keep the response concise and engaging, around 2-3 sentences.
            """;

        PromptTemplate promptTemplate = new PromptTemplate(template);
        Map<String,Object> params = Map.of("description",book.getDescription(),"title",book.getTitle(),"author",book.getAuthor());
        Prompt prompt = promptTemplate.create(params);

        try {


            return chatClient.call(prompt).getResult().getOutput().getContent();
        } catch (Exception e ) {
            throw new RuntimeException("Failed to generate insight " + e.getMessage());
        }
    }
}
