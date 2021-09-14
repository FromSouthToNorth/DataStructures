package vip.hyzt.bag;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 【包】不允许删除元素的集合（仅收集和迭代）
 * @author hy
 * @param <E> - 这个包中元素的通用类型
 */
public class Bag<E> implements Iterable<E> {

    /**
     * 包的第一个元素
     */
    private Node<E> firstElement;

    /**
     * 包的大小
     */
    private int size;

    /**
     * 包的默认构造器
     */
    public Bag() {
        firstElement = null;
        size = 0;
    }

    /**
     * 返回如果这个包是空的，则为真，否则为假
     */
    public boolean isEmpty() {
        return firstElement == null;
    }

    /**
     * 返回元素数量
     */
    public int size() {
        return size;
    }

    /**
     * 添加元素
     */
    public void add(E element) {
        Node<E> oldFirst = firstElement;
        firstElement = new Node<>();
        firstElement.content = element;
        firstElement.nextElement = oldFirst;
        size++;
    }

    /**
     * 返回如果包含元素为真，不包含为假
     * @param element 要找的元素
     */
    public boolean contains(E element) {
        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回 一个迭代器，它以任意顺序迭代这个包中的元素
     */
    @Override
    public Iterator<E> iterator() {
        return new ListIterator<>(firstElement);
    }

    @SuppressWarnings("hiding")
    private class ListIterator<E> implements Iterator<E> {

        private Node<E> currentElement;

        public ListIterator(Node<E> firstElement) {
            currentElement = firstElement;
        }

        @Override
        public boolean hasNext() {
            return currentElement != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E element = currentElement.content;
            currentElement = currentElement.nextElement;
            return element;
        }

    }

    private static class Node<E> {

        private E content;

        private Node<E> nextElement;

    }

}
