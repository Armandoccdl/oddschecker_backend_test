package com.oddschecker;

import java.io.BufferedReader;
import java.io.FileReader;

import static com.oddschecker.Utils.reassemble;

public class Main {

    public static void main(String[] args) {

        try (BufferedReader in = new BufferedReader(new FileReader("src/inputs.txt"))) {
            String fragmentProblem;
            while ((fragmentProblem = in.readLine()) != null) {
                System.out.println(reassemble(fragmentProblem));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
