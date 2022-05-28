package com.bookmapsolution;

import java.io.*;
import java.util.*;

public class BookMap {

    private LinkedList<BookRecord> bidList;
    private LinkedList<BookRecord> askList;

    public BookMap () {
        this.bidList = new LinkedList<BookRecord>();
        this.askList = new LinkedList<BookRecord>();
    };

    public void loadAndWriteBookMapFile() throws IOException {
        String fPath                = "input.txt";
        ArrayList<String> arrInput  = loadAndGetInputFile(fPath);
        StringBuilder resString     = new StringBuilder();

        for (int i = 0; i < arrInput.size(); i++){
            String[] partsLine = arrInput.get(i).split(",");
            if (partsLine[0].equals("u")){
                int lPrice  = Integer.parseInt(partsLine[1]);
                int lSize   = Integer.parseInt(partsLine[2]);

                if (partsLine[3].equals("ask")) {
                    Optional<BookRecord> resultAsk = this.askList.stream()
                            .filter(Objects::nonNull)
                            .filter(b -> b.getPrice() == lPrice)
                            .findFirst();

                    if (!resultAsk.isPresent())
                    {
                        QueryBuilder.addAskOrBid(this.askList, lPrice, lSize);
                    }else{
                        QueryBuilder.updateSizeBidOrAsk(this.askList, resultAsk, lPrice, lSize);
                    };
                }else if (partsLine[3].equals("bid")){
                    Optional<BookRecord> resultBid = this.bidList.stream()
                                .filter(Objects::nonNull)
                                .filter(b -> b.getPrice() == lPrice)
                                .findFirst();

                    if (!resultBid.isPresent())
                    {
                        QueryBuilder.addAskOrBid(this.bidList, lPrice, lSize);
                    }else{
                        QueryBuilder.updateSizeBidOrAsk(this.bidList, resultBid, lPrice, lSize);
                    };
                };
            }else if (partsLine[0].equals("q")){
                if (partsLine.length==2){
                    if (partsLine[1].equals("best_bid")){
                        String resStrB = QueryBuilder.getBestBidOrAsk(this.bidList, "best_bid");

                        resString.append(resStrB+"\n");
                    }else if (partsLine[1].equals("best_ask")){
                        String resStrA = QueryBuilder.getBestBidOrAsk(this.askList, "best_ask");

                        resString.append(resStrA+"\n");
                };
                }else if (partsLine.length==3){
                    String resStr = QueryBuilder.getBestBidOrAskBySize(this.askList, this.bidList, Integer.parseInt(partsLine[2]));

                    resString.append(resStr+"\n");
                };
            }else if (partsLine[0].equals("o")){
                int lSize = Integer.parseInt(partsLine[2]);

                if (partsLine[1].equals("buy")){
                    QueryBuilder.shareOutSizeBidOrAsk(this.askList, String.valueOf(partsLine[1]), lSize);
                }else if (partsLine[1].equals("sell")){
                    QueryBuilder.shareOutSizeBidOrAsk(this.bidList, String.valueOf(partsLine[1]), lSize);
                };
            };
        };

        writeOutputFile("output.txt", resString.toString());
    };

    private void writeOutputFile(String fileName, String content){
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(fileName))) {

            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        };
    };

    private ArrayList<String> loadAndGetInputFile(String fileName) throws IOException {
        ArrayList<String> lines = new ArrayList<String>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    };
}
