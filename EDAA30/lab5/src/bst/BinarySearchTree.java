package bst;

import java.util.ArrayList;
import java.util.Comparator;

public class BinarySearchTree<E> {
	BinaryNode<E> root; // Används också i BSTVisaulizer
	BinaryNode<E> parentNode;
	int size; // Används också i BSTVisaulizer
	private Comparator<E> ccomparator;

	public static void main(String[] args) {
		BSTVisualizer visualizer = new BSTVisualizer("Binary Tree", 500, 500);
		BinarySearchTree<Integer> binaryTree = new BinarySearchTree<Integer>();

		for (int i = 0; i < 11; i++) {
			binaryTree.add(i);
		}

		binaryTree.rebuild();
		visualizer.drawTree(binaryTree);
		binaryTree.printTree();
	}

	/**
	 * Constructs an empty binary search tree.
	 */
	public BinarySearchTree() {

	}

	/**
	 * Constructs an empty binary search tree, sorted according to the specified
	 * comparator.
	 */
	public BinarySearchTree(Comparator<E> comparator) {
		this.ccomparator = comparator;
	}

	/**
	 * Inserts the specified element in the tree if no duplicate exists.
	 * 
	 * @param x element to be inserted
	 * @return true if the the element was inserted
	 */
	/**
	 * Inserts the specified element in the tree if no duplicate exists.
	 * 
	 * @param x element to be inserted
	 * @return true if the the element was inserted
	 */
	public boolean add(E x) {
		if (root == null) {
			root = new BinaryNode<E>(x);
			size++;
			return true;
		}

		return add(root, x);
	}

	private boolean add(BinaryNode<E> parentNode, E node) {
		if (compareElements(parentNode.element, node) == 0) {
			return false;
		} else if (compareElements(parentNode.element, node) < 0) {
			if (parentNode.right == null) {
				parentNode.right = new BinaryNode<E>(node);
				size++;

				return true;
			} else {
				return add(parentNode.right, node);
			}
		} else {
			if (parentNode.left == null) {
				parentNode.left = new BinaryNode<E>(node);
				size++;

				return true;
			} else {
				return add(parentNode.left, node);
			}
		}
	}

	public int compareElements(E a, E b) {
		if (ccomparator == null) {
			return ((Comparable<E>) a).compareTo(b);
		} else {
			return ccomparator.compare(a, b);
		}
	}

	/**
	 * Computes the height of tree.
	 * 
	 * @return the height of the tree
	 */
	public int height() {
		return recursiveHeight(root);
	}

	private int recursiveHeight(BinaryNode<E> node) {
		if (node == null) {
			return 0;
		} else {
			return 1 + Math.max(recursiveHeight(node.left), recursiveHeight(node.right));
		}
	}

	/**
	 * Returns the number of elements in this tree.
	 * 
	 * @return the number of elements in this tree
	 */
	public int size() {
		return size;
	}

	/**
	 * Removes all of the elements from this list.
	 */
	public void clear() {
		root = null;
		size = 0;
	}

	/**
	 * Print tree contents in inorder.
	 */
	public void printTree() {
		recPrint(root);
	}

	private void recPrint(BinaryNode<E> node) {
		if (node != null) {
			recPrint(node.left);
			System.out.println(node.element);
			recPrint(node.right);
		}
	}

	private ArrayList<E> list;

	/**
	 * Builds a complete tree from the elements in the tree.
	 */
	public void rebuild() {
		list = new ArrayList<E>();
		toArray(root, list);
		root = buildTree(list, 0, list.size() - 1);
	}

	/*
	 * Adds all elements from the tree rooted at n in inorder to the list sorted.
	 */
	private void toArray(BinaryNode<E> n, ArrayList<E> sorted) {
		if (n != null) {
			toArray(n.left, sorted);
			sorted.add(n.element);
			toArray(n.right, sorted);
		}
	}

	/*
	 * Builds a complete tree from the elements from position first to last in the
	 * list sorted. Elements in the list a are assumed to be in ascending order.
	 * Returns the root of tree.
	 */
	private BinaryNode<E> buildTree(ArrayList<E> sorted, int first, int last) {
		if (first > last) {
			return null;
		} else {
			int mid = first + ((last - first) / 2);
			BinaryNode<E> binaryNode = new BinaryNode<E>(sorted.get(mid));
			binaryNode.left = buildTree(sorted, first, mid - 1);
			binaryNode.right = buildTree(sorted, mid + 1, last);
			return binaryNode;
		}
	}

	static class BinaryNode<E> {
		E element;
		BinaryNode<E> left;
		BinaryNode<E> right;

		private BinaryNode(E element) {
			this.element = element;
		}
	}

}
