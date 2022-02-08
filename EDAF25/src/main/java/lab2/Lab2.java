package lab2;

import graph.Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayDeque;

public class Lab2 {

  /**
   * Generic depth first search in a graph, starting from the vertex u.
   *
   * @param g       the graph to search in
   * @param u       the start vertex
   * @param visited set of visited vertices (should be empty for a full search)
   * @param <T>     the vertex type
   */
  private static <T> void dfs(Graph<T> g, T u, Set<T> visited) {
    visited.add(u);
    for (T v : g.neighbours(u)) {
      if (!visited.contains(v)) {
        dfs(g, v, visited);
      }
    }
  }

  public static boolean isConnected(Graph<Integer> g) {
    Set<Integer> set = new HashSet();
    dfs(g, 0, set);

    return set.size() == g.vertexCount();
  }

  public static int nbrOfComponents(Graph<Integer> g) {
    int noVertex = g.vertexCount();
    Set<Integer> visited = new HashSet();

    int comp = 0;
    for (int i = 0; i < noVertex; i++) {
      if (!visited.contains(i)) {
        dfs(g, i, visited);
        comp++;
      }
    }
    return comp;
  }

  public static boolean pathExists(Graph<Integer> g, int u, int v) {
    if (u == v) {
      return true;
    }

    Queue<Integer> queue = new ArrayDeque<>();
    Set<Integer> visited = new HashSet<>();

    queue.add(u);

    while (!queue.isEmpty()) {
      Integer current = queue.remove();
      visited.add(current);

      Collection<Integer> neighbours = g.neighbours(current);
      if (neighbours.contains(v)) {
        return true;
      }

      for (Integer i : neighbours) {
        if (!visited.contains(i)) {
          queue.add(i);
        }
      }
    }

    return false;
  }

  public static List<Integer> findPath(Graph<Integer> g, int u, int v) {
    // TODO(D5): implement this!
    return Collections.emptyList();
  }
}
