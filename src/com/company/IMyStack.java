package com.company;

public interface IMyStack<T> extends Iterable<T> {

    T pop();

    T push(T t);

    T peek();

    int size();

    boolean isEmpty();
}