package org.rag4j;

import org.rag4j.domain.RetrievalOutput;
import org.rag4j.indexing.Embedder;
import org.rag4j.indexing.IndexingService;
import org.rag4j.indexing.OpenNLPSentenceSplitter;
import org.rag4j.retrieval.TopNRetrievalStrategy;
import org.rag4j.retrieval.WindowRetrievalStrategy;
import org.rag4j.store.InternalContentStore;
import org.rag4j.workshop.AlphabetEmbedder;
import org.rag4j.workshop.DocumentRetrievalStrategy;

/**
 * The goal for this step is to experiment with a retrieval strategy to obtain a context to be used by an LLM to
 * generate an answer to a question. It must help you understand the difference between a chunk used for similarity
 * matching and the context used by the LLM to answer a question.
 */
public class AppStep5RetrievalStrategy {
    public static void main(String[] args) {
        String question = "Since when was the Vasa available for the public to visit?";

        // TODO: Initialize a retriever using the internal content store and the alphabet embedder.
        //  Use the IndexingService to index the documents in the file vasa-timeline.jsonl.
        // begin solution
        Embedder embedder = new AlphabetEmbedder();
        InternalContentStore internalContentStore = new InternalContentStore(embedder);
        IndexingService indexingService = new IndexingService(internalContentStore);
        indexingService.indexDocuments(new VasaContentReader(), new OpenNLPSentenceSplitter());
        // end solution


        // TODO: Use the TopN retrieval strategy to obtain the context for a question.
        // begin solution
        TopNRetrievalStrategy topNRetrievalStrategy = new TopNRetrievalStrategy(internalContentStore);
        RetrievalOutput retrieved = topNRetrievalStrategy.retrieve(question, 1);
        System.out.println(retrieved.constructContext());
        // end solution

        // TODO: Migrate to the Window Retrieval Strategy to obtain the context for a question.
        // begin solution
        WindowRetrievalStrategy windowRetrievalStrategy = new WindowRetrievalStrategy(internalContentStore, 1);
        retrieved = windowRetrievalStrategy.retrieve(question, 1);
        System.out.println(retrieved.constructContext());
        //end solution


        // TODO extra: Can you implement a new retrieval strategy? Think about a Document Retrieval Strategy!
        // begin solution
        DocumentRetrievalStrategy documentRetrievalStrategy = new DocumentRetrievalStrategy(internalContentStore);
        retrieved = documentRetrievalStrategy.retrieve(question, 1);
        System.out.println(retrieved.constructContext());
        // end solution
    }
}
