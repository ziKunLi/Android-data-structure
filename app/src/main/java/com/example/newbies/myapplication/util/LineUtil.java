package com.example.newbies.myapplication.util;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.newbies.myapplication.view.EdgeView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author NewBies
 * @date 2017/12/27
 */
public class LineUtil{

    private GraphType graphType;
    /**
     * 用于暂存用于连接的两个顶点
     */
    private ArrayList<View> lineForTowView;
    private ArrayList<View> views;
    private HashMap<View, ArrayList<Line>> vertexLine;
    private ArrayList<AbstractGraph.Edge> edges;
    private Graph<Integer> graph;
//    private ArrayList<AbstractGraph.Edge> unWeightEdges;
//    private ArrayList<WeightedEdge> weightedEdges;
//    private UnweightedGraph<Integer> unWeightedGraph = null;
//    private WeightedGraph weightedGraph = null;
    private AbstractGraph.Tree tree = null;
    private ViewGroup drawPane;
    private Context context;
    private Paint paint;

    private LineUtil(){
        lineForTowView = new ArrayList<>(2);
        views = new ArrayList<>();
        vertexLine = new HashMap<>();
//        unWeightEdges = new ArrayList<>();
//        weightedEdges = new ArrayList<>();
        edges = new ArrayList<>();
        graphType = GraphType.UNWEIGHT_GRAPH;
    }

    private static final LineUtil single = new LineUtil();

    /**
     * 饿汉式单例模式
     * @return
     */
    public static LineUtil getInstance(){
        return single;
    }

    public void setDrawPane(Context context, ViewGroup drawPane, GraphType graphType, Paint paint){
        this.context = context;
        this.drawPane = drawPane;
        this.graphType = graphType;
        this.paint = paint;
    }

    public void setGraphType(GraphType graphType){
        this.graphType = graphType;
    }

    /**
     * 发送开始绘制线条的消息
     * @param view
     */
    public void sendDrawLineStartMessage(final View view){
        if(!lineForTowView.contains(view)){
            lineForTowView.add(view);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(context, view.getId() + "", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
        if(lineForTowView.size() == 2){
            View startVertex  = lineForTowView.get(0);
            View endVertex = lineForTowView.get(1);
            final EdgeView edgeView = new EdgeView(context,startVertex.getX() + 50,startVertex.getY() + 50,endVertex.getX() + 50,endVertex.getY() + 50,"",paint);
            //判断当前是有权图还是无权图
            if(graphType == GraphType.UNWEIGHT_GRAPH){
                //添加边
                AbstractGraph.Edge startEdge = new AbstractGraph.Edge(startVertex.getId(),endVertex.getId());
                AbstractGraph.Edge endEdge = new AbstractGraph.Edge(endVertex.getId(),startVertex.getId());
                //判断边是否已经存在，如果已经存在，那么就不用再新建边了
                if(!edges.contains(startEdge)){

                    edges.add(startEdge);
                    edges.add(endEdge);

                    drawPane.addView(edgeView);

                    vertexLine.get(startVertex).add(new Line(edgeView,EdgeState.START,endVertex.getId()));
                    vertexLine.get(endVertex).add((new Line(edgeView,EdgeState.END,startVertex.getId())));
                }
            }
            else{
                int weight = (int) Math.sqrt(Math.pow(Math.abs(startVertex.getX() - endVertex.getX()),2) + Math.pow(Math.abs(startVertex.getY() - endVertex.getY()),2));
                WeightedEdge startEdge = new WeightedEdge(startVertex.getId(),endVertex.getId(),weight);
                WeightedEdge endEdge = new WeightedEdge(endVertex.getId(),startVertex.getId(),weight);
                //判断边是否已经存在，如果已经存在，那么就不用再新建边了
                if(!edges.contains(startEdge)){

                    edges.add(startEdge);
                    edges.add(endEdge);

                    drawPane.addView(edgeView);

                    vertexLine.get(startVertex).add(new Line(edgeView,EdgeState.START,endVertex.getId()));
                    vertexLine.get(endVertex).add((new Line(edgeView,EdgeState.END,startVertex.getId())));
                }

            }


        }
        if(!vertexLine.containsKey(view)){
            ArrayList<Line> lineViews = new ArrayList<>();
            vertexLine.put(view, lineViews);
            views.add(view);
        }
    }

    /**
     * 发送结束绘制线条的消息
     * @param view
     */
    public void sendDrawLineEndMessage(View view){
        if(lineForTowView.contains(view)){
            lineForTowView.remove(view);
        }
    }

    /**
     * 拖动顶点进行重绘
     * @param view
     */
    public void sendUpdataLineMessage(View view){
        ArrayList<Line> lines = vertexLine.get(view);
        for(int i = 0; i < lines.size(); i++){
            Line line = lines.get(i);
            if(line.getState() == EdgeState.START){
                line.getEdgeView().resetStartPoint(view.getX() + 50,view.getY() + 50);
            }
            else {
                line.getEdgeView().resetEndPoint(view.getX() + 50,view.getY() + 50);
            }
        }
    }

    /**
     * 发送进行深度优先遍历的消息，进行深度优先遍历
     * @param startVertex
     */
    public void sendDfsMessage(int startVertex){
        if(graphType == GraphType.UNWEIGHT_GRAPH){
            graph = new UnweightedGraph<Integer>(edges,edges.size());
        }
        else {
            graph = new WeightedGraph<Integer>(edges,edges.size());
        }
        tree = graph.dfs(startVertex);
        ArrayList<Integer> dfs = (ArrayList<Integer>) tree.getSearchOrders();
        System.out.println(tree.getSearchOrders());
    }

    /**
     * 发送查找路径的消息
     * @param startVertex
     * @param endVertex
     */
    public void sendPathMessage(int startVertex, int endVertex){
        graph = new UnweightedGraph<Integer>(edges,edges.size());
        tree = graph.dfs(endVertex);
        ArrayList<Integer> path = (ArrayList<Integer>) tree.getPath(startVertex);
    }

    /**
     * 清除相关数据，因为这个是单例模式的原因，如果在使用后想要重新使用，那么就得把里面已经存在了的数据删除
     */
    public void clear(){
        lineForTowView.clear();
        views.clear();
        vertexLine.clear();
        edges.clear();
        graph = null;
    }

    class Line{
        private EdgeView lineView;
        private EdgeState state;
        private int endPointId;

        public Line(EdgeView lineView, EdgeState state, int endPointId){
            this.lineView = lineView;
            this.state = state;
            this.endPointId = endPointId;
        }

        public EdgeView getEdgeView() {
            return lineView;
        }

        public EdgeState getState() {
            return state;
        }

        public int getEndPointId() {
            return endPointId;
        }
    }

    /**
     * 判断进行移动的点是结束点还是开始点
     */
    enum EdgeState{
        START,
        END
    }

    /**
     * 判断图的类型，是加权图还是无权图
     */
    public enum GraphType{
        UNWEIGHT_GRAPH,
        WEIGHT_GRAPH
    }

}
