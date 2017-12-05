package com.example.newbies.myapplication.util;

import java.util.List;


public interface Graph<V> {
	/**返回该图的大小**/
	public int getSize();

	/**返回该图中的顶点**/
	public List<V> getVertices();

	/**返回指定下标处的顶点**/
	public V getVertex(int index);

	/**返回指定顶点的下标**/
	public int getIndex(V v);

	/**返回特定角标处的相邻顶点**/
	public List<Integer> getNeighbors(int index);

	/**返回指定顶点的度数**/
	public int getDegree(int v);

	/**返回一个邻接矩阵**/
	public int[][] getAdjacencyMatrix();

	/**打印邻接矩阵**/
	public void printAdjacencyMatrix();

	/**打印Edges对象**/
	public void printEdges();

	/**深度优先搜索该树**/
	public AbstractGraph<V>.Tree dfs(int v);

	/**广度优先搜索该树**/
	public AbstractGraph<V>.Tree bfs(int v);

	public List<List<Integer>> getList();

	public List<List<AbstractGraph.Edge>> getNeighbors();
}
