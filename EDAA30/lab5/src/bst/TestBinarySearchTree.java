package bst;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

class TestBinarySearchTree {
	private BinarySearchTree<Integer> binaryTree;

	@BeforeEach
	void setUp() {
		binaryTree = new BinarySearchTree<Integer>();
	}

	@AfterEach
	void tearDown() {
		binaryTree = null;
	}

	@Test
	void testHeight() {
		assertEquals(0, binaryTree.height(), "Height is not equal to 0");
		binaryTree.add(20);
		binaryTree.add(10);
		binaryTree.add(47);
		binaryTree.add(30);
		assertEquals(3, binaryTree.height(), "Height is not equal to 3");
	}

	@Test
	void testAdd() {
		binaryTree.add(20);
		binaryTree.add(10);
		binaryTree.add(47);
		binaryTree.add(30);

		assertEquals(false, binaryTree.add(30), "Add does not return false for duplicate element");
		assertEquals(true, binaryTree.add(12), "Add does not return true for new element");
	}

	@Test
	void testSize() {
		assertEquals(0, binaryTree.size(), "Size is not equal to 0");
		binaryTree.add(20);
		binaryTree.add(10);
		binaryTree.add(47);
		binaryTree.add(30);
		assertEquals(4, binaryTree.size(), "Size is not equal to 4");
	}

	@Test
	void testClear() {
		binaryTree.add(20);
		binaryTree.add(10);
		binaryTree.add(47);
		binaryTree.add(30);
		binaryTree.clear();
		assertEquals(0, binaryTree.size(), "Size is not equal to 0");
		assertEquals(null, binaryTree.root, "Root is not null");
	}
}
