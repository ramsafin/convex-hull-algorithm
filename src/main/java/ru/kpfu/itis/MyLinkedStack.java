package ru.kpfu.itis;

import java.util.NoSuchElementException;

import java.util.*;


public class MyLinkedStack<T> implements IMyStack<T> {

    private int size;

    private Item first;

    @Override
    public T pop() {
        if (size <= 0) {
            throw new NoSuchElementException("Stack is empty");
        }
        T t = first.getT();
        first = first.getNextItem();
        size--;
        return t;

    }

    @Override
    public T push(T t) {

        if (first == null) {
            first = new Item(t);
            size++;
            return t;
        }
        first = new Item(t, first);
        size++;
        return t;
    }

    @Override
    public T peek() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        return first.getT();
    }

    @Override
    public int size() {

        return size;
    }

    public T nextToTop() {

        if (size < 2) {
            throw new NoSuchElementException("No such element!");
        }

        return first.nextItem.getT();
    }

    @Override
    public boolean isEmpty() {

        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int cursor = 0;
            private int count = 0;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public T next() {
                if (cursor == size) {
                    throw new NoSuchElementException("No such element with index " + cursor);
                }
                Item current;
                for (current = first, cursor = 0; cursor++ < count; current = current.getNextItem()) ;
                count++;
                return current.getT();

            }
        };
    }

    private class Item {

        private final T object;

        private Item nextItem;

        public Item(T t, Item nextItem) {
            object = t;
            this.nextItem = nextItem;
        }

        public Item(T object) {
            this.object = object;
        }

        public Item getNextItem() {

            return nextItem;
        }

        public T getT() {

            return object;
        }
    }

    public List<T> toList() {

        List<T> list = new ArrayList<>();

        for (Item i = first; i != null; i = i.nextItem) {
            list.add(i.getT());
        }

        return list;
    }

}