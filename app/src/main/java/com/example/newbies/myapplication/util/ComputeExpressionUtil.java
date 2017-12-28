package com.example.newbies.myapplication.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 *
 * @author NewBies
 * @date 2017/12/26
 */
public class ComputeExpressionUtil {
    private static Stack<String> num = new Stack<>();

    /**
     * 通过前缀表达式计算值
     * @param inFix
     * @return
     */
    public static int computeInFix(String inFix){
        return (int)computePostFix(infixToPostFix(inFix));
    }

    /**
     * 计算后缀表达式
     * @param postFix
     * @return
     */
    public static double computePostFix(ArrayList<String> postFix){
        for(int i = 0; i < postFix.size(); i++){
            String tempNum = "";
            //如果是数字，则直接压入到num栈中
            try{
                Integer.parseInt(postFix.get(i));
                num.push(postFix.get(i));
            }catch(Exception e){
                compute(postFix.get(i).charAt(0));
            }
        }
        return Double.parseDouble(num.pop());
    }
    /**
     * 计算后缀表达式
     * @param ch
     */
    private static void compute(char ch){
        double[] opNum = new double[2];
        double result = 0;
        if(ch == '+'||ch == '-'||ch == '*'||ch == '/'){
            opNum[1] = Double.parseDouble(num.pop());
            opNum[0] = Double.parseDouble(num.pop());
        }
        switch (ch) {
            case '+':
                result = opNum[0] + opNum[1];
                break;
            case '-':
                result = opNum[0] - opNum[1];
                break;
            case '*':
                result = opNum[0] * opNum[1];
                break;
            case '/':
                result = opNum[0] / opNum[1];
                break;
            default:
                break;
        }

        if(ch == '+'||ch == '-'||ch == '*'||ch == '/'){
            num.push((result + ""));
        }
    }

    public static ArrayList<String> infixToPostFix(String expression){
        //用于储存操作符的栈
        Stack<Character> op = new Stack<>();
        //用于储存操作数的栈
        Stack<String> num = new Stack<>();
        char[] infix = expression.toCharArray();
        //从左往右扫描
        for(int i = 0; i < infix.length; i++){
            String tempNum = "";
            boolean isBreak = false;
            //如果是数字，则直接压入到num栈中
            while(Character.isDigit(infix[i])){
                tempNum += infix[i];
                if(i >= infix.length - 1){
                    isBreak = true;
                    break;
                }
                i++;
            }
            if(isBreak){
                if(!tempNum.equals("")){
                    num.push(tempNum);
                    i--;
                }
                break;
            }
            if(!tempNum.equals("")){
                num.push(tempNum);
                i--;
            }
            else{
                //随意赋一个不特殊的值
                char temp = ' ';
                //判断是否为空，把栈顶元素拿出来，作后续的判断
                if(!op.isEmpty()){
                    temp = op.peek();
                }
                switch(infix[i]){
                    case '(':op.push(infix[i]);break;
                    case '+':
                    case '-':
                        //如果与栈顶元素同级或小于栈顶运算符，那么将顶部操作符加入到操作数栈
                        while(temp == '+'||temp == '-'||temp == '/'||temp == '*'){
                            //将栈顶操作符加入到操作数栈
                            num.push(temp + "");
                            //删除该操作符
                            op.pop();
                            //判空
                            if(op.isEmpty()){
                                break;
                            }
                            //用temp就是为了下一个操作符，用于下一次判断
                            temp = op.peek();
                        }
                        //判断完成，将当前操作符压入操作符栈
                        op.push(infix[i]);
                        break;
                    case '*':
                    case '/':
                        //如果与栈顶操作符同级，那么将栈顶运算符添加到操作数栈
                        while(temp == '/'||temp == '*'){
                            //将栈顶操作符加入到操作数栈
                            num.push(temp + "");
                            //删除该操作符
                            op.pop();
                            //判空
                            if(op.isEmpty()){
                                break;
                            }
                            //用temp就是为了下一个操作符，用于下一次判断
                            temp = op.peek();
                        }
                        //判断完成，将当前操作符压入操作符栈
                        op.push(infix[i]);
                        break;
                    case ')':
                        //删除栈顶元素，判断是否为左括号，如果不是，那么就是操作符，这把该操作符加入到操作数栈中，否则退出循环
                        temp = op.pop();
                        while(temp != '('&&!op.isEmpty()){
                            num.push(temp + "");
                            temp = op.pop();
                        }
                        break;
                    default:break;
                }
            }
        }


        ArrayList<String> result = new ArrayList<>();
        int opSize = op.size();
        //将操作符栈中的操作符加入到操作数栈中
        for(int i = 0; i < opSize; i++){
            num.push(op.pop() + "");
        }

        int numSize = num.size();
        //逆序
        for(int i = 0; i < numSize; i++){
            result.add(num.pop());
        }

        Collections.reverse(result);
        return result;
    }
}
