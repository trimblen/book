package com.bookmapsolution;
import java.util.*;

public class QueryBuilder {
    private QueryBuilder(){

    };

    public static void addAskOrBid(LinkedList<BookRecord> abList, int bPrice, int bSize)
    {
        BookRecord newLine = new BookRecord(bPrice, bSize);

        abList.add(newLine);
    };

    public static String getBestBidOrAsk(LinkedList<BookRecord> abList, String bestType) {
        String rString = "";

        if (bestType.equals("best_ask")) {
            abList.sort(Comparator.comparing(BookRecord::getPrice));
        } else if (bestType.equals("best_bid")) {
            abList.sort(Comparator.comparing(BookRecord::getPrice).reversed());
        };

        Optional<BookRecord> resultObj = abList.stream()
                .filter(Objects::nonNull)
                .findFirst();

        rString = getResultStringFromClass(resultObj);

        return rString;
    };

    public static String getBestBidOrAskBySize(LinkedList<BookRecord> aList, LinkedList<BookRecord> bList, int bPrice) {
        String rString = "";

        aList.sort(Comparator.comparing(BookRecord::getPrice).reversed());
        bList.sort(Comparator.comparing(BookRecord::getPrice).reversed());

        Optional<BookRecord> resultObjAsk = aList.stream()
                .filter(Objects::nonNull)
                .filter(b -> b.getPrice() == bPrice)
                .findFirst();

        Optional<BookRecord> resultObjBid = bList.stream()
                .filter(Objects::nonNull)
                .filter(b -> b.getPrice() == bPrice)
                .findFirst();

        int askPrice  = 0;
        int bidPrice  = 0;
        int askSize   = 0;
        int bidSize   = 0;

        if (resultObjAsk.isPresent()){
            BookRecord rClassA = resultObjAsk.get();

            askPrice  = rClassA.getPrice();
            askSize   = rClassA.getBSize();
         };

        if (resultObjBid.isPresent()){
            BookRecord rClassB = resultObjBid.get();

            bidPrice    = rClassB.getPrice();
            bidSize     = rClassB.getBSize();
        };

        if (askPrice == bidPrice){
            if (askSize == bidSize){
                rString = String.valueOf(askSize);
            }else if (askSize >bidSize) {
                rString = String.valueOf(askSize);
            }else if (bidSize> askSize){
                rString = String.valueOf(bidSize);
            };
        }else if (askPrice>bidPrice){
            rString = String.valueOf(askSize);
        }else if (bidPrice>askPrice){
            rString = String.valueOf(bidSize);
        };

        return rString;
    };

    private static String getResultStringFromClass(Optional<BookRecord> resObj){
        String rString = "";

        if (!resObj.isPresent()) {
            rString = "0, 0";
        } else {
            BookRecord rClass = resObj.get();

            int rPrice = rClass.getPrice();
            int rSize = rClass.getBSize();

            rString = String.valueOf(rPrice) + "," + String.valueOf(rSize);
        };

        return rString;
    };

    public static void updateSizeBidOrAsk(LinkedList<BookRecord> abList, Optional<BookRecord> bRecordVar, int pPrice, int pSize) {
        BookRecord rClass   = bRecordVar.get();
        int sIndex          = abList.indexOf(rClass);

        rClass.setbSize(pSize);

        abList.set(sIndex, rClass);
    }

    public static void shareOutSizeBidOrAsk(LinkedList<BookRecord> abList, String bestType, int pSize) {
        if (bestType.equals("buy")){
            abList.sort(Comparator.comparing(BookRecord::getPrice));
        }else if (bestType.equals("sell")) {
            abList.sort(Comparator.comparing(BookRecord::getPrice).reversed());
        }

        Optional<BookRecord> rSearch = abList.stream()
                .filter(Objects::nonNull)
                .findFirst();

        if (rSearch.isPresent()) {
            BookRecord rClass   = rSearch.get();
            int sIndex          = abList.indexOf(rClass);
            int currSize        = rClass.getBSize();
            int setSize         = currSize - pSize;

            rClass.setbSize(setSize);

            abList.set(sIndex, rClass);
        };
    };
}
