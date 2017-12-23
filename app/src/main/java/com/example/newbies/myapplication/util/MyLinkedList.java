package com.example.newbies.myapplication.util;

/**
 *
 * @author NewBies
 * @date 2017/12/7
 */

public class MyLinkedList<E> extends MyAbstractList<E> {
    private Node<E> head,tail;

    /**创建一个默认的链表**/
    public MyLinkedList(){

    }

    /**通过一个数组创建一个链表**/
    public MyLinkedList(E[] object){
        super(object);
    }

    /**获得链表的第一个结点**/
    public E getFirst(){
        if(size == 0) {
            return null;
        } else {
            return head.element;
        }
    }

    /**获得链表的最后一个结点**/
    public E getLast(){
        if(size == 0) {
            return null;
        } else {
            return tail.element;
        }
    }

    /**添加一个结点到链表头**/
    public void addFirst(E e){
        //创建一个新的结点
        Node<E> newNode = new Node<E>(e);
        //将新节点作为头结点
        newNode.next = head;
        //头结点指向这个新节点
        head = newNode;
        size++;//扩展链表大小

        if(tail == null){
            tail = head;
        }
    }

    /**添加一个结点到末尾的位置**/
    public void addLast(E e){
        //创建一个新的结点
        Node<E> newNode = new Node<E>(e);

        if(tail == null){
            head = tail = newNode;
        }
        else{
            tail.next = newNode;
            tail = tail.next;
        }
        size++;
    }

    /**添加一个元素到链表指定角标的位置**/
    @Override
    public void add(int index, E e) {
        if(index == 0){
            addFirst(e);
        }
        else if(index >= size){
            addLast(e);
        }
        else{
            Node<E> current = head;
            //通过循环，找到插入点
            for(int i = 1; i < index; i++){
                current = current.next;
            }
            Node<E> temp = current.next;
            current.next = new Node<E>(e);
            (current.next).next = temp;
            size++;
        }
    }

    /**删除第一个结点**/
    public E removeFirst(){
        if(size == 0) {
            return null;
        } else if(size == 1){
            E temp = head.element;
            head = tail = null;
            size = 0;
            return temp;
        }
        else{
            E temp = head.element;
            head = head.next;
            size--;
            return temp;
        }
    }

    /**删除最后一个结点**/
    public E removeLast(){
        if(size == 0) {
            return null;
        } else if(size == 1){
            Node<E> temp = head;
            //链表被清空
            head = tail = null;
            size = 0;
            return temp.element;
        }
        else{
            Node<E> current = head;
            //查找到倒数第二个结点
            for(int i = 0; i < size - 2; i++){
                current = current.next;
            }

            Node<E> temp = tail;
            //将会倒数第二个结点设为最后一个结点
            tail = current;
            //将最后一个结点删掉
            tail.next = null;
            size--;
            return temp.element;
        }
    }

    /**删除链表指定下标处的结点**/
    @Override
    public E remove(int index) {
        if(size == 0) {
            return null;
        } else if(index >= size) {
            return removeLast();
        }
        else if(index == 0){
            return removeFirst();
        }
        else{
            Node<E> current = head;
            //查找到要删除结点的前一个结点
            for(int i = 0; i < index - 1; i++){
                current = current.next;
            }
            //将即将删除的结点暂存为temp
            Node<E> temp = current.next;
            //删除结点
            current.next = temp.next;
            size--;
            return temp.element;
        }
    }

    /**重写了toString方法，返回该链表所有的元素**/
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder("[");

        Node<E> current = head;
        for(int i = 0; i < size; i++){
            result.append(current.element);
            current = current.next;
            if(current != null){
                result.append(", ");
            }
            else{
                result.append("]");
            }
        }

        return result.toString();
    }

    /**清除该链表**/
    @Override
    public void clear(){
        head = tail = null;
        size = 0;
    }

    /**如果该链表存在该元素则返回真**/
    @Override
    public boolean contains(E e) {
        if(size == 0) {
            return false;
        } else if(size == 1){
            if(e.equals(head.element)) {
                return true;
            }
        }
        else{
            Node<E> current = head;
            for(int i = 0; i < size - 1; i++){
                if(current.element.equals(e)) {
                    return true;
                }
                current = current.next;
            }
        }

        return false;
    }

    /**得到指定下标处的元素**/
    @Override
    public E get(int index) {
        if(size == 0) {
            return null;
        } else if(index >= size){
            return null;
        }
        else{
            Node<E> current = head;
            for(int i = 0; i < index; i++){
                current = current.next;
            }
            return current.element;
        }
    }

    /**得到第一次出现该元素的下标**/
    @Override
    public int indexOf(E e) {
        if(size == 0) {
            return -1;
        } else{
            Node<E> current = head;
            for(int i = 0; i < size - 1; i++){
                if(current.element.equals(e)){
                    return i;
                }
                current = current.next;
            }
        }
        return -1;
    }

    /**返回该元素在该链表最后一次出现位置的角标**/
    @Override
    public int lastIndexOf(E e) {
        if(size == 0) {
            return -1;
        } else{
            Node<E> current = head;
            int index = -1;
            for(int i = 0; i < size - 1; i++){
                if(current.element.equals(e)){
                    index = i;
                }
                current = current.next;
            }
            return index;
        }

    }

    /**替换指定下标处的值**/
    @Override
    public  E set(int index, E e) {
        if(size == 0) {
            return null;
        } else{
            Node<E> current = head;
            for(int i = 0; i < index; i++){
                current = current.next;
            }
//            Node<E> temp = new Node<E>(e);
//            current.next = temp;
//            temp.next = (current.next).next;
            current.element = e;
            return current.element;
        }
    }
    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element){
            this.element = element;
        }
    }
}
