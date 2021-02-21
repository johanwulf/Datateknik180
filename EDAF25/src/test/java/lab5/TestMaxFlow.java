package lab5;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class TestMaxFlow {
  @Test public void test1() {
    FlowGraph g = new FlowGraph(2, new FlowEdge(0, 1, 10));
    assertThat(Lab5.maxFlow(g, 0, 1)).isEqualTo(10);
  }

  @Test public void test2() {
    FlowGraph g = new FlowGraph(3,
        new FlowEdge(0, 1, 10),
        new FlowEdge(1, 2, 8)
    );
    assertThat(Lab5.maxFlow(g, 0, 2)).isEqualTo(8);
  }

  @Test public void test3() {
    FlowGraph g = new FlowGraph(5,
        new FlowEdge(0, 1, 1),
        new FlowEdge(0, 2, 2),
        new FlowEdge(0, 3, 4),
        new FlowEdge(1, 4, 8),
        new FlowEdge(2, 4, 8),
        new FlowEdge(3, 4, 8)
    );
    assertThat(Lab5.maxFlow(g, 0, 4)).isEqualTo(7);
  }

  @Test public void test4Infinite() {
    FlowGraph g = new FlowGraph(2,
        new FlowEdge(0, 1, Integer.MAX_VALUE)
    );
    assertThat(Lab5.maxFlow(g, 0, 1)).isEqualTo(Integer.MAX_VALUE);
  }

  @Test public void test5Infinite() {
    FlowGraph g = new FlowGraph(3,
        new FlowEdge(0, 1, Integer.MAX_VALUE),
        new FlowEdge(1, 2, 13)
    );
    assertThat(Lab5.maxFlow(g, 0, 2)).isEqualTo(13);
  }
}
