package ie.dit.britton.darren.shoppinglist;

import java.util.ArrayList;
import java.util.List;

public class Basket
{
	
	private static Basket instance = null;
	
	public static Basket getInstance()
	{
		if (instance == null)
		{
			instance = new Basket();
		}
		return instance;
	}
	
	private List<OrderLine> items;
	
	public Basket()
	{
		 items = new ArrayList<OrderLine>();
	}

	public void addItem(OrderLine item) 
	{
		
		items.add(item);
	}
	
	public boolean findItem(String itemName)
	{
		for (OrderLine i : items)
		{
			if (i.equals(itemName))
			{
				return true;
			}
		}
		return false;
	}

	public Double getTotal()
	{
		Double total = 0.0;
		
		for (OrderLine i : items)
		{
			total += i.getPrice() * i.getQuantity();
		}
		
		total *= 100;
		total = (double) Math.round(total);
		total /= 100;
		
		return total;
	}
	
}