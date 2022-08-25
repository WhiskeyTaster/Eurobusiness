package com.mygdx.game;

public class Pair <T extends Number, V extends Number> {

    private T first;
    private V second;

    public Pair() {

    }

    public Pair (T first, V second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return this.first;
    }

    public V getSecond() {
        return this.second;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public void setSecond(V second) {
        this.second = second;
    }

    public boolean equals(Pair<T, V> secondPair) {
        return this.first.equals(secondPair.first) && this.second.equals(secondPair.second);
    }

    public boolean bothValuesEqual() {
        return first.equals(second);
    }
}