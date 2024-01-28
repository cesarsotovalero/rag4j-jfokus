package org.rag4j.workshop;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.rag4j.domain.Chunk;
import org.rag4j.domain.RelevantChunk;
import org.rag4j.domain.RetrievalOutput;
import org.rag4j.retrieval.Retriever;
import org.rag4j.workshop.DocumentRetrievalStrategy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DocumentRetrievalStrategyTest {

    @Test
    public void retrieveShouldReturnCorrectOutputForGivenQuestion() {
        Retriever retriever = Mockito.mock(Retriever.class);
        DocumentRetrievalStrategy strategy = new DocumentRetrievalStrategy(retriever);
        RelevantChunk relevantChunk = new RelevantChunk("doc1", 0, 1, 1.0, "text text", Map.of());
        Chunk chunk = new Chunk("doc1", 0, 1, "text text", Map.of());
        when(retriever.findRelevantChunks("question", 1)).thenReturn(Collections.singletonList(relevantChunk));
        when(retriever.getChunk("doc1", 0)).thenReturn(chunk);
        when(retriever.getChunk("doc1", 1)).thenReturn(chunk);

        RetrievalOutput expected = RetrievalOutput.builder()
                .items(Collections.singletonList(
                        RetrievalOutput.RetrievalOutputItem.builder()
                                .documentId("doc1")
                                .chunkId(0)
                                .text("text text")
                                .build()))
                .build();

        assertEquals(expected, strategy.retrieve("question", 1));
    }

    @Test
    public void retrieveShouldReturnEmptyOutputForNoRelevantChunks() {
        Retriever retriever = Mockito.mock(Retriever.class);
        DocumentRetrievalStrategy strategy = new DocumentRetrievalStrategy(retriever);
        when(retriever.findRelevantChunks("question", 1)).thenReturn(Collections.emptyList());

        RetrievalOutput expected = RetrievalOutput.builder().build();

        assertEquals(expected, strategy.retrieve("question", 1));
    }

    @Test
    public void retrieveShouldReturnCorrectOutputForGivenQuestionAndVector() {
        Retriever retriever = Mockito.mock(Retriever.class);
        DocumentRetrievalStrategy strategy = new DocumentRetrievalStrategy(retriever);
        RelevantChunk relevantChunk = new RelevantChunk("doc1", 0, 1, 1.0, "text text", Map.of());
        Chunk chunk = new Chunk("doc1", 0, 1, "text text", Map.of());
        List<Double> vector = Arrays.asList(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        when(retriever.findRelevantChunks("question", vector, 1)).thenReturn(Collections.singletonList(relevantChunk));
        when(retriever.getChunk("doc1", 0)).thenReturn(chunk);
        when(retriever.getChunk("doc1", 1)).thenReturn(chunk);

        RetrievalOutput expected = RetrievalOutput.builder()
                .items(Collections.singletonList(
                        RetrievalOutput.RetrievalOutputItem.builder()
                                .documentId("doc1")
                                .chunkId(0)
                                .text("text text")
                                .build()))
                .build();

        assertEquals(expected, strategy.retrieve("question", vector, 1));
    }

    @Test
    public void retrieveShouldReturnEmptyOutputForNoRelevantChunksAndGivenVector() {
        Retriever retriever = Mockito.mock(Retriever.class);
        DocumentRetrievalStrategy strategy = new DocumentRetrievalStrategy(retriever);
        List<Double> vector = Arrays.asList(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        when(retriever.findRelevantChunks("question", vector, 1)).thenReturn(Collections.emptyList());

        RetrievalOutput expected = RetrievalOutput.builder().build();

        assertEquals(expected, strategy.retrieve("question", vector, 1));
    }
}