package com.company;

import FileBitStream.BitOutputStream;
import Tree.Tree;
import Tree.TreeCreator;

import java.io.*;
import java.util.*;

public class Encoder {
    private String datafile;
    private String codedfile;
    private BufferedReader bufferedReader;
    private FileOutputStream fileOutputStream;
    private BitOutputStream bitOutputStream;
    private int length; // cluster length
    private Tree tree;

    public Encoder(String datafile, String codedfile, TreeCreator treeCreator){
        this(datafile, codedfile, treeCreator, 1);
    }

    public Encoder(String datafile, String codedfile, TreeCreator treeCreator, int length){
        this.datafile = datafile;
        this.codedfile = codedfile;
        openFiles();
        this.length = length;
        createTree(treeCreator);
    }

    private void openFiles(){
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(datafile), "UTF8"));
            fileOutputStream = new FileOutputStream(codedfile);
            bitOutputStream = new BitOutputStream(fileOutputStream);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void closeFiles(){
        try {
            bufferedReader.close();
            bitOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reopenFiles(){
        closeFiles();
        openFiles();
    }

    private String read(){
        int character = 1;
        String string = "";

        try{
            while (string.length() < length &&
                    (character = bufferedReader.read()) != -1){
                string += "" + (char)character;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return string;
    }

    private void write(String string){
        try {
            bitOutputStream.write(tree.encode(string));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Integer> createMap(){
        HashMap<String, Integer> map = new HashMap<>();

        String string;
        while (!(string = read()).isEmpty()){
            if (map.containsKey(string))
                map.replace(string, map.get(string) + 1);
            else
                map.put(string, 1);
        }

        // Because we get to the end of the file
        // we must reopen it
        reopenFiles();

        return map;
    }

    private void createTree(TreeCreator treeCreator){
        Map<String, Integer> map = createMap();
        // Add end of file
        map.put("EndOfFile", 1);

        tree = treeCreator.createTree(map);
    }

    private void saveMap(String treefile){
        try {
            ObjectOutputStream fout = new ObjectOutputStream(fileOutputStream);
            fout.writeObject(tree);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void encode(){
        saveMap("tree.bin");

        System.out.println(tree.toString());

        String string;
        // Reading from datafile and write coded data into another file
        while (!(string = read()).isEmpty()){
            write(string);
        }

        // Add end of file
        write("EndOfFile");

        closeFiles();
    }
}
