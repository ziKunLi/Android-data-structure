package com.example.newbies.myapplication.util;

/**
 *
 * @author NewBies
 * @date 2017/12/27
 */

public class WeightedEdge extends AbstractGraph.Edge implements Comparable<WeightedEdge>{
    /**
     * 边Edge的权重
     */
    public int weight;

    /** 加权边的默认构造方法*/
    public WeightedEdge(int u, int v, int weight) {
        //  调用默认的构造方法
        super(u, v);
        this.weight = weight;
    }

    //  比较两条边的权重
    @Override
    public int compareTo(WeightedEdge edge) {
        if(weight > edge.weight){
            return 1;
        }else if(weight == edge.weight){
            return 0;
        }else {
            return -1;
        }
    }
}
