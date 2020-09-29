package com.company;

import Tree.HuffmanTreeCreator;
import Tree.ShannonFanoTreeCreator;

import java.util.Date;

public class Main {

    public static void main(String[] args) {

        String datafile = "Voyna_i_mir.txt";
        String coded = "coded.bin";
        int n = 1;

        for (int i = 1; i < 2; i++){
            System.out.println("Group by " + i + " characters.");

            Date date = new Date();
            Encoder encoder = new Encoder(datafile, "test_" + i + ".bin", new HuffmanTreeCreator(), i);
            encoder.encode();
            System.out.println("Encoding complete in: " + ((new Date()).getTime() - date.getTime()));

            date = new Date();
            Decode decode = new Decode("test_" + i + ".bin", "decoded_" + i + ".txt");
            decode.decode();
            System.out.println("Decoding complete in: " + ((new Date()).getTime() - date.getTime()));

            System.out.println("Complete!");
            System.out.println();
        }
    }
}
