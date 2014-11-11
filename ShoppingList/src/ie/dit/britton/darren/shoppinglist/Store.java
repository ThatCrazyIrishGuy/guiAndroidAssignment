package ie.dit.britton.darren.shoppinglist;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Store
{
	
	private static Store instance = null;
	
	public static Store getInstance()
	{
		if (instance == null)
		{
			instance = new Store();
		}
		return instance;
	}
	
	private List<Item> items;
	
	public Store()
	{
		 items = new ArrayList<Item>();
	}

	public String[] getItemNames()
	{
		 List<String> names = new ArrayList<String>();
		 
		 for(Item i : items)
		 {
			 names.add(i.getName());
		 }
		 
		 String[] namesArray = new String[names.size()];
		 return names.toArray(namesArray);
	}
	
	public void populate(String[] names) 
	{
		Random r = new Random();
		
        Double randPrice = 0.0;
        randPrice = r.nextDouble() * 10;
        randPrice *= 100;
        randPrice = (double) Math.round(randPrice);
        randPrice /= 100;
		
		for(String name : names)
		{
			items.add(new Item(name,randPrice));
		}
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
		Item item = new Item(itemName,-1.0);
		return item;
	}
	
}
