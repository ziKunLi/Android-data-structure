package com.example.newbies.myapplication.util;


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

    /**
     * 根据存在数组中的边和顶点建立一个带权图
     */
    public WeightedGraph(int[][] edges, V[] vertices) {
        super(edges, vertices);
        createQueues(edges, vertices.length);
    }



    /**
     * 为顶点0,1,2和边列表构造一个WeightedGraph
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public WeightedGraph(List<Edge> edges, List<V> vertices) {
        super((List) edges, vertices);
        createQueues(edges, vertices.size());
    }

    /**
     * 从顶点0,1和边数组构造一个WeightedGraph
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public WeightedGraph(List<Edge> edges, int numberOfVertices) {
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
    private void createQueues(List<Edge> edges, int numberOfVertices) {
        for (int i = 0; i < numberOfVertices; i++) {
            // 创建一个队列
            queues.add(new PriorityQueue<WeightedEdge>());
        }

        for (Edge edge : edges) {
            // 向队列中插入边
            if(edge instanceof WeightedEdge){
                queues.get(edge.u).offer((WeightedEdge) edge);
            }
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
        List<Integer> vertexs = new ArrayList<>();
        //初始化vertexs
        vertexs.add(startingIndex);

        int numberOfVertices = vertices.size();
        int[] parent = new int[numberOfVertices];
        //初始化parent数组
        for (int i = 0; i < parent.length; i++) {
            parent[i] = -1;
        }
        // 整棵树的权值相当于距离
        double totalWeight = 0;

        //深度克隆，保证原始队列不变
        List<PriorityQueue<WeightedEdge>> queues = deepClone(this.queues);

        //是否找到所有顶点
        while (vertexs.size() < numberOfVertices) {
            //寻找与vertexs相邻顶点的最小权值边
            int v = -1;
            double smallestWeight = Double.MAX_VALUE;

            for (int u : vertexs) {
                while (!queues.get(u).isEmpty()&& vertexs.contains(queues.get(u).peek().v)) {
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
            } //结束循环

            if (v != -1) {
                //添加一个新的顶点到树中
                vertexs.add(v);
            } else {
                //树未连接，找到部分MST
                break;
            }

            totalWeight += smallestWeight;
        }

        return new MST(startingIndex, parent, vertexs, totalWeight);
    }

    /**
     * 深度克隆队列
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
        /**
         * 最小生成树的权重总和
         */
        private double totalWeight;

        public MST(int root, int[] parent, List<Integer> searchOrder, double totalWeight) {
            super(root, parent, searchOrder);
            this.totalWeight = totalWeight;
        }

        public double getTotalWeight() {
            return totalWeight;
        }
    }

    /**
     * 查找最短路径
     */
    public ShortestPathTree getShortestPath(int sourceIndex) {
        // 用于存储顶点
        List<Integer> vertexs = new ArrayList<Integer>();
        //
        vertexs.add(sourceIndex);

        //顶点数量
        int numberOfVertices = vertices.size();
        //用于存储各个顶点的父节点
        int[] parent = new int[numberOfVertices];
        //将起点处的父节点设为-1
        parent[sourceIndex] = -1;
        //用于存储从某一顶点到起点的权值
        double[] weights = new double[numberOfVertices];
        for (int i = 0; i < weights.length; i++) {
            //设为不可达
            weights[i] = Double.MAX_VALUE;
        }
        //起点的权值为0
        weights[sourceIndex] = 0;

        // 获得一个深度克隆的优先队列
        List<PriorityQueue<WeightedEdge>> queues = deepClone(this.queues);

        while (vertexs.size() < numberOfVertices) {
            //顶点待定
            int v = -1;
            //将新加入的点设为不可达
            double smallestWeight = Double.MAX_VALUE;
            //循环遍历已经可以到达的点，寻找可以继续到达的点的最短路径
            for (int u : vertexs) {
                //将已经访问了的最短的边从队列中删除
                while (!queues.get(u).isEmpty() && vertexs.contains(queues.get(u).peek().v)) {
                    //从队列中删除顶点u
                    queues.get(u).remove();
                }

                if (queues.get(u).isEmpty()) {
                    //所有以u为起点的顶点都存在于vertexs中
                    continue;
                }

                //取出第U个第一条边，也就是最短边
                WeightedEdge edge = queues.get(u).peek();
                //判断该边是不是最小边
                if (weights[u] + edge.weight < smallestWeight) {
                    v = edge.v;
                    //对最小权值进行重新赋值
                    smallestWeight = weights[u] + edge.weight;
                    // 如果V添加到了树中，那么u就是他的parent
                    parent[v] = u;
                }
            }
            // 循环结束

            // 将一个新的顶点添加到vertex中
            vertexs.add(v);
            weights[v] = smallestWeight;
        } //结束循环

        // 创建一棵树
        return new ShortestPathTree(sourceIndex, parent, vertexs, weights);
    }

    /**
     * 最小生成树
     */
    public class ShortestPathTree extends Tree {
        /**
         * 成本[v]是从v到源的成本
         */
        private double[] weights;

        public ShortestPathTree(int source, int[] parent, List<Integer> searchOrder, double[] weights) {
            super(source, parent, searchOrder);
            this.weights = weights;
        }

        public double getCost(int v) {
            return weights[v];
        }

        /**
         * 打印从顶点开始的所有最短路径
         */
        public void printAllPaths() {
            System.out.println("从 " + vertices.get(getRoot()) + "中的所有最短路径有：");
            for (int i = 0; i < weights.length; i++) {
                printPath(i);
                System.out.println("(cost: " + weights[i] + ")");
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
