package ru.kpfu.itis;

public interface IMyStack<T> extends Iterable<T> {

    T pop();

    T push(T t);

    T peek();

    int size();

    boolean isEmpty();
}