package lab3;

import graph.Graph;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class WordGraph implements Graph<String> {
  private final Map<String, Set<String>> graph = new HashMap<>();

  public WordGraph(Path wordfile, WordCriteria criteria) throws IOException {
    try (Reader in = Files.newBufferedReader(wordfile)) {
      Scanner scan = new Scanner(in);
      while (scan.hasNext()) {
        String word = scan.nextLine();
        graph.put(word, new HashSet<String>());
      }
    } catch (Exception e) {
      throw new IOException();
    }

    // TODO(D3): compute word neighbours (according to criteria).
    for (Map.Entry<String, Set<String>> entry1 : graph.entrySet()) {
      for (Map.Entry<String, Set<String>> entry2 : graph.entrySet()) {
        if (criteria.adjacent(entry1.getKey(), entry2.getKey())) {
          if (!entry1.getValue().contains(entry2.getKey())) {
            Set<String> set = entry1.getValue();
            set.add(entry2.getKey());
            graph.put(entry1.getKey(), set);
          }
        }
      }
    }
  }

  @Override
  public int vertexCount() {
    return graph.size();
  }

  @Override
  public Collection<String> vertexSet() {
    return graph.keySet();
  }

  @Override
  public Collection<String> neighbours(String v) {
    return graph.getOrDefault(v, Collections.emptySet());
  }
}
