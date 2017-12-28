package com.example.newbies.myapplication.util;

import com.example.newbies.myapplication.util.AbstractGraph;
import com.example.newbies.myapplication.util.WeightedEdge;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author NewBies
 */
public class WeightedGraph<V> extends AbstractGraph<V> {

    protected List<PriorityQueue<WeightedEdge>> queues = new ArrayList<PriorityQueue<WeightedEdge>>();

    public WeightedGraph() {
    }

    //根据存在数组中的边和顶点建立一个带权图
    public WeightedGraph(int[][] edges, V[] vertices) {
        super(edges, vertices);
        createQueues(edges, vertices.length);
    }


    /**
     * 为顶点0,1,2和边列表构造一个WeightedGraph
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public WeightedGraph(List<WeightedEdge> edges, List<V> vertices) {
        super((List) edges, vertices);
        createQueues(edges, vertices.size());
    }

    /**
     * 从顶点0,1和边数组构造一个WeightedGraph
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public WeightedGraph(List<WeightedEdge> edges, int numberOfVertices) {
        super((List) edges, numberOfVertices);
        createQueues(edges, numberOfVertices);
    }

    /**
     * 根据边创建优先邻接矩阵
     */
    private void createQueues(int[][] edges, int numberOfVertices) {
        for (int i = 0; i < numberOfVertices; i++) {
            //创建一个队列
            queues.add(new PriorityQueue<WeightedEdge>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int weight = edges[i][2];
            // 向队列中插入边
            queues.get(u).offer(new WeightedEdge(u, v, weight));
        }
    }

    /**
     * 根据边创建优先邻接矩阵
     */
    private void createQueues(List<WeightedEdge> edges, int numberOfVertices) {
        for (int i = 0; i < numberOfVertices; i++) {
            // 创建一个队列
            queues.add(new PriorityQueue<WeightedEdge>());
        }

        for (WeightedEdge edge : edges) {
            // 向队列中插入边
            queues.get(edge.u).offer(edge);
        }
    }

    /**
     * 显示与重量的边
     */
    public void printWeightedEdges() {
        for (int i = 0; i < queues.size(); i++) {
            System.out.print(getVertex(i) + " (" + i + "): ");
            for (WeightedEdge edge : queues.get(i)) {
                System.out.print("(" + edge.u + ", " + edge.v + ", "
                        + edge.weight + ") ");
            }
            System.out.println();
        }
    }

    /**
     * 得到图中的边
     */
    public List<PriorityQueue<WeightedEdge>> getWeightedEdges() {
        return queues;
    }

    /**
     * 清空图
     */
    public void clear() {
        vertices.clear();
        neighbors.clear();
        queues.clear();
    }

    /**
     * 添加顶点
     */
    @Override
    public void addVertex(V vertex) {
        super.addVertex(vertex);
        queues.add(new PriorityQueue<WeightedEdge>());
    }

    /**
     * 添加边
     */
    public void addEdge(int u, int v, int weight) {
        super.addEdge(u, v);
        queues.get(u).add(new WeightedEdge(u, v, weight));
        queues.get(v).add(new WeightedEdge(v, u, weight));
    }

    /**
     *  得到根为 vertex 0 的最小生成树
     */
    public MST getMinimumSpanningTree() {
        return getMinimumSpanningTree(0);
    }

    /**
     * 得到根为指定顶点的最小生成树
     */
    public MST getMinimumSpanningTree(int startingIndex) {
        List<Integer> T = new ArrayList<Integer>();
        //T最初只包含startingIndex
        T.add(startingIndex);

        int numberOfVertices = vertices.size();
        // 顶点符节点
        int[] parent = new int[numberOfVertices];
        // 初始化父节点为-1
        for (int i = 0; i < parent.length; i++) {
            parent[i] = -1;
        }
        // 整棵树的权值相当于距离
        double totalWeight = 0;

        //赋值优先队列，用来保证原始队列不变
        List<PriorityQueue<WeightedEdge>> queues = deepClone(this.queues);

        //是否找到所有顶点
        while (T.size() < numberOfVertices) {
            //寻找与T相邻顶点的最小权值边
            int v = -1;
            double smallestWeight = Double.MAX_VALUE;
            for (int u : T) {
                while (!queues.get(u).isEmpty()
                        && T.contains(queues.get(u).peek().v)) {
                    // 如果相邻，则从队列中移除边缘[u]
                    // 你的顶点已经在T了
                    queues.get(u).remove();
                }

                if (queues.get(u).isEmpty()) {
                    //考虑T中的下一个顶点
                    continue;
                }

                //当前在与u相邻的边上的最小权重
                WeightedEdge edge = queues.get(u).peek();
                if (edge.weight < smallestWeight) {
                    v = edge.v;
                    smallestWeight = edge.weight;
                    //如果将v添加到树中，则将成为其父项
                    parent[v] = u;
                }
            } // End of for

            if (v != -1) {
                //添加一个新的顶点到树中
                T.add(v);
            } else {
                //树未连接，找到部分MST
                break;
            }

            totalWeight += smallestWeight;
        }

        return new MST(startingIndex, parent, T, totalWeight);
    }

    /**
     * Clone an array of queues
     */
    private List<PriorityQueue<WeightedEdge>> deepClone(
            List<PriorityQueue<WeightedEdge>> queues) {
        List<PriorityQueue<WeightedEdge>> copiedQueues = new ArrayList<PriorityQueue<WeightedEdge>>();

        for (int i = 0; i < queues.size(); i++) {
            copiedQueues.add(new PriorityQueue<WeightedEdge>());
            for (WeightedEdge e : queues.get(i)) {
                copiedQueues.get(i).add(e);
            }
        }

        return copiedQueues;
    }

    /**
     *  最小生成树
     */
    public class MST extends Tree {
        // Total weight of all edges in the tree
        private double totalWeight;

        public MST(int root, int[] parent, List<Integer> searchOrder,
                   double totalWeight) {
            super(root, parent, searchOrder);
            this.totalWeight = totalWeight;
        }

        public double getTotalWeight() {
            return totalWeight;
        }
    }

    /**
     * Find single source shortest paths
     */
    public ShortestPathTree getShortestPath(int sourceIndex) {
        // T stores the vertices whose path found so far
        List<Integer> T = new ArrayList<Integer>();
        // T initially contains the sourceVertex;
        T.add(sourceIndex);

        // vertices is defined in AbstractGraph
        int numberOfVertices = vertices.size();

        // parent[v] stores the previous vertex of v in the path
        int[] parent = new int[numberOfVertices];
        // The parent of source is set to -1
        parent[sourceIndex] = -1;

        // costs[v] stores the cost of the path from v to the source
        double[] costs = new double[numberOfVertices];
        for (int i = 0; i < costs.length; i++) {
            // Initial cost set to infinity
            costs[i] = Double.MAX_VALUE;
        }
        // Cost of source is 0
        costs[sourceIndex] = 0;

        // Get a copy of queues
        List<PriorityQueue<WeightedEdge>> queues = deepClone(this.queues);

        // Expand T
        while (T.size() < numberOfVertices) {
            // Vertex to be determined
            int v = -1;
            // Set to infinity
            double smallestCost = Double.MAX_VALUE;
            for (int u : T) {
                while (!queues.get(u).isEmpty()
                        && T.contains(queues.get(u).peek().v)) {
                    // Remove the vertex in queue for u
                    queues.get(u).remove();
                }

                if (queues.get(u).isEmpty()) {
                    // All vertices adjacent to u are in T
                    continue;
                }

                WeightedEdge e = queues.get(u).peek();
                if (costs[u] + e.weight < smallestCost) {
                    v = e.v;
                    smallestCost = costs[u] + e.weight;
                    // If v is added to the tree, u will be its parent
                    parent[v] = u;
                }
            }
            // End of for

            // Add a new vertex to T
            T.add(v);
            costs[v] = smallestCost;
        } // End of while

        // Create a ShortestPathTree
        return new ShortestPathTree(sourceIndex, parent, T, costs);
    }

    /**
     * ShortestPathTree is an inner class in WeightedGraph
     */
    public class ShortestPathTree extends Tree {
        //成本[v]是从v到源的成本
        private double[] costs;

        /**
         * Construct a path
         */
        public ShortestPathTree(int source, int[] parent,
                                List<Integer> searchOrder, double[] costs) {
            super(source, parent, searchOrder);
            this.costs = costs;
        }

        /**
         * Return the cost for a path from the root to vertex v
         */
        public double getCost(int v) {
            return costs[v];
        }

        /**
         * Print paths from all vertices to the source
         */
        public void printAllPaths() {
            System.out.println("All shortest paths from "
                    + vertices.get(getRoot()) + " are:");
            for (int i = 0; i < costs.length; i++) {
                // 打印从我到源的路径
                printPath(i);
                System.out.println("(cost: " + costs[i] + ")");
            }
        }
    }

    @Override
    public int[][] getAdjacencyMatrix() {
        return null;
    }

    @Override
    public void printAdjacencyMatrix() {
    }

    @Override
    public List<List<UnweightEdge>> getNeighbors() {
        return null;
    }
}
