package lab2;

import graph.Graph;
import graph.SimpleGraph;

public class GraphFactory {
  /** Returns a connected graph of at least 5 vertices. */
  public static Graph<Integer> buildConnected() {
    return new SimpleGraph(5, new int[][] {
      {0, 1},
      {1, 2},
      {2, 3},
      {3, 4},
      {4, 0}
    });
  }

  /** Returns a disconnected graph of at least 5 vertices. */
  public static Graph<Integer> buildDisconnected() {
    return new SimpleGraph(5, new int[][] {
      {0, 1},
      {1, 2},
      {2, 3},
      {3, 0}
    });
  }
}
