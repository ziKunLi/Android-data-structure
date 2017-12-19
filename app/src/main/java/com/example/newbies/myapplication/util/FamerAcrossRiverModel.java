package com.example.newbies.myapplication.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author NewBies
 */
public class FamerAcrossRiverModel {

	private static List<AbstractGraph.Edge> edges;

	private static UnweightedGraph<Integer> graph;

	/**
	 * 用于暂时存放一个顶点与其他顶点关系的图
	 */
	private static HashMap<Integer, ArrayList<Integer>> tempEdges = new HashMap<Integer, ArrayList<Integer>>();

	/**
	 * 用于判断生成图时该顶点是否被顶点是否被访问
	 */
	private static ArrayList<Integer> isAdded = new ArrayList<Integer>();

	/**
	 * 用于判断遍历图时该顶点是否被顶点是否被访问
	 */
	private static boolean[] isVisited = null;

	/**
	 * 存储各个顶点的邻居
	 */
	private static List<List<Integer>> neighbors = null;

	/**
	 * 存储指定的两个顶点的所有简单路径
	 */
	private static ArrayList<ArrayList<Integer>> allPath = null;
	private static ArrayList<ArrayList<Integer>> state = new ArrayList<>();

	public static List<Integer> getPath(){
		if(graph == null){
			init();
		}
		return graph.dfs(0).getPath(15);
	}

	public static Graph<Integer> getGraph(){
		if(graph == null){
			init();
		}
		return graph;
	}

	private static void init() {
		isAdded.add(0);
		edges = new ArrayList<AbstractGraph.Edge>();
		getAllBit(0);
		ArrayList<Integer> valueList;
		Set<Map.Entry<Integer, ArrayList<Integer>>> entries = tempEdges.entrySet();
		for(Map.Entry<Integer, ArrayList<Integer>> entry :entries){
			valueList = entry.getValue();
			for(int i = 0; i < valueList.size(); i++){
				edges.add(new AbstractGraph.Edge(entry.getKey(), valueList.get(i)));
			}
		}
		graph = new UnweightedGraph<Integer>(edges,edges.size());
	}


	private static void getAllBit(int num){
		//用来存储该数二进制各个位上的数
		int[] bits = new int[4];
		//用来判断农夫在河的哪岸，0就是在南岸，1就是在北岸
		int state = 0;
		//用于进行各种判断
		boolean isRight = true;
		//用于暂存传入的状态
		int sourceNum = num;

		//得到该数二进制各个位上的数
		for(int i = 3; i >= 0; i--){
			bits[3 - i] = (num&(int)Math.pow(2, i)) >> i;
		}

		//农夫从0变为1，也就是农夫从南岸到达北岸
		if((num&8) >> 3 == 0){
			state = 0;
		}
		else{
			state = 1;
		}

		//判断农夫应该带走哪一个物件
		for(int i = 0; i < bits.length; i++){
			//假设将第一个没有带走的物件带走
			if(bits[i] == state){

				bits[i] = Math.abs(bits[i] - 1);
				isRight = true;
				//判断是否应该带走这个物件
				for(int j = 1; j < bits.length - 1; j++){
					if(bits[j] == state&&bits[j + 1] == state){
						isRight = false;
						break;
					}
				}

				if(isRight){
					//如果能够带走，就把它作为一种情况
					//将bits中的数转换为十进制数
					int temp = 0;
					for(int j = 0; j < bits.length; j++){
						temp += bits[j] * (int)Math.pow(2, 3 - j);
					}

					if(!tempEdges.containsKey(sourceNum)){
						ArrayList<Integer> tempList = new ArrayList<Integer>();
						tempList.add(temp);
						tempEdges.put(sourceNum, tempList);
					}
					else{
						ArrayList<Integer> tempList = tempEdges.get(sourceNum);
						tempList.add(temp);
						tempEdges.put(sourceNum, tempList);
					}

					//用于判断是否应该递归
					if(!isAdded.contains(temp)&&sourceNum!=15){
						isAdded.add(temp);
						//递归调用
						getAllBit(temp);
					}

				}
				//i等于0时，是将农夫在过河，所以此时不用将物件状态还原
				if(i != 0){
					//将该物件的状态还原，考虑下一种情况
					bits[i] = state;
				}
			}
		}
	}

	/**
	 * 得到过河的路径
	 * @return
	 */
	public static ArrayList<ArrayList<Integer>> getAllPath(){
		if(allPath == null){
			findPath(0,15);
		}
		return allPath;
	}

	/**
	 * 得到每次过河时的状态变化情况
	 * @return
	 */
	public static ArrayList<ArrayList<Integer>> getState(){
		if(allPath == null){
			findPath(0,15);
		}
		return state;
	}

	/**
	 * 广度优先遍历输出图graph中顶点U到顶点V路径长度为length的所有简单路径
	 * @param u
	 * @param v
	 */
	private static <V> ArrayList<ArrayList<Integer>> findPath(int u, int v){
		if(allPath == null){
			allPath = new ArrayList<ArrayList<Integer>>();
			if(graph == null){
				init();
			}
			isVisited = new boolean[graph.getSize()];
			//获得邻接线性表
			neighbors = graph.getList();
			int[] path = new int[graph.getSize()];
			findPath(u, v, 1, path);
		}
		return allPath;
	}

	private static int findPath(int u, int v, int currentLength, int[] path){
		isVisited[u] = true;
		//记录路径
		path[currentLength - 1] = u;
		//获得顶点u的第一个相邻顶点
		int[] current = new int[neighbors.get(u).size()];
		for(int i = 0; i < neighbors.get(u).size(); i++){
			//依次获取顶点U的相邻结点
			current[i] = neighbors.get(u).get(i);
			//判断是否访问到目的顶点
			if(current[i] == v){
				ArrayList<Integer> tempPath = new ArrayList<>();
				ArrayList<Integer> tempState = new ArrayList<>();
				for(int j = 0; j < currentLength - 1; j++){
					tempState.add(Math.abs(path[j + 1] - path[j]) - 8);
					tempPath.add(path[j]);
				}
				tempPath.add(path[currentLength - 1]);
				tempPath.add(v);
				tempState.add(Math.abs(v - path[currentLength - 1]) - 8);
				allPath.add(tempPath);
				state.add(tempState);
			}
		}
		//对每一个顶点，进行层次遍历，寻找路径
		for(int i = 0; i < neighbors.get(u).size(); i++){
			//判断领接点是不是终点，如果是则就不要让终点去遍历其他点了（路径）例如：0 -> 1 -> 3 -> 4 -> 5 -> 2 -> 5
			//这里的5->2->5就是不对的
			if(current[i] == v){
				continue;
			}
			//判断该相邻结点是否已经被访问
			if(!isVisited[current[i]]){
				findPath(current[i], v, ++currentLength, path);
				currentLength--;
				//恢复状态，便于查找下一条路径时使用
				isVisited[current[i]] = false;
			}
		}
		return currentLength;
	}
}
