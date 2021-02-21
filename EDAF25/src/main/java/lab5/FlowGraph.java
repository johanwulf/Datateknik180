package lab5;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A flow graph with integer edge capacities.
 */
public class FlowGraph {
  private final int vertexCount;
  private final int[][] capacity;

  /**
   * Creates a flow graph with the edges listed in the second parameter.
   *
   * @param vertexCount number of vertices in the graph
   * @param edges list of directed edges
   */
  public FlowGraph(int vertexCount, FlowEdge... edges) {
    Preconditions.checkArgument(vertexCount >= 2,
        "vertex count must be at least 2 (source and sink must always exist)");
    this.capacity = new int[vertexCount][vertexCount];
    this.vertexCount = vertexCount;
    Map<Integer, Set<Integer>> adjacent = new HashMap<>(vertexCount);
    for (FlowEdge edge : edges) {
      int u = edge.source;
      int v = edge.destination;
      Preconditions.checkArgument(u != v, "cannot create loop at vertex %s", u);
      Preconditions.checkArgument(u >= 0 && u < vertexCount && v >= 0 && v < vertexCount,
          "cannot create edge from vertex %s to %s in a graph with %s vertices", u, v, vertexCount);
      Set<Integer> adj = adjacent.get(u);
      if (adj == null) {
        adj = new HashSet<>();
        adjacent.put(u, adj);
      }
      Preconditions.checkArgument(!adj.contains(v), "cannot create duplicate edge %s -> %s", u, v);
      adj.add(v);
      capacity[u][v] = edge.maxCapacity;
    }
  }

  public int vertexCount() {
    return vertexCount;
  }

  /**
   * Returns the capacity on the edge from node u to v. If there is no
   * such edge, 0 is returned.
   */
  public int getCapacity(int u, int v) {
    Preconditions.checkArgument(u >= 0 && u < vertexCount, "argument u is out of bounds");
    Preconditions.checkArgument(v >= 0 && v < vertexCount, "argument v is out of bounds");
    return capacity[u][v];
  }
}
