package com.company;

import FileBitStream.BitInputStream;
import FileBitStream.BitOutputStream;
import Tree.Tree;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Decode {
    private String codedfile;
    private String decodedfile;

    private FileInputStream fileInputStream;
    private BitInputStream bitInputStream;
    private FileOutputStream fileOutputStream;
    private Tree tree;

    public Decode(String codedfile, String decodedfile){
        this.codedfile = codedfile;
        this.decodedfile = decodedfile;
        openFiles();
    }

    private void openFiles(){
        try {
            fileInputStream = new FileInputStream(codedfile);
            bitInputStream = new BitInputStream(fileInputStream);
            fileOutputStream = new FileOutputStream(decodedfile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void closeFiles(){
        try {
            bitInputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isEnd(List<Boolean> list){
        return tree.decode(list).equals("EndOfFile");
    }

    private List<Boolean> read(){
        List<Boolean> list = new LinkedList<>();

        try{
            while (!tree.contains(list)) {
                list.add(bitInputStream.read());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    private void write(List<Boolean> list){
        try{
            fileOutputStream.write(tree.decode(list).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readTree(){
        try {
            ObjectInputStream fin = new ObjectInputStream(fileInputStream);
            tree = (Tree) fin.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void decode(){
        readTree();

        List<Boolean> list;

        while (true){
            list = read();
            if(isEnd(list))
                break;
            write(list);
        }

        closeFiles();
    }
}
