package org.rag4j.workshop;

import org.junit.jupiter.api.Test;
import org.rag4j.workshop.AlphabetEmbedder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlphabetEmbedderTest {

    @Test
    public void embedShouldReturnCorrectEmbeddingForGivenText() {
        AlphabetEmbedder embedder = new AlphabetEmbedder();
        List<Double> expected = Arrays.asList(1.0, 2.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        assertEquals(expected, embedder.embed("abcdebt"));
    }

    @Test
    public void embedShouldReturnCorrectEmbeddingForGivenTextWithCasing() {
        AlphabetEmbedder embedder = new AlphabetEmbedder();
        List<Double> expected = Arrays.asList(1.0, 2.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        assertEquals(expected, embedder.embed("aBcdEbt"));
    }

    @Test
    public void embedShouldReturnCorrectEmbeddingForTextWithDiacritics() {
        AlphabetEmbedder embedder = new AlphabetEmbedder();
        List<Double> expected = Arrays.asList(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        assertEquals(expected, embedder.embed("à"));
    }

    @Test
    public void embedShouldReturnZeroEmbeddingForEmptyText() {
        AlphabetEmbedder embedder = new AlphabetEmbedder();
        List<Double> expected = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        assertEquals(expected, embedder.embed(""));
    }

    @Test
    public void embedShouldIgnoreNonAlphabeticCharacters() {
        AlphabetEmbedder embedder = new AlphabetEmbedder();
        List<Double> expected = Arrays.asList(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        assertEquals(expected, embedder.embed("a1!"));
    }
}