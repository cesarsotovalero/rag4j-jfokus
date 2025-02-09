package org.rag4j.workshop;

import org.rag4j.domain.Chunk;
import org.rag4j.domain.RelevantChunk;
import org.rag4j.domain.RetrievalOutput;
import org.rag4j.retrieval.RetrievalStrategy;
import org.rag4j.retrieval.Retriever;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This strategy is used to retrieve a document from the retriever. It does this by combining all the chunks of a
 * document into one text. You can also create an embedding for the complete document, but having smaller chunks
 * for the matching often works better.
 */
public class DocumentRetrievalStrategy implements RetrievalStrategy {
    private final Retriever retriever;

    public DocumentRetrievalStrategy(Retriever retriever) {
        this.retriever = retriever;
    }

    @Override
    public RetrievalOutput retrieve(String question, int maxResults) {
        List<RelevantChunk> relevantItems = retriever.findRelevantChunks(question, maxResults);

        return extractDocumentFromRelevantChunk(relevantItems);
    }

    @Override
    public RetrievalOutput retrieve(String question, List<Double> vector, int maxResults) {
        List<RelevantChunk> relevantItems = retriever.findRelevantChunks(question, vector, maxResults);

        return extractDocumentFromRelevantChunk(relevantItems);
    }

    private RetrievalOutput extractDocumentFromRelevantChunk(List<RelevantChunk> relevantItems) {
        // TODO: Implement this method to extract the document for each relevant chunk.

        // begin solution
        List<RetrievalOutput.RetrievalOutputItem> outputItems = relevantItems
                .stream()
                .map(this::getDocumentChunksByRelevantChunk)
                .toList();
        return outputItems.isEmpty() ?
                RetrievalOutput.builder().build() : RetrievalOutput.builder().items(outputItems).build();
        // end solution
    }

    private RetrievalOutput.RetrievalOutputItem getDocumentChunksByRelevantChunk(RelevantChunk relevantChunk) {
        int[] chunkIds = createChunkArrayForDocument(relevantChunk.getTotalChunks());
        String text = combineChunksTexts(relevantChunk, chunkIds);

        return RetrievalOutput.RetrievalOutputItem.builder()
                .documentId(relevantChunk.getDocumentId())
                .chunkId(relevantChunk.getChunkId())
                .text(text.trim())
                .build();
    }

    int[] createChunkArrayForDocument(int n) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = i;
        }
        return array;
    }

    private String combineChunksTexts(RelevantChunk relevantChunk, int[] chunkIds) {
        return Arrays.stream(chunkIds)
                .mapToObj(chunkId -> retriever.getChunk(relevantChunk.getDocumentId(), chunkId))
                .map(Chunk::getText)
                .collect(Collectors.joining(" "));
    }
}

