package com.example.newbies.myapplication.util;

/**
 * @author NewBies
 */
public class GenericQueue<E>{
	private java.util.LinkedList<E> list = new java.util.LinkedList<E>();

	/**
	 * 入队列
	 * @param e
	 */
	public void enqueue(E e){
		list.addLast(e);
	}

	/**
	 * 出队列
	 * @return
	 */
	public E dequeue(){
		return list.removeFirst();
	}

	public int getSize(){
		return list.size();
	}

	@Override
	public String toString(){
		return "Queue: " + list.toString();
	}
}
