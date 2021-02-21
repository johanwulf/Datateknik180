package testqueue;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import java.util.Queue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import queue_singlelinkedlist.FifoQueue;

class NewFifoQueueTest {
	private FifoQueue<Integer> firstIntQueue;
	private FifoQueue<Integer> secondIntQueue;
	
	@BeforeEach
	void setUp() {
		firstIntQueue = new FifoQueue<Integer>();
		secondIntQueue = new FifoQueue<Integer>();
	}

	@AfterEach
	void tearDown(){
		firstIntQueue = null;
		secondIntQueue = null;
	}

	/**
	 * Try to merge two empty queues
	 */
	@Test
	void testEmptyAppend() {
		firstIntQueue.append(secondIntQueue);
		assertEquals(0, firstIntQueue.size(), "Size of firstIntQueue is not equal to 0");
		assertEquals(0, secondIntQueue.size(), "Size of secondIntQueue is not equal to 0");
		assertEquals(null, firstIntQueue.peek(), "Front of firstIntQueue is not null");
		assertEquals(null, firstIntQueue.peek(), "Front of secondIntQueue is not null");
	}
	
	/**
	 * Try to merge an empty queue to a queue with elements
	 */
	@Test
	void testEmptyToQueueAppend() {
		for(int i = 0; i < 5; i++) {
			firstIntQueue.offer(i);
		}
		
		firstIntQueue.append(secondIntQueue);
		
		assertEquals(5, firstIntQueue.size(), "Size of firstIntQueue is not equal to 5");
		
		for(int i = 0; i < 5; i++) {
			assertEquals(i, firstIntQueue.poll(), "Values of firstIntQueue are not correct");
		}
		
		assertEquals(0, secondIntQueue.size(), "Size of secondIntQueue is not equal to 0");
		assertEquals(null, secondIntQueue.poll(), "Poll of secondIntQueue is not null");
	}
	
	/**
	 * Try to merge a queue with elements to an empty queue
	 */
	@Test
	void testQueueToEmptyAppend() {
		for(int i = 0; i < 5; i++) {
			firstIntQueue.offer(i);
		}
		
		secondIntQueue.append(firstIntQueue);
		
		assertEquals(5, secondIntQueue.size(), "Size of secondIntQueue is not equal to 5");
		
		for(int i = 0; i < 5; i++) {
			assertEquals(i, secondIntQueue.poll(), "Values of firstIntQueue are incorrect");
		}
		
		assertEquals(0, firstIntQueue.size(), "Size of firstIntQueue is not equal to 0");
		assertEquals(null, firstIntQueue.poll(), "Poll of firstIntQueue is not null");
	}
	
	/**
	 * Try to merge two queues
	 */
	@Test
	void testTwoQueuesAppend() {
		for(int i = 1; i <= 10; i++) {
			if(i <= 5) {
				firstIntQueue.offer(i);
			} else {
				secondIntQueue.offer(i);
			}
		}
		
		firstIntQueue.append(secondIntQueue);
		
		assertEquals(10, firstIntQueue.size(), "Size of firstIntQueue is not equal to 10");

		for(int i = 1; i <= 10; i++) {
			assertEquals(i, firstIntQueue.poll(), "Values of firstIntQueue are incorrect");
		}
		
		assertEquals(0, secondIntQueue.size(), "Size of secondIntQueue is not equal to 0");
		assertEquals(null, secondIntQueue.poll(), "Poll of secondIntQueue is not null");
	}
	
	/**
	 * Try to merge a queue with itself
	 */
	@Test
	void testSelfAppend() {
		assertThrows(IllegalArgumentException.class, () -> firstIntQueue.append(firstIntQueue));
	}
}
