package com.example.newbies.myapplication.util;

import android.util.Log;

import com.example.newbies.myapplication.util.Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractGraph<V> implements Graph<V> {
	/**
	 * 用于存储结点
	 */
	protected List<V> vertices;
	/**
	 * 邻接表
	 */
	protected List<List<Integer>> neighbors;

	/**
	 * 空的构造方法
	 */
	protected AbstractGraph() {
	}

	/**从数组中存储的边和顶点构造一个图**/
	public AbstractGraph(int[][] edges, V[] vertices){
		this.vertices = new ArrayList<V>();
		for(int i = 0; i < vertices.length; i++){
			this.vertices.add(vertices[i]);
		}
		createAdjacencyLists(edges, vertices.length);
	}

	protected AbstractGraph(List<Edge> edges, List<V> vertices){
		this.vertices = vertices;

		createAdjacencyLists(edges, vertices.size());
	}

	@SuppressWarnings("unchecked")
	protected AbstractGraph(List<Edge> edges, int numberOfVertices){
		vertices = new ArrayList<V>();
		for(int i = 0; i < numberOfVertices; i++){
			//顶点是{0,1,2,3...}
			vertices.add((V)(new Integer(i)));
		}

		createAdjacencyLists(edges, numberOfVertices);
	}

	@SuppressWarnings("unchecked")
	protected AbstractGraph(int[][] edges, int numberOfVertices){
		//创建顶点
		vertices = new ArrayList<V>();
		for(int i = 0; i < numberOfVertices; i++){
			//顶点是{1,2,3...}
			vertices.add((V)(new Integer(i)));
		}

		createAdjacencyLists(edges, numberOfVertices);
	}

	/**
	 * 向图中加入顶点
	 * @param vertex
	 */
	public void addVertex(V vertex) {
		vertices.add(vertex);
		neighbors.add(new ArrayList<Integer>());
	}

	/**
	 * 向图中加入边
	 * @param u
	 * @param v
	 */
	public void addEdge(int u, int v) {
		neighbors.get(u).add(v);
		neighbors.get(v).add(u);
	}
	/**
	 * 为每个顶点创建一个邻接线性表
	 * @param edges
	 * @param numberOfVertices
	 */
	private void createAdjacencyLists(int[][] edges, int numberOfVertices){
		//创建一个链表
		neighbors = new ArrayList<List<Integer>>();
		for(int i = 0; i < numberOfVertices; i++){
			neighbors.add(new ArrayList<Integer>());
		}

		for(int i = 0; i < edges.length; i++){
			int u = edges[i][0];
			int v = edges[i][1];
			neighbors.get(u).add(v);
		}
	}

	/**
	 * 程序完善第二题
	 * 为每个顶点创建一个邻接线性表
	 * @param edges
	 * @param numberOfVertices
	 */
	private void createAdjacencyLists(List<Edge> edges, int numberOfVertices){
		//创建一个线性表
		neighbors = new ArrayList<List<Integer>>();
		for(int i = 0; i < numberOfVertices; i++){
			neighbors.add(new ArrayList<Integer>());
		}

		for(Edge edge : edges){
			neighbors.get(edge.u).add(edge.v);
		}

		if (edges.get(0) instanceof WeightedEdge){
			return;
		}
		/**
		 * 对邻居排序
		 */
		for(int i = 0; i < neighbors.size(); i++){
			Collections.sort(neighbors.get(i));
		}
	}

	/**返回该图中顶点的个数**/
	@Override
	public int getSize(){
		return vertices.size();
	}

	/**返回图中所有顶点**/
	@Override
	public List<V> getVertices(){
		return vertices;
	}

	/**返回特定角标处的顶点**/
	@Override
	public V getVertex(int index){
		return vertices.get(index);
	}

	/**返回指定角标的角标**/
	@Override
	public int getIndex(V v){
		return vertices.indexOf(v);
	}

	/**返回特定角标处顶点的相邻顶点**/
	@Override
	public List<Integer> getNeighbors(int index){
		return neighbors.get(index);
	}

	/**返回指定顶点的度数**/
	@Override
	public int getDegree(int v){
		return neighbors.get(v).size();
	}

	/**返回一个邻接矩阵**/
	@Override
	public int[][] getAdjacencyMatrix(){
		int[][] adjacencyMatrix = new int[getSize()][getSize()];

		for(int i = 0; i < neighbors.size(); i++){
			for(int j = 0; j < neighbors.get(i).size(); j++){
				int v = neighbors.get(i).get(j);
				adjacencyMatrix[i][v] = 1;
			}
		}

		return adjacencyMatrix;
	}

	/**打印邻接矩阵**/
	@Override
	public void printAdjacencyMatrix(){
		int[][] adjacencyMatrix = getAdjacencyMatrix();
		for(int i = 0; i < adjacencyMatrix.length; i++){
			for(int j = 0; j < adjacencyMatrix[0].length; j++){
				System.out.print(adjacencyMatrix[i][j] + " ");
			}

			System.out.println();
		}
	}

	/**打印edges(相当于邻接线性表)**/
	@Override
	public void printEdges(){
		for(int u = 0; u < neighbors.size(); u++){
			System.out.print("Vertex " + u + ": ");
			for(int j = 0; j < neighbors.get(u).size(); j++){
				System.out.print("(" + u + ", " + neighbors.get(u).get(j) + ") ");
			}
			System.out.println();
		}
	}

	/**
	 * 得到一个邻接线性表
	 * @return
	 */
	@Override
	public List<List<Integer>> getList(){
		return neighbors;
	}

	/**
	 * 程序完善第一题
	 * 内部类Edge
	 * @author NewBies
	 */
	public static class Edge{
		//起始顶点
		public int u;
		//结束顶点
		public int v;

		/**创建默认起始顶点和终止顶点的构造器**/
		public Edge(int u, int v){
			this.u = u;
			this.v = v;
		}

		@Override
		public boolean equals(Object o){
			if(o instanceof  Edge){
				return this.u == ((Edge)o).u && this.v == ((Edge)o).v;
			}
			return false;
		}
	}

	/**获得从顶点v开始的DFS树**/
	@Override
	public Tree dfs(int v){
		List<Integer> searchOrders = new ArrayList<Integer>();
		int[] parent = new int[vertices.size()];
		for(int i = 0; i < parent.length; i++){
			//初始化parent[i]为-1
			parent[i] = -1;
		}
		//标记访问顶点
		boolean[] isVisited = new boolean[vertices.size()];

		//递归查找
		dfs(v, parent, searchOrders, isVisited);

		//返回一个查找树
		return new Tree(v, parent, searchOrders);
	}

	/**DFS搜索的递归方法**/
	private void dfs(int v, int[] parent, List<Integer> searchOrders, boolean[] isVisited){
		//存储已经被访问的顶点
		searchOrders.add(v);
		//顶点v已经被访问
		isVisited[v] = true;

		for(int i : neighbors.get(v)){
			if(!isVisited[i]){
				//顶点i的父元素是v
				parent[i] = v;
				//递归查找
				dfs(i, parent, searchOrders, isVisited);
			}
		}
	}

	/**获得从顶点v开始的bfs树**/
	@Override
	public Tree bfs(int v){
		List<Integer> searchOrders = new ArrayList<Integer>();
		int[] parent = new int[vertices.size()];

		for(int i = 0; i < parent.length; i++){
			//初始化为-1
			parent[i] = -1;
		}

		//申明一个队列
		LinkedList<Integer> queue = new LinkedList<Integer>();
		//初始化访问标志
		boolean[] isVisited = new boolean[vertices.size()];
		//将V添加到队列尾部
		queue.offer(v);
		//标记已被访问
		isVisited[v] = true;
		int count = 0;
		while(!queue.isEmpty()){
			//删除队列的第一个元素
			int u = queue.poll();
			//将u添加到查找记录表中
			searchOrders.add(u);
			//依次遍历第一个元素的邻居
			for(int w : neighbors.get(u)){
				//如果它的邻居没有被访问
				if(!isVisited[w]){
					//那么就将其添加到队列中
					queue.offer(w);
					parent[w] = u;
					isVisited[w] = true;
					count++;
				}
			}
		}
		Log.d("count",count + "");
		return new Tree(v, parent, searchOrders);
	}

	/**内部类tree**/
	public class Tree{
		private int root;
		private int[] parent;
		private List<Integer> searchOrders;

		public Tree(int root, int[] parent, List<Integer> searchOrders){
			this.root = root;
			this.parent = parent;
			this.searchOrders = searchOrders;
		}

		public Tree(int root, int[] parent){
			this.root = root;
			this.parent = parent;
		}

		/**返回该树的根**/
		public int getRoot(){
			return root;
		}

		/**返回顶点V的父节点**/
		public List<Integer> getSearchOrders(){
			return searchOrders;
		}

		/**返回查找到的顶点数**/
		public int getNumberOfVerticesFound(){
			return searchOrders.size();
		}

		/**返回顶点索引到根节点的路径**/
		public List<V> getPath(int index){
			ArrayList<V> path = new ArrayList<V>();

			do{
				path.add(vertices.get(index));
				index = parent[index];
			}
			while(index != -1);

			return path;
		}

		/**返回顶点索引到根节点的路径**/
		public ArrayList<Edge> findPath(int index){
			ArrayList<Edge> path = new ArrayList<>();

			//index为-1，表示该点没有父节点
			while(index != -1){
				path.add(new Edge(parent[index],(Integer)vertices.get(index)));
				index = parent[index];
			}
			path.remove(path.size() - 1);
			return path;
		}

		public void printPath(int index){
			List<V> path = getPath(index);
			System.out.print("A path from " + vertices.get(root) + " to " + vertices.get(index) + ": ");
			for(int i = path.size() - 1; i >= 0; i--){
				System.out.print(path.get(i) + " ");
			}
		}


		/**打印整颗树**/
		public void printTree(){
			System.out.println("Root is: " + vertices.get(root));
			System.out.print("Edges: ");
			for(int i = 0; i < parent.length; i++){
				if(parent[i] != -1){
					//显示一个Edge
					System.out.print("(" + vertices.get(parent[i]) + ", " + vertices.get(i) + ") ");
				}
			}
			System.out.println();
		}

		public ArrayList<Edge> getTree(int startVertex){
			ArrayList<Edge> edges = new ArrayList<AbstractGraph.Edge>();
			for(int i = startVertex; i < parent.length + startVertex; i++){
				if(parent[i%parent.length] != -1){
					edges.add(new Edge((Integer) vertices.get(parent[i%parent.length]), (Integer)vertices.get(i%parent.length)));
				}
			}
			return edges;
		}
	}
}
