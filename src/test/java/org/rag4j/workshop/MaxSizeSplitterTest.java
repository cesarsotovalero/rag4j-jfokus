package org.rag4j.workshop;

import org.junit.jupiter.api.Test;
import org.rag4j.domain.Chunk;
import org.rag4j.domain.InputDocument;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxSizeSplitterTest {

    @Test
    public void splitShouldReturnChunksOfMaxSize() {
        MaxSizeSplitter splitter = new MaxSizeSplitter(5);
        InputDocument inputDocument = InputDocument.builder()
                .documentId("doc1")
                .text("This is a test document")
                .build();
        List<Chunk> chunks = splitter.split(inputDocument);
        assertEquals(3, chunks.size());
        assertEquals("This", chunks.get(0).getText());
        assertEquals("is a", chunks.get(1).getText());
        assertEquals("test", chunks.get(2).getText());
    }

    @Test
    public void splitShouldReturnEmptyListForEmptyDocument() {
        MaxSizeSplitter splitter = new MaxSizeSplitter(5);
        InputDocument inputDocument = InputDocument.builder()
                .documentId("doc1")
                .text("")
                .build();
        List<Chunk> chunks = splitter.split(inputDocument);
        assertEquals(0, chunks.size());
    }

    @Test
    public void splitShouldReturnSingleChunkForDocumentSmallerThanMaxSize() {
        MaxSizeSplitter splitter = new MaxSizeSplitter(50);
        InputDocument inputDocument = InputDocument.builder()
                .documentId("doc1")
                .text("This is a test document")
                .build();
        List<Chunk> chunks = splitter.split(inputDocument);
        assertEquals(1, chunks.size());
        assertEquals("This is a test document", chunks.get(0).getText());
    }
}