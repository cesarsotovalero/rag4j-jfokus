package org.rag4j;

import org.rag4j.domain.InputDocument;
import org.rag4j.indexing.ContentReader;
import org.rag4j.resources.JsonlReader;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

public class VasaContentReader implements ContentReader {
    private final String filename = "vasa-timeline.jsonl";

    @Override
    public Stream<InputDocument> read() {
        JsonlReader jsonlReader = new JsonlReader(List.of("title", "timerange", "body"), filename);
        return jsonlReader.getLines().stream().map(line -> {
            Map<String, Object> properties = Map.of(
                    "title", line.get("title"),
                    "timerange", line.get("timerange")
            );
            String documentId = line.get("title").toLowerCase(Locale.ROOT).replace(" ", "-");
            return InputDocument.builder()
                    .documentId(documentId)
                    .text(line.get("body"))
                    .properties(properties)
                    .build();
        });
    }
}
