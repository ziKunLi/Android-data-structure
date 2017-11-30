package com.example.newbies.myapplication.util;

/**
 *
 * @author NewBies
 * @date 2017/11/29
 */
public class HuffmanTree implements Comparable<HuffmanTree>{
    /**
     * 该树的根
     */
    public Node root;

    /**
     * 通过两棵子树，创建出一个新的树
     */
    public HuffmanTree(HuffmanTree t1, HuffmanTree t2) {
        root = new Node();
        //将较小值置于左边
        root.left = t1.root;
        //将较大值置于右边
        root.right = t2.root;
        //将左右子树的权重相加得到新的权重
        root.weight = t1.root.weight + t2.root.weight;
    }

    /**
     * 创建一个带权重和数据的树
     */
    public HuffmanTree(int weight, char element) {
        root = new Node(weight, element);
    }

    /**
     * 比较两棵树的权重
     */
    public int compareTo(HuffmanTree o) {
        if (root.weight < o.root.weight){
            return 1;
        }

        else if (root.weight == o.root.weight){
            return 0;
        }
        else{
            return -1;
        }
    }

    /**
     * 内部类，储存哈夫曼树中的节点
     */
    public class Node {
        /**
         * 储存叶子结点的字符
         */
        public char element;
        /**
         * 结点权重
         */
        public int weight;
        /**
         * 左子树
         */
        public Node left;
        /**
         * 右子树
         */
        public Node right;
        /**
         * 哈夫曼编码
         */
        public String code = "";

        public Node() {
        }

        /**
         * 创建一个带权重和数据的结点
         */
        public Node(int weight, char element) {
            this.weight = weight;
            this.element = element;
        }
    }
}
