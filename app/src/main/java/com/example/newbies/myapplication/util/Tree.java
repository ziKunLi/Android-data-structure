package com.example.newbies.myapplication.util;

public interface Tree<E extends Comparable<E>> {
	/**如果该元素在该树上，那么返回真**/
	boolean search(E e);

	/**向树中插入一个元素，插入成功则返回true**/
	boolean insert(E e);

	/**从树中删除一个元素，删除成功则返回true**/
	boolean delete(E e);

	/**中序遍历该树**/
	void inorder();

	/**后序遍历该树**/
	void postorder();

	/**前序遍历该树**/
	void preorder();

	/**得到树的大小**/
	int getSize();

	/**判断该树是否为空**/
	boolean isEmpty();

	/**返回一个迭代器去浏览该树中的所有元素**/
	java.util.Iterator iterator();
}