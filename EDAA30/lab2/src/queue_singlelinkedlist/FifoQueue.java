package queue_singlelinkedlist;

import java.util.*;

public class FifoQueue<E> extends AbstractQueue<E> implements Queue<E> {
	private QueueNode<E> last;
	private int size;

	public FifoQueue() {
		super();
		last = null;
		size = 0;
	}

	/**
	 * Inserts the specified element into this queue, if possible
	 * post: The specified element is added to the rear of this queue
	 * 
	 * @param e the element to insert
	 * @return true if it was possible to add the element
	 *         to this queue, else false
	 */
	public boolean offer(E e) {
		QueueNode<E> node = new QueueNode<>(e);

		if (last == null) {
			node.next = node;
		} else {
			node.next = last.next;
			last.next = node;
		}

		last = node;
		size++;

		return true;
	}

	/**
	 * Returns the number of elements in this queue
	 * 
	 * @return the number of elements in this queue
	 */
	public int size() {
		return size;
	}

	/**
	 * Retrieves, but does not remove, the head of this queue,
	 * returning null if this queue is empty
	 * 
	 * @return the head element of this queue, or null
	 *         if this queue is empty
	 */
	public E peek() {
		if (last == null) {
			return null;
		}

		return last.next.element;
	}

	/**
	 * Retrieves and removes the head of this queue,
	 * or null if this queue is empty.
	 * post: the head of the queue is removed if it was not empty
	 * 
	 * @return the head of this queue, or null if the queue is empty
	 */
	public E poll() {
		QueueNode<E> delNode = last;

		if (last == null) {
			return null;
		}

		if (last.next == last) {
			last = null;
		} else {
			delNode = last.next;
			last.next = delNode.next;
		}

		size--;

		return delNode.element;
	}

	/**
	 * Appends the specific queue to this queue
	 * post: all elements from the specific queue are appended
	 * to this queue. The specific queue (q) is empty after the call
	 * 
	 * @param q the queue to append
	 * @throws IllegalArgumentException if this queue and q are identical
	 */
	public void append(FifoQueue<E> q) {
		if (q == this) {
			throw new IllegalArgumentException();
		}

		if (q.last != null) {
			QueueNode<E> qFirst = q.last.next;
			QueueNode<E> qLast = q.last;

			if (last != null) {
				qLast.next = last.next;
				last.next = qFirst;
			}

			last = qLast;
			size += q.size;

			q.last = null;
			q.size = 0;
		}
	}

	/**
	 * Returns an iterator over the elements in this queue
	 * 
	 * @return an iterator over the elements in this queue
	 */
	public Iterator<E> iterator() {
		return new QueueIterator();
	}

	private static class QueueNode<E> {
		E element;
		QueueNode<E> next;

		private QueueNode(E x) {
			element = x;
			next = null;
		}
	}

	private class QueueIterator implements Iterator<E> {
		private QueueNode<E> pos;

		private QueueIterator() {
			pos = last;
		}

		@Override
		public boolean hasNext() {
			return pos != null;
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			QueueNode<E> next = pos.next;
			pos = pos.next;

			if (pos == last) {
				pos = null;
			}

			return next.element;
		}
	}
}
