package lab4;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/** Helper class for the priority queue in Dijkstras algorithm. */
class PQElement {
  int node;
  int distance;

  public PQElement(int node, int dist) {
    this.node = node;
    this.distance = dist;
  }
}

public class Lab4 {
  /**
   * Computes the shortest distance between start and end in the graph g using
   * Dijkstra's
   * algorithm.
   * This version handles only graphs with integer edge distances.
   * 
   * @param g     a graph with distance information attached to the edges
   * @param start start vertex
   * @param end   end vertex
   * @return shortest distance between start and end
   */
  public static int distance(DistanceGraph g, int start, int end) {
    Comparator<PQElement> cmp = Comparator.comparingInt(e -> e.distance);
    PriorityQueue<PQElement> queue = new PriorityQueue<>(cmp);
    queue.add(new PQElement(start, 0));

    Map<Integer, Integer> mappedValues = new HashMap<>();
    Set<Integer> visitedVertexes = new HashSet<>();

    mappedValues.put(start, 0);
    visitedVertexes.add(start);

    while (!queue.isEmpty()) {
      PQElement temp = queue.poll();
      if (temp.node == end) {
        return temp.distance;
      } else {
        for (Edge e : g.edges(temp.node)) {
          int w = e.destination;
          int newDistance = e.distance + temp.distance;

          int wDist = mappedValues.getOrDefault(w, Integer.MAX_VALUE);

          if (!visitedVertexes.contains(w) || newDistance < wDist) {
            queue.add(new PQElement(w, newDistance));
            visitedVertexes.add(w);
          }
        }
      }
    }

    return -1;
  }

  /**
   * Finds a shortest path between start and end in a graph g Dijkstra's
   * algorithm.
   * The graph can have any distance unit.
   * 
   * @param g     a graph with distance information attached to the edges
   * @param start start vertex
   * @param end   end vertex
   * @return a list containing the nodes on the shortest path from start to end
   */
  public static List<Integer> shortestPath(DistanceGraph g, int start, int end) {
    Comparator<PQElement> cmp = Comparator.comparingInt(e -> e.distance);
    PriorityQueue<PQElement> queue = new PriorityQueue<>(cmp);
    queue.add(new PQElement(start, 0));

    LinkedList<Integer> answer = new LinkedList<>();
    HashMap<Integer, Integer> shortestPath = new HashMap<>();
    HashMap<Integer, Integer> previousPath = new HashMap<>();

    // Add all vertexes with weighted value
    for (int i : g.vertexSet()) {
      if (i == start) {
        shortestPath.put(start, 0);
      } else {
        shortestPath.put(i, Integer.MAX_VALUE);
      }
    }

    // Gå igenom hela queuen
    while (!queue.isEmpty()) {
      PQElement current = queue.poll();

      if (current.node == end) {
        int current_node = end;
        while (current_node != start) {
          answer.addFirst(current_node); // add first to list
          current_node = previousPath.get(current_node);
        }
        answer.addFirst(current_node); // add first to list

        return answer;
      }

      for (Edge e : g.edges(current.node)) {
        int w = e.destination;
        int newDist = current.distance + e.distance;
        int wDist = shortestPath.get(w);
        if (newDist < wDist) {
          shortestPath.put(w, newDist);
          queue.add(new PQElement(w, newDist));
          previousPath.put(w, current.node);
        }
      }
    }
    return answer;
  }
}