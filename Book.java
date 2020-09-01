package bookmap;

public class Book {
    private int 	price;
    private int 	bsize;
    private String 	btype;

    public Book (int price, short bsize, String btype) {
        this.price = price;
        this.bsize = bsize;
        this.btype = btype;
    };
    
    public int getPrice() {    	
    	return price; 
    };
    
    public int getBsize() {    	
    	return bsize; 
    };
    
    public String getBtype() {    	
    	return btype; 
    };
}
