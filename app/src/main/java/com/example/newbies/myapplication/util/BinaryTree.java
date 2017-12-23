package com.example.newbies.myapplication.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

/**
 * @author NewBies
 */
public class BinaryTree<E extends Comparable<E>> extends AbstractTree<E>{

	protected TreeNode<E> root;
	protected int size = 0;

	/**创建一个默认的二叉树**/
	public BinaryTree(){

	}

	/**创建一个有数组构成的二叉树**/
	public BinaryTree(E[] objects){
		for(int i = 0; i < objects.length; i++){
			insert(objects[i]);
		}
	}

	/**如果该元素在该树中则返回真**/
	@Override
	public boolean search(E e) {
		//从根节点开始
		TreeNode<E> current = root;

		while(current != null){
			if(e.compareTo(current.element) < 0){
				current = current.left;
			}
			else if(e.compareTo(current.element) > 0){
				current = current.right;
			}
			else {
				return true;
			}
		}
		return false;
	}

	/**将一个元素插入该树，如果插入成功则返回true**/
	@Override
	public boolean insert(E e) {

		if(root == null){
			root = createNewNode(e);
		}
		else{
			TreeNode<E> parent = null;
			TreeNode<E> current = root;

			while(current != null) {
				if (e.compareTo(current.element) < 0) {
					parent = current;
					current = current.left;
				} else if (e.compareTo(current.element) > 0) {
					parent = current;
					current = current.right;
				} else {
					return false;
				}
			}

			/**创建一个新的结点，并加入到父节点上**/
			if(e.compareTo(parent.element) < 0){
				parent.left = createNewNode(e);
			}
			else {
				parent.right = createNewNode(e);
			}
		}

		size++;
		return true;
	}

	protected TreeNode<E> createNewNode(E e){
		return new TreeNode<E>(e);
	}

	/**中序遍历该树**/
	@Override
	public void inorder(){
		inorder(root);
	}
	/**中序遍历子树**/
	protected void inorder(TreeNode<E> root){
		if(root == null) {
			return;
		}
		inorder(root.left);
		System.out.print(root.element + " ");
		inorder(root.right);
	}

	/**后序遍历该树**/
	@Override
	public void postorder(){
		postorder(root);
	}
	/**后序遍历子树**/
	protected void postorder(TreeNode<E> root){
		if(root == null) {
			return;
		}
		postorder(root.left);
		postorder(root.right);
		System.out.print(root.element + " ");
	}

	/**前序遍历该树**/
	@Override
	public void preorder(){
		preorder(root);
	}
	/**前序遍历子树**/
	protected void preorder(TreeNode<E> root){
		if(root == null) {
			return;
		}
		System.out.print(root.element + " ");
		preorder(root.left);
		preorder(root.right);
	}

	/**
	 * 从该二叉树中删除一个元素
	 * 删除成功则返回真
	 * 如果该树中没有指定元素，则返回假
	 */
	@Override
	public boolean delete(E e) {
		//定位删除的结点，并定位其父节点
		TreeNode<E> parent = null;
		TreeNode<E> current = root;
		while(current != null){
			if(e.compareTo(current.element) < 0){
				parent = current;
				current = current.left;
			}
			else if(e.compareTo(current.element) > 0){
				parent = current;
				current = current.right;
			}
			else {
				break;
			}
		}

		if(current == null) {
			//如果这个元素不在这个二叉树中
			return false;
		}

		//第一种情况，当没有左子树时
		if(current.left == null){
			//将父节点连接到当前节点的正确子节点
			if(parent == null){
				root = current.right;
			}
			else{
				if(e.compareTo(parent.element) < 0) {
					parent.left = current.right;
				} else {
					parent.right = current.right;
				}
			}
		}
		//第二种情况：当前节点有一个左子节点，它在当前节点的左子树中找到最右边的节点，以及它的父节点。
		else{
			TreeNode<E> parentOfRightMost = current;
			TreeNode<E> rightMost = current.left;

			while(rightMost != null){
				parentOfRightMost = rightMost;
				//一直向右走
				rightMost = rightMost.right;
			}

			//用最右边的元素替换当前元素
			current.element = rightMost.element;

			//消除右边的结点
			if(parentOfRightMost.right == rightMost){
				parentOfRightMost.right = rightMost.left;
			}
			else{
				//特殊情况：最右的父节点等于current
				parentOfRightMost = rightMost.left;
			}
		}

		size--;
		return true;
	}

	/**得到该树的大小**/
	@Override
	public int getSize() {
		return size;
	}

	/**得到该树的根节点**/
	public TreeNode getRoot(){
		return root;
	}

	/**返回从根节点指定结点的路径**/
	public ArrayList<TreeNode<E>> path(E e){
		ArrayList<TreeNode<E>> list = new ArrayList<TreeNode<E>>();
		TreeNode<E> current = root;

		while(current != null) {
			//添加一个结点到这个线性表
			list.add(current);
			if (e.compareTo(current.element) < 0) {
				current = current.left;
			} else if (e.compareTo(current.element) > 0) {
				current = current.right;
			} else {
				break;
			}
		}
		//返回一个结点的线性表
		return list;
	}

	/**获得指定结点所在层数**/
	public int getNodeLevel(E e){
		return getNodeLevel(root,e, 1);
	}

	/**获得指定结点所在层数**/
	private int getNodeLevel(TreeNode<E> root,E e,int  level){
		if(root != null){
			//查找成功
			if(root.element.equals(e)){
				return level;
			}
			else{
				level++;
				//判断所查找值是否在左边
				int temp = getNodeLevel(root.left,e, level);
				if(temp == 0){
					//判断查找的值是否在右边
					return getNodeLevel(root.right, e,level);
				}
				else {
					return temp;
				}
			}
		}
		return 0;
	}

	/**得到指定层数的结点个数**/
	public int getLevelNodeNum(int level){
		return getLevelNodeNum(root,  level, 0);
	}

	/**得到指定层数的结点个数**/
	private int getLevelNodeNum(TreeNode<E> root,int level, int sum){
		if(root == null){
			return sum;
		}
		//找到了该层
		if(level == 0){
			return ++sum;
		}
		//进入下一层
		level--;
		//遍历左树
		sum = getLevelNodeNum(root.left, level, sum);
		//遍历右树
		sum = getLevelNodeNum(root.right, level, sum);
		return sum;
	}

	/**输出指定结点的父节点**/
	public void printPrent(E e){
		System.out.println(root.element);
		printPrent(e, root);
	}

	/**输出指定结点的父节点**/
	private boolean printPrent(E e, TreeNode<E> root){
		if(root == null||root.left == null||root.right == null){
			return false;
		}
		else if(root.left.element.equals(e)||root.right.element.equals(e)){
			System.out.println(root.element);
			return true;
		}
		else if(printPrent(e, root.left)||printPrent(e, root.right)){
			System.out.println(root.element);
			return true;
		}
		return false;
	}

	/**判断两棵二叉树是否相似**/
	public boolean isLike(BinaryTree<E> tree){
		return (tree.size == this.size)&&isLike(tree.getRoot(),this.root);
	}

	/**判断两棵二叉树是否相似**/
	private boolean isLike(TreeNode<E> e1, TreeNode<E> e2){
		//当有一个二叉树的一条支路遍历完时，另一个也应该遍历完
		if(e1 == null && e2 == null){
			return true;
		}
		//否则，只要有一个二叉树的一个支路遍历完而另一个没有，说明不相似
		else if(e1 == null|| e2 == null){
			return false;
		}
		return (isLike(e1.left, e2.left)&&isLike(e1.right,e2.right));
	}

	/**获得二叉树结点数量**/
	public int getNodeNum(){
		return getNodeNum(root);
	}

	/**获得二叉树结点数量**/
	private int getNodeNum(TreeNode<E> e){
		if(e == null){
			return 0;
		}
		return getNodeNum(e.left) + getNodeNum(e.right) + 1;
	}

	/**输出叶子结点**/
	public void printLeaf(){
		printLeaf(root);
	}

	/**输出叶子结点**/
	private void printLeaf(TreeNode<E> e){
		if(e != null){
			if(e.left == null && e.right == null){
				System.out.println(e.element);
			}
			printLeaf(e.left);
			printLeaf(e.right);
		}
	}

	/**非递归方式后续遍历输出**/
	public void postOrderInLoop(){
		//为了避免结点所存值相同而无法判别的问题，这里采用通过结点来判断
		Stack<TreeNode<E>> stack = new Stack<TreeNode<E>>();
		Stack<TreeNode<E>> tempStack = new Stack<TreeNode<E>>();
		boolean isChanged = false;
		TreeNode<E> current = root;
		while(current != null){
			stack.push(current);
			//如果左子节点为空，但右子节点不为空，将其存入stack栈中
			if(current.right != null){
				if(current.left == null){
					current = current.right;
					stack.push(current);
				}
				else {//如果左右结点都不为空，将该结点存入tempStack栈中
					tempStack.push(current);
				}
			}
			if(current.left == null && current.right == null){
				while(!stack.isEmpty()){
					if(!tempStack.isEmpty()){
						if(stack.peek() == tempStack.peek()){
							current = tempStack.pop();
							isChanged = true;
							break;
						}
						else {
							System.out.println(stack.pop().element);
						}
					}
					else {
						System.out.println(stack.pop().element);
					}
				}
			}
			if(!isChanged){
				current = current.left;
			}
			else {
				isChanged = false;
				current = current.right;
			}
		}
	}

	public int levelTraverse(TreeNode<E> root) {
		int width = 0;
		int i = 0;
		if (root == null) {
			return 0;
		}
		LinkedList<TreeNode<E>> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			TreeNode<E> node = queue.poll();

			if (node.left != null) {
				queue.offer(node.left);
			}
			if (node.right != null) {
				queue.offer(node.right);
			}
			if(node.left != null&&node.right != null){

			}
			i++;
		}

		return width;
	}

	/**获得一个中序遍历的迭代器**/
	public Iterator inorderIterator(){
		return new InorderIterator();
	}

	/**InorderIterator内部类**/
	class InorderIterator implements Iterator{
		//存储元素的线性表
		private ArrayList<E> list = new ArrayList<E>();
		//指向线性表的指针
		private int current = 0;

		public InorderIterator(){
			//中序遍历该树并将其各个元素储存到线性表中
			inorder();
		}

		/**从根节点开始，中序遍历该树**/
		private void inorder(){
			inorder(root);
		}

		/**中序遍历子树**/
		private void inorder(TreeNode<E> root){
			if(root == null){
				return;
			}

			inorder(root.left);
			list.add(root.element);
			inorder(root.right);
		}

		/**判断继续遍历是否还存在元素**/
		@Override
		public boolean hasNext() {
			if(current < list.size())
				return true;

			return false;
		}

		/**得到下一个元素**/
		@Override
		public Object next() {
			return list.get(current++);
		}

		/**删除当前元素并刷新列表**/
		@Override
		public void remove(){
			//删除当前元素
			delete(list.get(current));
			//清空线性表
			list.clear();
			//重建线性表
			inorder();
		}
	}

	/**
	 * 清空该树
	 **/
	public void clear(){
		root = null;
		size = 0;
	}

	public static class TreeNode<E>{
		public E element;
		public TreeNode<E> left;
		public TreeNode<E> right;

		public TreeNode(E element){
			this.element = element;
		}
	}
}
