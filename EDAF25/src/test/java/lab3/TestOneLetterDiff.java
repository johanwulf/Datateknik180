package lab3;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class TestOneLetterDiff {
  private static final OneLetterDiff diff = new OneLetterDiff();

  /** Equal words are not adjacent. */
  @Test public void test0() {
    assertThat(diff.adjacent("hi", "hi")).isFalse();
  }

  @Test public void test1() {
    assertThat(diff.adjacent("foo", "bar")).isFalse();
    assertThat(diff.adjacent("bar", "foo")).isFalse();
  }

  @Test public void test2() {
    assertThat(diff.adjacent("foo", "fob")).isTrue();
    assertThat(diff.adjacent("fob", "foo")).isTrue();
  }

  @Test public void test3() {
    assertThat(diff.adjacent("foo", "fOO")).isFalse();
    assertThat(diff.adjacent("foo", "fOo")).isTrue();
  }

  @Test public void test4() {
    assertThat(diff.adjacent("foo", "fooo")).isFalse();
    assertThat(diff.adjacent("foo", "fo")).isFalse();
    assertThat(diff.adjacent("fooo", "foo")).isFalse();
    assertThat(diff.adjacent("fo", "foo")).isFalse();
  }
}
