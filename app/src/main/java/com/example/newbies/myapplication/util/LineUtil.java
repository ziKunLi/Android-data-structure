package com.example.newbies.myapplication.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.newbies.myapplication.view.EdgeView;
import java.util.ArrayList;
import java.util.Collections;
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
    private ArrayList<View> lineForTowView  = new ArrayList<>(2);
    private ArrayList<View> views  = new ArrayList<>();
    private HashMap<View, ArrayList<Line>> vertexLine = new HashMap<>();
    private ArrayList<AbstractGraph.Edge> edges = new ArrayList<>();
    private Graph<Integer> graph;
    private AbstractGraph.Tree tree = null;
    private ViewGroup drawPane;
    private Context context;
    private Paint paint;

    private LineUtil(){
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
        this.clear();
    }

    public void setGraphType(GraphType graphType){
        this.graphType = graphType;
        this.clear();
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
            //以下代码虽被废弃，但价值存在
//            //如果是带权图，那么在移动顶点时，权值也应该改变
//            if(GraphType.WEIGHT_GRAPH == graphType){
//                for(int j = 0; j < edges.size(); j++){
//                    if(edges.get(j).u == view.getId()&&edges.get(j).v == line.getEndPointId()||edges.get(j).v == view.getId()&&edges.get(j).u == line.getEndPointId()){
//                        ((WeightedEdge)edges.get(j)).weight = line.getEdgeView().getLength();
//                    }
//                }
//            }
        }
    }

    /**
     * 发送进行深度优先遍历的消息，进行深度优先遍历
     * @param startVertex
     */
    public void sendDfsMessage(int startVertex){
        if(newGraph()){
            return;
        }
        tree = graph.dfs(startVertex);
        ArrayList<AbstractGraph.Edge> dfs = tree.getTree(startVertex);
        drawTree(dfs);
    }

    /**
     * 发送进行广度优先遍历的消息，进行深度优先遍历
     * @param startVertex
     */
    public void sendBfsMessage(int startVertex){
        if(newGraph()){
            return;
        }
        tree = graph.bfs(startVertex);
        ArrayList<AbstractGraph.Edge> bfs = tree.getTree(startVertex);
        drawTree(bfs);
    }

    /**
     * 发送查找路径的消息
     * @param startVertex
     * @param endVertex
     */
    public void sendPathMessage(int startVertex, int endVertex){
        if(newGraph()){
            return;
        }
        if(graph instanceof WeightedGraph){
            tree = ((WeightedGraph)graph).getShortestPath(startVertex);
        }
        else {
            tree = graph.dfs(startVertex);
        }
        ArrayList<AbstractGraph.Edge> path = tree.findPath(endVertex);
        drawTree(path);
    }

    /**
     * 发送查找最小生成树的消息
     * @param startVertex
     */
    public void sendMinTreeMessage(int startVertex){
        if(newGraph()){
            return;
        }
        if(graph instanceof UnweightedGraph){
            return;
        }
        tree = ((WeightedGraph)graph).getMinimumSpanningTree(startVertex);
        ArrayList<AbstractGraph.Edge> path = tree.getTree(startVertex);
        drawTree(path);
    }

    private void drawTree(ArrayList<AbstractGraph.Edge> tree){
        for(int i = 0; i < tree.size(); i++){
            View startView = views.get(tree.get(i).u);

            ArrayList<Line> lines = vertexLine.get(startView);
            for(int j = 0; j < lines.size(); j++){
                if(lines.get(j).getEndPointId() == tree.get(i).v){
                    Paint paint = new Paint();
                    //设置画笔的颜色
                    paint.setColor(Color.RED);
                    //设置画笔的锯齿效果
                    paint.setAntiAlias(true);
                    //设置画笔的风格（空心或实心）
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(5);
                    //设置画笔文字大小
                    paint.setTextSize(60);
                    //设置文字居中
                    paint.setTextAlign(Paint.Align.CENTER);
                    //判断这个点在划线是终点还是起点，然后判断箭头的坐标
                    if(lines.get(j).getState() == EdgeState.START){
                        lines.get(j).lineView.resetLine(true,paint);
                    }
                    else {
                        lines.get(j).lineView.resetLine(false,paint);
                    }
                    break;
                }
            }
        }
    }

    private boolean newGraph(){
        //判断该图是否连通
        for(int i = 0; i < vertexLine.size(); i++){
            if(vertexLine.get(views.get(i)).size() == 0){
                Toast.makeText(context, "请将图连通！", Toast.LENGTH_SHORT).show();
                return true;
            }
        }

        if(graphType == GraphType.UNWEIGHT_GRAPH){
            graph = new UnweightedGraph<>(edges,views.size());
        }
        else {
            graph = new WeightedGraph<>(edges,views.size());
        }
        Collections.sort(views,new VertexViewComparator());

        return false;
    }

    public void sendUpdataMessage(){
        Paint paint = new Paint();
        //设置画笔的颜色
        paint.setColor(Color.BLACK);
        //设置画笔的锯齿效果
        paint.setAntiAlias(true);
        //设置画笔的风格（空心或实心）
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        //设置画笔文字大小
        paint.setTextSize(60);
        //设置文字居中
        paint.setTextAlign(Paint.Align.CENTER);
        for(int i = 0; i < views.size(); i++){
            ArrayList<Line> lines = vertexLine.get(views.get(i));
            for(int j = 0; j < lines.size(); j++){
                if(lines.get(j).lineView.isChanged()){
                    lines.get(j).lineView.setLinePaint(paint);
                    lines.get(j).lineView.setChanged(false);
                }
            }
        }
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

    public int getVertexSize(){
        return views.size();
    }

    class Line{
        /**
         * 线
         */
        private EdgeView lineView;
        /**
         * 这条线相对于一个点的状态
         */
        private EdgeState state;
        /**
         * 结束点ID
         */
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
