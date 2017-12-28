package com.example.newbies.myapplication.util;

import com.example.newbies.myapplication.util.AbstractGraph;

import java.util.ArrayList;
import java.util.List;

public class UnweightedGraph<V> extends AbstractGraph<V> {

	/**通过一个edges和包含一个顶点的数组构建个图**/
	public UnweightedGraph(int[][] edges, V[] vertices) {
		super(edges, vertices);
	}

	public UnweightedGraph(List<Edge> edges, int numberOfVertices) {
		super(edges, numberOfVertices);
	}

	public UnweightedGraph(ArrayList<Edge> edges, int numberOfVertices){
		super(edges, numberOfVertices);
	}

	@Override
	public List<List<UnweightEdge>> getNeighbors() {
		return null;
	}
}
