package lab4;

import com.google.common.base.Preconditions;
import graph.Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A directed graph with integer edge distances.
 */
public class DistanceGraph implements Graph<Integer> {
  private final int vertexCount;
  private final Map<Integer, List<Edge>> edges;

  /**
   * Creates a graph with the edges listed in the second parameter.
   *
   * @param vertexCount number of vertices in the graph
   * @param edges list of directed edges
   */
  public DistanceGraph(int vertexCount, Edge... edges) {
    Preconditions.checkArgument(vertexCount >= 0, "vertex count must be non-negative");
    this.vertexCount = vertexCount;
    this.edges = new HashMap<>(vertexCount);
    Map<Integer, Set<Integer>> adjacent = new HashMap<>(vertexCount);
    for (Edge edge : edges) {
      int u = edge.source;
      int v = edge.destination;
      Preconditions.checkArgument(u != v, "cannot create loop at vertex %s", u);
      Preconditions.checkArgument(u >= 0 && u < vertexCount && v >= 0 && v < vertexCount,
          "cannot create edge from vertex %s to %s in a graph with %s vertices", u, v, vertexCount);
      List<Edge> edgeList = this.edges.get(u);
      Set<Integer> adj = adjacent.get(u);
      if (adj == null) {
        edgeList = new ArrayList<>();
        adj = new HashSet<>();
        this.edges.put(u, edgeList);
        adjacent.put(u, adj);
      }
      Preconditions.checkArgument(!adj.contains(v), "cannot create duplicate edge %s -> %s", u, v);
      edgeList.add(edge);
      adj.add(v);
    }
  }

  /** Returns the list of all outgoing edges from vertex v. */
  public List<Edge> edges(Integer v) {
    return Collections.unmodifiableList(edges.getOrDefault(v, Collections.emptyList()));
  }

  @Override public int vertexCount() {
    return vertexCount;
  }

  @Override public Collection<Integer> vertexSet() {
    return IntStream.range(0, vertexCount).boxed().collect(Collectors.toList());
  }

  @Override public List<Integer> neighbours(Integer v) {
    return edges.getOrDefault(v, Collections.emptyList())
        .stream()
        .map(edge -> edge.destination)
        .collect(Collectors.toList());
  }

  /**
   * Returns a String representation of the vertices and edges of the graph.
   *
   * @return A String representation of the graph.
   */
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append("digraph G {\n");
    for (int u = 0; u < vertexCount; ++u) {
      for (int v : neighbours(u)) {
        str.append(String.format("\t%d -> %d\n", u, v));
      }
    }
    str.append("}");
    return str.toString();
  }
}
