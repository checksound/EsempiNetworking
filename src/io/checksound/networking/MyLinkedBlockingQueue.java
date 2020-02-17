package io.checksound.networking;

import java.util.LinkedList;

public class MyLinkedBlockingQueue<E> {
	private LinkedList<E> taskList = new LinkedList<E>();

	public void clear() {
		synchronized (taskList) {
			taskList.clear();
		}
	}

	public void add(E task) {
		synchronized (taskList) {
			taskList.addLast(task);
			taskList.notify();
		}
	}

	public E take() throws InterruptedException {
		synchronized (taskList) {
			while (taskList.isEmpty())
				taskList.wait();
			return taskList.removeFirst();
		}
	}
}
