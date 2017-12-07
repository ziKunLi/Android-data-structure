package com.example.newbies.myapplication.util;

/**
 * Created by NewBies on 2017/12/7.
 */

public interface MyList<E> {
    /**将一个新的元素添加入线性表**/
    public void add(E e);

    /**将一个元素添加到线性表指定角标的位置**/
    public void add(int index, E e);

    /**清空线性表**/
    public void clear();

    /**如果线性表包含该元素则返回真**/
    public boolean contains(E e);

    /**返回指定角标处的元素**/
    public E get(int index);

    /**返回线性表中第一个匹配该元素的下标,如果该元素不存在，那么就返回-1**/
    public int indexOf(E e);

    /**如果这个线性表为空，则返回真**/
    public boolean isEmpty();

    /**返回线性表中最后一个匹配该元素的下标,如果该元素不存在，那么就返回-1**/
    public int lastIndexOf(E e);

    /**删除指定元素**/
    public boolean remove(E e);

    /**删除指定下标处的元素并返回该元素**/
    public E remove(int index);

    /** 替换指定下标处的元素**/
    public Object set(int index, E e);

    /** 返回该线性表的大小**/
    public int size();

    public boolean addAll(MyList<E> list);

    public boolean removeAll(MyList<E> list);

    public boolean retainAll(MyList<E> list);
}
