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
}
