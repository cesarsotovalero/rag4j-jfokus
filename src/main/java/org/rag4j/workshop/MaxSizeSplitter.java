package org.rag4j.workshop;

import org.rag4j.domain.Chunk;
import org.rag4j.domain.InputDocument;
import org.rag4j.indexing.Splitter;

import java.util.ArrayList;
import java.util.List;

/**
 * A splitter to transform the InputDocument into chunks of max size. A chunk should never be split in the middle of
 * a word. If adding word is longer than the max size, it should be added to the next chunk. A chunk should end with a
 * space. If a word is longer than the max size, it should be skipped. The chunk id should be incremented for each
 * chunk within the same document. The total chunks should be set to the total amount of chunks for the document.
 * TIP: Use the available unit test to verify your implementation.
 */
public class MaxSizeSplitter implements Splitter {
    private final int maxSize;

    public MaxSizeSplitter(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Splits the input document into chunks of max size.
     *
     * @param inputDocument the input document to split
     * @return the list of chunks
     */
    @Override
    public List<Chunk> split(InputDocument inputDocument) {
        List<Chunk> chunks = new ArrayList<>();
        // TODO: implement this method
        // beginning of solution
        if (inputDocument.getText().isEmpty()) {
            return chunks;
        }

        String[] words = inputDocument.getText().split(" ");
        StringBuilder currentChunk = new StringBuilder();
        int currentSize = 0;
        int chunkId = 0;

        for (String word : words) {
            if (word.length() > maxSize) {
                continue;
            }
            if (currentSize + word.length() > maxSize) {
                chunks.add(Chunk.builder()
                        .documentId(inputDocument.getDocumentId())
                        .text(currentChunk.toString().trim())
                        .chunkId(chunkId++)
                        .properties(inputDocument.getProperties())
                        .build());
                currentChunk = new StringBuilder();
                currentSize = 0;
            }
            currentChunk.append(word).append(" ");
            currentSize += word.length() + 1;
        }

        // If there is content left, add it as the last chunk
        if (!currentChunk.isEmpty()) {
            chunks.add(Chunk.builder()
                    .documentId(inputDocument.getDocumentId())
                    .text(currentChunk.toString().trim())
                    .chunkId(chunkId)
                    .properties(inputDocument.getProperties())
                    .build());
        }
        // end of solution

        return chunks;
    }
}
