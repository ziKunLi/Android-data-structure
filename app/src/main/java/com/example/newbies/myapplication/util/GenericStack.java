package com.example.newbies.myapplication.util;

/**
 * @author NewBies
 */
public class GenericStack<E>{
	private java.util.ArrayList<E> list = new java.util.ArrayList<E>();

	public int getSize(){
		return list.size();
	}

	/**
	 * 查看栈顶元素
	 * @return
	 */
	public E peek(){
		return list.get(getSize() - 1);
	}

	/**
	 * 进栈
	 * @param o
	 */
	public void push(E o){
		list.add(o);
	}

	/**
	 * 出栈
	 * @return
	 */
	public E pop(){
		E o = list.get(getSize() - 1);
		list.remove(getSize() - 1);
		return o;
	}

	public boolean isEmpty(){
		return list.isEmpty();
	}
}