package com.bookmapsolution;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        BookMap bm = new BookMap();
        try
        {
            bm.loadAndWriteBookMapFile();
        }
        catch(IOException ioException)
        {
            throw new RuntimeException("IO Exception in CommandLine Application",ioException);
        };
    };
};
