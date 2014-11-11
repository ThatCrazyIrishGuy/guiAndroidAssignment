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
	
	private List<Item> items;
	
	public Basket()
	{
		 items = new ArrayList<Item>();
	}

	public void addItem(Item item) 
	{
		
		items.add(item);
	}
	
	public Item findItem(String itemName)
	{
		for (Item i : items)
		{
			if (i.equals(itemName))
			{
				return i;
			}
		}
		Item item = new Item(itemName,-1.0,0);
		return item;
	}

	public Double getTotal()
	{
		Double total = 0.0;
		
		for (Item i : items)
		{
			total += i.getPrice() * i.getQuantity();
		}
		
		total *= 100;
		total = (double) Math.round(total);
		total /= 100;
		
		return total;
	}
	
}
