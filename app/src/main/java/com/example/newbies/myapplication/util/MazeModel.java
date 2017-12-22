package com.example.newbies.myapplication.util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

/**
 * @author NewBies
 */
public class MazeModel implements Serializable {
	/**
	 * 存储迷宫的二维数组
	 */
	private int[][] maze = null;
	/**
	 * 没有四周墙的迷宫
	 */
	private  int[][] subMaze = null;
	/**
	 * 判断迷宫中的某个点是否被访问
	 */
	private boolean[] isVisited = null;
	/**
	 * 路劲
	 */
	private ArrayList<Integer> path = null;
	/**
	 * 迷宫大小
	 */
	private int row = 8;
	private int column = 8;

	public MazeModel(){
		initMaze(8, 8);
	}

	public MazeModel(int row, int column){
		initMaze(row, column);
		this.row = row;
		this.column = column;
	}

	/**
	 * 初始化迷宫
	 * @param row
	 * @param column
	 */
	private void initMaze(int row, int column){
		maze = new int[row + 2][column + 2];
		subMaze = new int[row][column];
		//将迷宫的墙壁设为1
		for(int i = 0; i < column + 2; i++){
			maze[0][i] = 1;
			maze[row + 1][i] = 1;
		}
		for(int i = 1; i < row + 1; i++){
			maze[i][0] = 1;
			maze[i][column + 1] = 1;
		}
		//通过随机数，随机产生迷宫
		int randomNum = 0;
		for(int i = 1; i < row + 1; i++){
			for(int j = 1; j < column + 1; j++){
				randomNum = (int)(Math.random() * 3);
				maze[i][j] = (randomNum == 0) ? 1:0;
				subMaze[i - 1][j - 1] = maze[i][j];
			}
		}
		//设置终点和起点为0
		maze[row][column] = 0;
		maze[1][1] = 0;
	}

	/**
	 * 查找路径
	 * @return
	 */
	public ArrayList<Integer> findWay(){
		if(path == null){
			dfsByStack(row, column);
		}
		return path;
	}

	/**
	 * 深度优先算法查找路径，用栈实现
	 */
	private void dfsByStack(int row, int column){
		//得到邻接线性表
		isVisited = new boolean[(row + 2) * (column + 2)];
		path = new ArrayList<Integer>();
		//初始化栈
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(column + 3);
		while(!stack.isEmpty()){
			//取出栈顶元素
			int temp = stack.pop();
			while(isVisited[temp]){
				//如果栈中所有的元素都被访问了，则说明遍历完毕，结束方法
				if(stack.isEmpty()){
					return;
				}
				//删除已经被访问了的栈顶元素
				temp = stack.pop();
			}
			//添加到路径
			path.add((temp/(column + 2) - 1)*column + (temp%(column + 2) - 1));
			//判断是否到达终点
			if(temp == (column + 2)*(row + 1) - 2){
				return;
			}
			//标记刚才输出的元素已经被访问
			isVisited[temp] = true;
			//将与刚才遍历出的站点相关的未遍历的点入栈
			//将与该点相邻的上面那个点入栈（这个入栈的顺序很重要，能影响迷宫查找路径）
			if(!isVisited[temp - column - 2]&&maze[(temp - column - 2)/(column + 2)][(temp - column - 2)%(column + 2)] != 1){
				stack.push(temp - column - 2);
			}
			//将与该点相邻的上前面那个点入栈
			if(!isVisited[temp - 1]&&maze[(temp - 1)/(column + 2)][(temp - 1)%(column + 2)] != 1){
				stack.push(temp - 1);
			}
			//将与该点相邻的下面那个点入栈
			if(!isVisited[temp + (column + 2)]&&maze[(temp + (column + 2))/(column + 2)][(temp + (column + 2))%(column + 2)] != 1){
				stack.push(temp + (column + 2));
			}
			//将与该点相邻的后面那个点入栈
			if(!isVisited[temp + 1]&&maze[(temp + 1)/(column + 2)][(temp + 1)%(column + 2)] != 1){
				stack.push(temp + 1);
			}
		}
	}
	/**
	 * 得到构成迷宫的二维数组
	 * @return
	 */
	public int[][] getMaze(){
		return subMaze;
	}
}

