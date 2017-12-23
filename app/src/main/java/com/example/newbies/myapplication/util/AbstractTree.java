package com.example.newbies.myapplication.util;

/**
 * @author NewBies
 */
public abstract class AbstractTree<E extends Comparable<E>> implements Tree<E> {
	/**中序遍历该树**/
	@Override
	public void inorder(){

	}

	/**后序遍历该树**/
	@Override
	public void postorder(){

	}

	/**前序遍历该树**/
	@Override
	public void preorder(){

	}

	/**判断该树是否为空**/
	@Override
	public boolean isEmpty(){
		return getSize() == 0;
	}

	/**返回一个遍历该树的迭代器**/
	@Override
	public java.util.Iterator iterator(){
		return null;
	}
}
