package com.example.newbies.myapplication.util;

/**
 * Created by NewBies on 2017/12/7.
 */

public class MyArrayList<E> extends MyAbstractList<E> {
    private static final int INITIAL_CAPACITY = 16;
    private E[] data  = (E[])new Object[INITIAL_CAPACITY];

    /**创建一个默认的线性表**/
    public MyArrayList(){}

    /**创建一个有数组构成的线性表**/
    public MyArrayList(E[] objects){
        for(int i = 0; i < objects.length; i++)
            //警告：不能使用super(objects)
            add(objects[i]);
    }

    /**在指定角标处插入一个新元素**/
    @Override
    public void add(int index, E e) {
        ensureCapacity();

        //右移数组
        for(int i = size - 1; i >= index; i--)
            data[i + 1] = data[i];

        //在角标为index处插入该元素
        data[index] = e;

        size ++;

    }

    /**创建一个更大的数组**/
    private void ensureCapacity(){
        if(size >= data.length){
            E[] newData	= (E[])(new Object[size * 2 + 1]);
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    /**清空该线性表**/
    @Override
    public void clear() {
        data = (E[])new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**如果该线性表包含该元素，则返回真**/
    @Override
    public boolean contains(E e) {
        for(int i = 0; i < size; i++){
            if(e.equals(data[i]))
                return true;
        }
        return false;
    }

    /**返回该角标处的元素**/
    @Override
    public E get(int index) {
        return data[index];
    }

    /**返回匹配到第一个元素的角标，如果不存在则返回-1**/
    @Override
    public int indexOf(E e) {
        for(int i = 0; i < size; i++)
            if(e.equals(data[i]))
                return i;
        return -1;
    }


    /**返回最后一个匹配到该元素的角标，如果没有匹配到的，则返回-1**/
    @Override
    public int lastIndexOf(E e) {
        for(int i = size; i > 0; i--)
            if(e.equals(data[i]))
                return i;
        return -1;
    }

    /**删除并返回指定角标处的元素**/
    @Override
    public E remove(int index) {
        E e = data[index];

        //左移
        for(int j = index; j < size - 1; j++)
            data[j] = data[j + 1];
        //现在，这个位置的元素为null
        data[size - 1] = null;

        size--;

        return e;
    }

    /**替换指定角标处的元素**/
    @Override
    public Object set(int index, E e) {
        E old = data[index];
        data[index] = e;
        return old;
    }

    /**重写了toString方法，返回该线性表中的所有数据**/
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder("[");

        for(int i = 0; i < size; i++){
            result.append(data[i]);
            if(i < size - 1)
                result.append(", ");
        }

        return result.toString() + "]";
    }

    /**压缩数组到实际长度**/
    public void trimToSize(){
        if(size != data.length){
            //如果实际长度等于当前数组的长度，那么就不需要压缩
            E[] newData = (E[])(new Object[size]);
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }
}
