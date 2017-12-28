package com.example.newbies.myapplication.util;

/**
 *
 * @author NewBies
 * @date 2017/12/27
 */

public class UnweightEdge extends AbstractGraph.Edge{
    //起始顶点
    public int u;
    //结束顶点
    public int v;

    /**创建默认起始顶点和终止顶点的构造器**/
    public UnweightEdge(int u, int v){
        super(u,v);
        this.u = u;
        this.v = v;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof  UnweightEdge){
            return this.u == ((UnweightEdge)o).u && this.v == ((UnweightEdge)o).v;
        }
        return false;
    }
}
