package ie.dit.britton.darren.shoppinglist;

public class OrderLine extends Item{
	
	private int quantity ;
	
	public OrderLine(String name, Double price,int quantity) {
		super(name, price);
		this.quantity = quantity;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	public void setQuantity(int itemQuantity)
	{
		this.quantity = itemQuantity;
	}
	public double getTotal()
	{
		Double total = getPrice() * getQuantity();
		total *= 100;
		total = (double) Math.round(total);
		total /= 100;
		
		return total;
	}
	
	
}
