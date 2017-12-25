package com.example.newbies.myapplication.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author NewBies
 * @date 2017/12/14
 */
public class CalculateRatio {
    /**
     *  将4个操作数简化为3个操作数时，将这三个操作数存放在temp1中
     */
    private static double[] temp1 = new double[3];
    /**
     * 将3个操作数简化为2个操作数时，将这两个操作数存放在temp2中
     */
    private static double[] temp2 = new double[2];

    private static double sum;
    private static int[] cardArray = new int[4];
    private static char[] operator = { '+', '-', '*', '/' };
    private static double[] scard = new double[4];
    private static boolean isCorrect = false;

    /**
     * 列出所有不重复的组合
     */
    public static String allResult() {
        int allcount = 0;
        int count = 0;
        int times = 0;
        // 这里只考虑对数据
        // 为了减少运算量，避免重复的排列组合，比如1,2,3,4与1,2,4,3，这样就是重复的，采用了j = i, k = j这种来控制循环
        for (int i = 1; i < 14; i++) {
            for (int j = i; j < 14; j++) {
                for (int k = j; k < 14; k++) {
                    for (int l = k; l < 14; l++) {
                        //判断花色重复的数量
                        times = times(i, j, k, l);
                        allcount += times;
                        // 判断该表达式是否正确
                        if (getExpression(i, j, k, l) != null) {
                            count += times;
                        }
                    }
                }
            }
        }

        double rate = (double) count / allcount;
        System.out.println("所有可能的组合共有：" + allcount);
        System.out.println("结果为24的组合共有： " + count);
        System.out.println("成功的几率是:" + rate);
        return "所有可能的组合共有：" + allcount + "结果为24的组合共有： " + count + "成功的几率是:" + rate;
    }

    /**
     * 方法简述：输入4个数判断其能产生多少个结果等于24的算式，就是选出来的四个数一个会有多少种排列组合
     *
     * @param num1
     * @param num2
     * @param num3
     * @param num4
     * @return
     */
    public static ArrayList<String> getExpression(int num1, int num2, int num3, int num4) {
        //声明一个ArrayList，用于存放所有可能的表达式
        ArrayList<String> expressionList = new ArrayList<String>();
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                // 这些判断是为了防止产生错误的组合，比如传进来的数是1,4,6,8，如果没有判断，就可能导致产生1,1,6,8这种组合
                if (b == a) {
                    continue;
                }

                for (int c = 0; c < 4; c++) {
                    if (c == a || c == b) {
                        continue;
                    }

                    for (int d = 0; d < 4; d++) {

                        if (d == a || d == b || d == c) {
                            continue;
                        }
                        // 让四个数产生了不同的组合，比如1,4,6,8--8,1,6,4等
                        cardArray[a] = num1;
                        cardArray[b] = num2;
                        cardArray[c] = num3;
                        cardArray[d] = num4;

                        for (int m = 0; m < 4; m++) {
                            // 这里转换为double类型是为了方便后面的除法运算
                            scard[m] = (double) cardArray[m] % 13;
                            if (cardArray[m] % 13 == 0) {
                                scard[m] = 13;
                            }
                        }

                        // 进行一次搜索
                        expressionList = search();

                        // 如果搜索出来的是正确的，那么将isCorrect置为false，便于下次使用
                        if (isCorrect) {
                            isCorrect = false;
                            return expressionList;
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * 方法简述：基本计算
     *
     * @param number1
     *            数字1
     * @param number2
     *            数字2
     * @param operator
     *            数字3
     * @return
     */
    private static double calcute(double number1, double number2, char operator) {
        if (operator == '+') {
            return number1 + number2;
        } else if (operator == '-') {
            return number1 - number2;
        } else if (operator == '*') {
            return number1 * number2;
        } else if (operator == '/' && number2 != 0) {
            return number1 / number2;
        } else {
            return -1;
        }
    }

    public static  ArrayList<String> search(int num1, int num2, int num3, int num4){
        scard[0] = num1;
        scard[1] = num2;
        scard[2] = num3;
        scard[3] = num4;
        return search();
    }

    private static ArrayList<String> search() {

        //声明一个ArrayList，用于存放所有可能的表达式
        ArrayList<String> expressionList = new ArrayList<String>();
        // 第一次放置的符号（算术优先级最高）
        for (int i = 0; i < 4; i++) {

            // 第二次放置的符号（算术优先级次高）
            for (int j = 0; j < 4; j++) {

                // 第三次放置的符号（最后一个计算）
                for (int k = 0; k < 4; k++) {

                    // 首先计算的两个相邻数字，共有3种情况，相当于括号的作用，也就是各种优先级顺序组合
                    for (int m = 0; m < 3; m++) {
                        // 如果出现除数为零则表达式出错，结束此次循环
                        if (scard[m + 1] == 0 && operator[i] == '/') {
                            break;
                        }
                        // 从4个操作数中提取出两个数，然后将可能进行优先计算的两个数计算，然后得到值，注意：这里是优先级最高的运算符，也就是第一次放置的符号
                        temp1[m] = calcute(scard[m], scard[m + 1], operator[i]);
                        // 将其余两个没有进行计算的值赋值给temp1数组
                        temp1[(m + 1) % 3] = scard[(m + 2) % 4];
                        temp1[(m + 2) % 3] = scard[(m + 3) % 4];

                        // 先确定首先计算的两个数字，计算完成相当于剩下三个数，按顺序储存在temp1数组中
                        // 三个数字选出先计算的两个相邻数字，两种情况，相当于第二个括号
                        for (int n = 0; n < 2; n++) {
                            if (temp1[n + 1] == 0 && operator[j] == '/') {
                                break;
                            }
                            // 简化运算，将三个操作数简化为两个操作数，注意：这里是优先级最高的运算符，也就是第二次放置的符号
                            temp2[n] = calcute(temp1[n], temp1[n + 1], operator[j]);
                            temp2[(n + 1) % 2] = temp1[(n + 2) % 3];

                            if (temp2[1] == 0 && operator[k] == '/') {
                                break;
                            }

                            // 将两个操作数简化为一个操作数，注意：这里是优先级最高的运算符，也就是第三次放置的符号
                            sum = calcute(temp2[0], temp2[1], operator[k]);

                            // 如果能够24，那么将该算式输出来
                            if (sum == 24) {
                                isCorrect = true;
                                String expression = "";

                                // 根据组合列出算式
                                if (m == 0 && n == 0) {
                                    expression = "((" + (int) scard[0] + operator[i] + (int) scard[1] + ")" + operator[j] + (int) scard[2] + ")" + operator[k] + (int) scard[3] + "=" + (int) sum;
                                } else if (m == 0 && n == 1) {
                                    expression = "(" + (int) scard[0] + operator[i] + (int) scard[1] + ")" + operator[k]  + "(" + (int) scard[2] + operator[j] + (int) scard[3] + ")=" + (int) sum;
                                } else if (m == 1 && n == 0) {
                                    expression = "(" + (int) scard[0] + operator[j] + "(" + (int) scard[1] + operator[i] + (int) scard[2] + "))" + operator[k] + (int) scard[3] + "=" + (int) sum;
                                } else if(m == 1 && n == 1){
                                    expression = ((int)scard[0]) + "" + operator[k] + "((" + (int) scard[1] + operator[i] + (int) scard[2] + ")" + operator[j] + (int) scard[3] + ")=" + (int) sum;
                                } else if (m == 2 && n == 1) {
                                    expression = ((int)scard[0]) + "" + operator[k] + "(" + (int) scard[1] + operator[j] + "(" + (int) scard[2] + operator[i] + (int) scard[3] + "))=" + (int) sum;
                                }

                                System.out.println(expression);
                                if(!expression.equals("")){
                                    expressionList.add(expression);
                                }
                            }
                        }
                    }
                }
            }
        }
        return expressionList;
    }

    /**
     * 利用对花色的排列组合，避免花色重复，大量地减少运算量，提高效率
     * @param i
     * @param j
     * @param k
     * @param l
     * @return
     */
    private static int times(int i,int j,int k,int l){
        //利用set判断有多少种重复
        Set<Integer> set =new HashSet<Integer>();
        set.add(i);
        set.add(j);
        set.add(k);
        set.add(l);
        //当4个数的数字全部一样时，只可能有一种花色的组合，比如红桃6，梅花6，方块6，黑桃6
        if(set.size()==1){
            return 1;
        }
        //当4个数中，有两个数相同，其余的数都不相同时，就有C4取2乘以C4取1乘以C4取1种可能的花色组合，比如红桃6，梅花6，方块7，黑桃8
        else if(set.size()==3){
            return 96;
        }
        //当4个数全部不同时，就有C4取1乘以C4取1乘以C4取1乘以C4取1种（256）情况
        else if(set.size()==4){
            return 256;
        }
        else{
            //当4个数中，两两相同时，同样的，利用排列组合，一共有C4取2乘以C4取2，共36种情况
            if((i==j&&k==l)||(i==k&&j==l)){
                return 36;
            }
            //当4个数中有三个数相同，另外一个数不同时
            else {
                return 16;
            }
        }
    }
}
