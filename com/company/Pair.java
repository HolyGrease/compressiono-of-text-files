package com.company;

public class Pair <T, U> {
    private T left;
    private U right;

    public Pair(T left, U right){
        this.left = left;
        this.right = right;
    }

    public T getLeft(){
        return left;
    }

    public U getRight(){
        return right;
    }
}
