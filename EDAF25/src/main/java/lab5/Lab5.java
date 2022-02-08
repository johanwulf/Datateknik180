package lab5;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Lab5 {
  /**
   * Computes the maximum flow for a flow network.
   * 
   * @param g a graph with edge capacities and a source and sink
   * @return shortest distance between start and end
   */
  public static int maxFlow(FlowGraph g, int source, int sink) {
    int noVertices = g.vertexCount(); // Number of Vertices in the FlowGraph g
    int residualGraph[][] = new int[noVertices][noVertices]; // Create a new matrix with the same dimensions as the
                                                             // FlowGraph g
    int pred[] = new int[noVertices];

    // Copy all values from g to residualGraph
    for (int i = 0; i < noVertices; i++) {
      for (int j = 0; j < noVertices; j++) {
        residualGraph[i][j] = g.getCapacity(i, j);
      }
    }

    // Initial flow is 0
    int flow = 0;

    // Continue until no more paths are found
    while (true) {
      if (!bfs(source, sink, noVertices, residualGraph, pred))
        break; // Break if no path from source to sink was found

      int bottleneck = residualGraph[pred[sink]][sink]; // If there was a path, find the bottleneck

      for (int i = sink; i != source; i = pred[i]) { // Go over path
        bottleneck = Math.min(bottleneck, residualGraph[pred[i]][i]); // Set bottleneck to smallest capacity
      }

      for (int i = sink; i != source; i = pred[i]) {
        residualGraph[i][pred[i]] += bottleneck; // Raise the flow by the bottleneck in the direction to sink
        residualGraph[pred[i]][i] -= bottleneck; // Lower the flow by the bottleneck in the direction to source
      }

      flow += bottleneck; // Add bottleneck to flow
    }

    return flow;
  }

  private static boolean bfs(int source, int sink, int noVertices, int[][] residual, int[] pred) {
    boolean visited[] = new boolean[noVertices]; // Mark all vertices as not visited
    Queue<Integer> queue = new PriorityQueue<Integer>(); // Create queue for BFS
    visited[source] = true; // Set source as visited
    queue.add(source); // Add our source node to the queue

    while (!queue.isEmpty()) {
      int current = queue.peek(); // Current node

      if (current == sink) {
        return true; // Arrived at sink
      } else {
        current = queue.poll(); // Retrieve next node from queue

        for (int i = 0; i < noVertices; i++) {
          if (!visited[i] && residual[current][i] > 0) { // If node wasn't visited and has capacity > 0
            visited[i] = true; // Add to visited nodes
            queue.add(i); // Add node to queue
            pred[i] = current; // ???
          }
        }
      }
    }

    return false;
  }

  /**
   * Read a flowgraph from a file.
   */
  public static FlowGraph loadFlowGraph(Path path) throws IOException {
    Scanner scan = new Scanner(path);
    int nodeCount = Integer.parseInt(scan.nextLine());
    int edgeCount = Integer.parseInt(scan.nextLine());
    FlowEdge[] edges = new FlowEdge[edgeCount];

    for (int i = 0; i < edgeCount; i++) {
      String edge = scan.nextLine();
      String s[] = edge.split(" ");
      int out[] = new int[s.length];

      for (int j = 0; j < s.length; j++) {
        out[j] = Integer.parseInt(s[j]);
      }

      if (out[2] < 0) {
        out[2] = Integer.MAX_VALUE;
      }

      edges[i] = new FlowEdge(out[0], out[1], out[2]);
    }

    return new FlowGraph(nodeCount, edges);
  }
}
