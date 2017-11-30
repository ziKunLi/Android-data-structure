package com.example.newbies.myapplication.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆，用于进行堆排序
 * @author NewBies
 * @date 2017/11/29
 */
public class Heap<E extends Comparable> implements Cloneable{
    private  ArrayList<E> list = new ArrayList<E>();

    public Heap(){

    }

    public Heap(E[] objects){

    }

    public void add(E newObject){
        list.add(newObject);
        int currentIndex = list.size() - 1;

        while(currentIndex > 0){
            int parentIndex = (currentIndex - 1)/2;

            if(list.get(currentIndex).compareTo(list.get(parentIndex)) > 0){
                E temp = list.get(currentIndex);
                list.set(currentIndex, list.get(parentIndex));
                list.set(parentIndex, temp);
            }
            else {
                break;
            }

            currentIndex = parentIndex;
        }
    }

    public E remove(){
        if(list.size() == 0){
            return null;
        }

        E removedObjectE = list.get(0);
        list.set(0, list.get(list.size() - 1));
        list.remove(list.size() - 1);

        int currentIndex = 0;
        while(currentIndex < list.size()){
            int leftChildIndex = 2 * currentIndex + 1;
            int rightChildIndex = 2 * currentIndex + 2;

            if(leftChildIndex >= list.size()){
                break;
            }

            int maxIndex = leftChildIndex;

            if(rightChildIndex < list.size()){
                if(list.get(maxIndex).compareTo(list.get(rightChildIndex)) < 0){
                    maxIndex = rightChildIndex;
                }
            }

            if(list.get(currentIndex).compareTo(list.get(maxIndex)) < 0){
                E tempE = list.get(maxIndex);
                list.set(maxIndex, list.get(currentIndex));
                list.set(currentIndex, tempE);
                currentIndex = maxIndex;
            }
            else {
                break;
            }
        }
        return removedObjectE;
    }

    /**
     * 选做题第一题
     * @param list
     */
    public void sort(E[] list){
        for(int i = 0; i < list.length; i++){
            this.add(list[i]);
        }
        for(int i = 0; i < list.length; i++){
            list[i] = this.remove();
        }
    }

    public int getSize(){
        return list.size();
    }

    public List<E> getList(){
        return list;
    }

    /**
     * 深度复制，将其数据域list也复制了
     */
    @Override
    public Object clone(){
        ArrayList<E> arrayList = (ArrayList<E>) list.clone();
        Heap<E> heap = new Heap<>();
        for(int i = 0 ; i < arrayList.size(); i++){
            heap.add(arrayList.get(i));
        }

        return heap;
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof Heap<?>){
            Heap<?> heap = (Heap<?>)object;
            if(heap == this&&heap.getList() == this.list){
                return true;
            }
        }
        return false;
    }
}
