package com.example.newbies.myapplication.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：多枚硬币反面问题模型
 * @author NewBies
 * @date 2017/12/5
 */
public class CoinsModel {

    /**
     * 一共有多少枚硬币
     */
    private int count;
    /**
     * 每一列一行都有count枚硬币
     */
    private int row;
    /**
     * 一共有n种情况
     */
    private int n;
    /**
     * 定义一颗查找树
     */
    private AbstractGraph<Integer>.Tree tree;
    /**
     * 正面
     */
    private final char POSITIVE = 'H';
    /**
     * 反面
     */
    private final char OPPOSITE = 'T';
    private GameMode game_mode = GameMode.STRIGHT;
    public CoinsModel(int coins, GameMode game_mode){
        count = coins;
        row = (int) Math.sqrt(coins);
        //计算coins枚硬币一共有多少中情况
        n = (int) Math.pow(2,coins);
        this.game_mode = game_mode;
        //创建一个Edges
        List<AbstractGraph.Edge> edges = getEdges();

        //由所有的邻接数组构成一个无权图
        UnweightedGraph<Integer> graph = new UnweightedGraph<Integer>(edges, n);

        //广度优先遍历该图，并得到利用双亲存储建立的查找树
        tree = graph.bfs(n - 1);
    }

    /**
     * 得到由硬币的各种情况连接起来构成的一张图
     * @return
     */
    public List<AbstractGraph.Edge> getEdges(){
        List<AbstractGraph.Edge> edges = new ArrayList<>();
        //一共有多少个结点，就循环多少次，判断每一个结点有哪些相邻结点
        for(int i = 0 ; i < n; i++){
            //判断该结点一共有几个相邻结点
            for(int j = 0; j < count; j++){
                //得到顶点u的结点
                ArrayList<Character> node = getNode(i);
                //这里可以理解为不翻动已经翻过来的硬币，避免重复翻动
                if(node.get(j) == POSITIVE){
                    int v = getFlippedNode(node, j);
                    edges.add(new AbstractGraph.Edge(v, i));
                }
            }
        }
        return edges;
    }

    /**
     * 翻转所有相邻硬币，并返回翻转后的索引值
     * @param node
     * @param position
     * @return
     */
    public int getFlippedNode(ArrayList<Character> node, int position){
        int row = position / this.row;
        int column = position % this.row;

        flipACell(node, row, column);
        //根据不同的游戏模式，进行不同的翻转
        if(game_mode == GameMode.STRIGHT){
            flipACell(node, row - 1, column);
            flipACell(node, row + 1, column);
            flipACell(node, row, column - 1);
            flipACell(node, row, column + 1);
        }
        else {
            flipACell(node, row - 1, column - 1);
            flipACell(node, row - 1, column + 1);
            flipACell(node, row + 1, column - 1);
            flipACell(node, row + 1, column + 1);
        }

        return getIndex(node);
    }

    /**
     * 翻转单枚硬币
     * @param node
     * @param row
     * @param column
     */
    public void flipACell(ArrayList<Character> node, int row, int column){
        if(row >= 0 && row <= this.row - 1  && column >= 0 && column <= this.row - 1 ){

            if(node.get(row * this.row + column) == POSITIVE){
                //将H变为T
                node.set(row * this.row + column,'T');
            }
            else{
                //将T变为H
                node.set(row * this.row + column,'H');
            }
        }
    }

    /**
     * 得到指定情况处的索引
     * @param node
     * @return
     */
    public int getIndex(ArrayList<Character> node){
        int result = 0;

        for(int i = 0; i < count; i++){
            if(node.get(i) == 'T'){
                result = result * 2 + 1;
            }
            else {
                result = result * 2 + 0;
            }
        }

        return result;
    }

    /**
     * 得到指定结点的具体情况（将int类型的索引转换为char[]类型的描述）
     * @param index
     * @return
     */
    public ArrayList<Character> getNode(int index){
        ArrayList<Character> result = new ArrayList<Character>(count);

        for(int i = 0 ; i < count; i++){
            result.add(' ');
        }
        //解析结点，将整形结点解析成char数组
        for(int i = 0; i < count; i++){
            int digit = index % 2;
            if(digit == 0){
                result.set(count - i - 1,'H');
            }
            else {
                result.set(count - i - 1,'T');
            }
            index = index / 2;
        }

        return result;
    }

    /**
     * 得到指定结点的最短路径集合
     * @param nodeIndex
     * @return
     */
    public List<Integer> getShotestPath(int nodeIndex){
        return tree.getPath(nodeIndex);
    }

    public GameMode getGame_mode() {
        return game_mode;
    }

    public void setGame_mode(GameMode game_mode) {
        this.game_mode = game_mode;
    }
}
