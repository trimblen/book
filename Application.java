package bookmap;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Application {		
	public static void main(String[] args) throws IOException {
		 //process the whole file
		 ProcessInputFile();
	};
	
	private static void ProcessInputFile() throws IOException {	
		String content 				= new String(Files.readAllBytes(Paths.get("D:/data/input.txt")));		
		String[] splitCom 			= content.split("\\s+");
		List<String> resultArr 		= new ArrayList<String>();
		List<Book> bookings 		= new ArrayList<Book>();
		
		//parsing file and getting command row
		for (int i = 0; i < splitCom.length; i++) {			
			String[] commRow 	= splitCom[i].split(",");			
			String resStr 		= ExecuteCommandRow(commRow, bookings); 
			
			if(!resStr.trim().isEmpty()) {				
				resultArr.add(resStr);
			};			
		};	
	     
	    //display result on screen and write results to the text file
	    if(!resultArr.isEmpty()) {	    	
		    for(String res: resultArr) {	    	
			       System.out.println(res);
			};	
						
			FileWriter writer = new FileWriter("D:/data/output.txt"); 
			for(String str: resultArr) {
			  writer.write(str + System.lineSeparator());
			};
			
			writer.close();
	    };
	};
	
	private static String ExecuteCommandRow(String[] commandRow, List<Book> bookArray) {		
		StringBuilder resString = new StringBuilder();
		
		switch(commandRow[0]) {
			case "u":
			    if (commandRow[3].contains("ask")){
                    CreateItem(Integer.parseInt(commandRow[1]), (short) Integer.parseInt(commandRow[2]),"ask",bookArray);
                }else if (commandRow[3].contains("bid")){
                    CreateItem(Integer.parseInt(commandRow[1]), (short) Integer.parseInt(commandRow[2]),"bid",bookArray);
                };
				break;
			case "q":
                if (commandRow[1].contains("best_ask")){
                    Optional<Book> itemAsk       = GetMaxValue(bookArray, "ask");
                    if (!itemAsk.isEmpty()){
                        resString.append(String.valueOf(itemAsk.get().getPrice()));
                        resString.append(",");
                        resString.append(String.valueOf(itemAsk.get().getBsize()));                      
                    };
                }else if (commandRow[1].contains("best_bid")){           
                    Optional<Book> itemBid       = GetMaxValue(bookArray, "bid");
                    if (!itemBid.isEmpty()){                    	
                        resString.append(String.valueOf(itemBid.get().getPrice()));
                        resString.append(",");
                        resString.append(String.valueOf(itemBid.get().getBsize()));   
                    };
                }else if (commandRow[1].contains("size")){
                	Optional<Book> itemSize       = GetMaxSizeByPrice(bookArray, Integer.parseInt(commandRow[2]));
                    if (!itemSize.isEmpty()){
                        resString.append(String.valueOf(itemSize.get().getBsize()));
                    };
                };    
				break;
			case "o":
                if (commandRow[1].contains("buy")){
                    Optional<Book> cheapestItem = GetMinValue(bookArray, "ask");
                    if (!cheapestItem.isEmpty()){
                    	bookArray.remove(cheapestItem.get());
                    };
                }else if (commandRow[1].contains("sell")){
                	Optional<Book> mexpItem     = GetMaxValue(bookArray,"bid");
                    if (!mexpItem.isEmpty()){
                    	bookArray.remove(mexpItem.get());
                    };
                };    
			default:
				break;
		};
		
		return resString.toString();		
	};
	
	private static void CreateItem(int price, short qsize, String qtype, List<Book> bookArray) {		
		Optional<Book> exsElement = bookArray.stream()
	             .filter(book -> book.getPrice()==price && book.getBsize()==qsize && book.getBtype()==qtype)
	             .findFirst();
		
		if(!exsElement.isEmpty()) {			
			bookArray.remove(exsElement.get());			
		};
		
		bookArray.add(new Book(price,qsize, qtype));		
	};
	
	private static Optional<Book> GetMaxValue(List<Book> bookArray, String btype){		
	     Comparator<Book> comparator = Comparator.comparingInt(Book::getPrice)
                 .thenComparingInt(Book::getBsize);
	     
	     bookArray.sort(comparator.reversed());
	     
	     Optional<Book> searchElement = bookArray.stream()
	             .filter(book -> book.getBtype().equals(btype))
	             .findFirst();
				
		return searchElement;		
	}; 
	
	private static Optional<Book> GetMaxSizeByPrice(List<Book> bookArray, int price){		
	     Comparator<Book> comparator = Comparator.comparingInt(Book::getPrice)
                .thenComparingInt(Book::getBsize);
	     
	     bookArray.sort(comparator.reversed());
	     
	     Optional<Book> searchElement = bookArray.stream()
	             .filter(book -> book.getPrice()<=price)
	             .findFirst();
				
		return searchElement;		
	}; 
	
	private static Optional<Book> GetMinValue(List<Book> bookArray, String btype){	   
		Comparator<Book> comparator = Comparator.comparingInt(Book::getPrice)
                 .thenComparingInt(Book::getBsize);
	     
	     bookArray.sort(comparator);
	     
	     Optional<Book> searchElement = bookArray.stream()
	             .filter(book -> book.getBtype().equals(btype))
	             .findFirst();
	     
		return searchElement;		
	}; 	
}
