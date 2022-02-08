package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import map.*;

class TestSimpleHashMap {
	Map<Integer, Integer> m;
	Map<Integer, Integer> m16;
	Map<String, Integer> s;

	@BeforeEach
	void setUp() {
		m = new SimpleHashMap<Integer, Integer>(10);
		m16 = new SimpleHashMap<Integer, Integer>();
		s = new SimpleHashMap<String, Integer>();
	}

	@AfterEach
	void tearDown() {
		m = null;
		m16 = null;
	}

	@Test
	void testEmpty() {
		assertTrue(m.isEmpty(), "isEmpty false for empty set");
		m.put(1, 1);
		assertFalse(m.isEmpty(), "isEmpty true for non empty set");
	}

	@Test
	void testPutInEmptyMap() {
		assertNull(m.put(1, 1), "wrong put(1, 1):");
		assertEquals(1, m.size(), "wrong size():");
		assertEquals(Integer.valueOf(1), m.get(1), "key not found in map: 1");
	}

	@Test
	void testPutSameIndex() {
		m16.put(1, 1);
		m16.put(17, 17);
		assertEquals(2, m16.size(), "wrong size():");
		assertEquals(Integer.valueOf(1), m16.get(1), "key not found in map: 1");
		assertEquals(Integer.valueOf(17), m16.get(17), "key not found in map: 17");
	}

	@Test
	void testDuplicates() {
		m16.put(1, 1);
		m16.put(17, 17);
		assertEquals(Integer.valueOf(1), m16.put(1, 2), "wrong put(1, 2):");
		assertEquals(Integer.valueOf(17), m16.put(17, 18), "wrong put(17, 18):");
		assertEquals(2, m16.size(), "wrong size():");
		assertEquals(Integer.valueOf(2), m16.get(1), "key not found in map: 2");
		assertEquals(Integer.valueOf(18), m16.get(17), "key not found in map: 18");
	}

	@Test
	void testNegative() {
		m.put(1, 1);
		m.put(-17, -17);
		assertEquals(2, m.size(), "wrong size():");
		assertEquals(Integer.valueOf(-17), m.get(-17), "key not found in map: -17");
	}

	@Test
	void testIntegerMinValue() {
		m.put(1, 1);
		m.put(Integer.MIN_VALUE, 17);
		assertEquals(2, m.size(), "wrong size():");
		assertEquals(Integer.valueOf(17), m.get(Integer.MIN_VALUE), "key not found in map: Integer.MIN_VALUE");
	}

	@Test
	void testSize() {
		assertEquals(0, m.size(), "wrong size():");
		m.put(0, 0);
		assertEquals(1, m.size(), "wrong size():");
		for (int i = 1; i < 100; i++) {
			m.put(i, i);
		}
		assertEquals(100, m.size(), "wrong size():");
	}

	@Test
	void testGetInEmptyMap() {
		assertNull(m.get(1), "wrong get():");
	}

	@Test
	void testGet() {
		m16.put(1, 1);
		m16.put(17, 17);
		m16.put(33, 33);
		m16.put(49, 49);
		m16.put(200, 200);
		assertEquals(5, m16.size(), "wrong size():");
		assertEquals(Integer.valueOf(1), m16.get(1), "key not found in map: 1"); // last or first
		assertEquals(Integer.valueOf(17), m16.get(17), "key not found in map: 17"); // middle
		assertEquals(Integer.valueOf(33), m16.get(33), "key not found in map: 33"); // middle
		assertEquals(Integer.valueOf(49), m16.get(49), "key not found in map: 49"); // first or last
		assertEquals(Integer.valueOf(200), m16.get(200), "key not found in map: 200");
	}

	@Test
	void testRemoveInEmptyMap() {
		assertNull(m.remove(1), "wrong remove():");
	}

	@Test
	void testRemoveOneElement() {
		m.put(1, 1);
		assertNull(m.remove(2), "wrong remove():");
		assertEquals(Integer.valueOf(1), m.remove(1), "wrong result from remove: 1");
		assertNull(m.get(1), "wrong get():");
		assertNull(m.put(1, 1), "wrong put():");
	}

	@Test
	void testRemove() {
		m16.put(1, 1);
		m16.put(17, 17);
		m16.put(33, 33);
		m16.put(49, 49);
		m16.put(65, 65);
		assertEquals(5, m16.size(), "wrong size():");
		assertEquals(Integer.valueOf(65), m16.remove(65), "wrong result from remove: 1"); // first or last
		assertEquals(Integer.valueOf(1), m16.remove(1), "wrong result from remove: 1"); // last or first
		assertEquals(Integer.valueOf(33), m16.remove(33), "wrong result from remove: 1"); // middle
		assertEquals(Integer.valueOf(49), m16.remove(49), "wrong result from remove: 1");
		assertEquals(Integer.valueOf(17), m16.remove(17), "wrong result from remove: 1");
		assertEquals(0, m16.size(), "wrong size():");
		assertNull(m16.get(1), "wrong get():");
		assertNull(m16.get(17), "wrong get():");
		assertNull(m16.get(33), "wrong get():");
		assertNull(m16.get(49), "wrong get():");
		assertNull(m16.get(65), "wrong get():");
	}

	@Test
	void testRemoveNonExistingElements() {
		m16.put(1, 1);
		m16.put(17, 17);
		m16.put(33, 33);
		assertEquals(3, m16.size(), "wrong size():");
		assertNull(m16.remove(49), "wrong result from remove: 49"); // non-existing element in non-empty list
		assertEquals(3, m16.size(), "wrong size():");
		assertNull(m16.remove(2), "wrong result from remove: 2"); // non-existing element in empty list
		assertEquals(3, m16.size(), "wrong size():");
		assertEquals(Integer.valueOf(1), m16.get(1), "key not found in map: 1");
		assertEquals(Integer.valueOf(17), m16.get(17), "key not found in map: 17");
		assertEquals(Integer.valueOf(33), m16.get(33), "key not found in map: 33");
	}

	@Test
	void testManyPutAndRemove() {
		java.util.Random random = new java.util.Random(123456);
		HashSet<Integer> randNbrs = new HashSet<Integer>();
		for (int i = 0; i < 100000; i++) {
			int r = random.nextInt(10000);
			m16.put(r, r);
			randNbrs.add(r);
		}
		for (int i : randNbrs) {
			assertEquals(Integer.valueOf(i), m16.remove(i), "key not found in map:" + i);
		}
		assertEquals(0, m16.size(), "wrong size():");
	}

	@Test
	void testManyPutAndGet() {
		java.util.Random random = new java.util.Random(123456);
		HashSet<Integer> randNbrs = new HashSet<Integer>();
		for (int i = 0; i < 100; i++) {
			int r = random.nextInt(10000);
			m16.put(r, r);
			randNbrs.add(r);
		}
		for (int i : randNbrs) {
			assertEquals(Integer.valueOf(i), m16.get(i), "key not found in map:" + i);
		}
	}

	@Test
	void testputAndGetStringKeys() {
		s.put("abc", 0);
		s.put("def", 1);
		s.put("ghi", 2);
		s.put("jkl", 3);
		s.put("abc", 4);
		assertEquals(4, s.size(), "wrong size():");
		// Uses new String to make sure that target keys are different objects than
		// those in the map.
		assertEquals(Integer.valueOf(4), s.get(new String("abc")), "key not found in map: 1");
		assertEquals(Integer.valueOf(1), s.get(new String("def")), "key not found in map: 17");
		assertEquals(Integer.valueOf(2), s.get(new String("ghi")), "key not found in map: 33");
		assertEquals(Integer.valueOf(3), s.get(new String("jkl")), "key not found in map: 49");
	}

	@Test
	void testManyPutAndRemoveStrings() {
		java.util.Random random = new java.util.Random(123456);
		HashSet<Integer> randNbrs = new HashSet<>();
		for (int i = 0; i < 100000; i++) {
			int r = random.nextInt(10000);
			s.put(Integer.toString(r), r);
			randNbrs.add(r);
		}
		for (int i : randNbrs) {
			assertEquals(Integer.valueOf(i), s.remove(Integer.toString(i)), "key not found in map:" + i);
		}
		assertEquals(0, m16.size(), "wrong size():");
	}
}
