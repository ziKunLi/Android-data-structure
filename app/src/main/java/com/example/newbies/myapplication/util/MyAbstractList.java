package com.example.newbies.myapplication.util;

/**
 *
 * @author NewBies
 * @date 2017/12/7
 */

public abstract class MyAbstractList<E> implements MyList<E>{
    protected int size = 0;  //确定该线性表的大小

    /**创建一个默认的线性表**/
    protected MyAbstractList(){}

    /**通过一个数组创建一个线性表**/
    protected MyAbstractList(E[] objects){
        for(int i = 0; i < objects.length; i++){
            add(objects[i]);
        }
    }

    /**添加一个元素到该线性表中**/
    @Override
    public void add(E e){
        add(size,e);
    }

    /**如果该线性表为空，则返回真**/
    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    /**返回该线性表的大小**/
    @Override
    public int size(){
        return size;
    }

    /**删除指定元素，删除成功则返回true，失败则返回FALSE**/
    @Override
    public boolean remove(E e){
        if(indexOf(e) >= 0){
            remove(indexOf(e));
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 将指定链表全部添加到该链表中，如果该链表发生了改变则返回true
     */
    @Override
    public boolean addAll(MyList<E> list){
        if(list.isEmpty()){
            return false;
        }
        for(int i = 0; i < list.size(); i++){
            this.add(list.get(i));
        }
        return true;
    }

    /**
     * 删除该链表与指定链表中相同的元素，如果该链表发生改变，那么返回true
     */
    @Override
    public boolean removeAll(MyList<E> list){
        boolean isChanged = false;
        for(int i = 0; i < size; i++){
            E temp = this.get(i);
            if(list.contains(temp)){
                this.remove(temp);
                isChanged = true;
            }
        }
        return isChanged;
    }

    /**
     * 删除该链表与指定链表中不同的元素，如果该链表发生改变，那么返回true
     */
    @Override
    public boolean retainAll(MyList<E> list){
        boolean isChanged = false;
        for(int i = 0; i < size; i++){
            E temp = this.get(i);
            if(!list.contains(temp)){
                this.remove(temp);
                isChanged = true;
            }
        }
        return isChanged;
    }
}
