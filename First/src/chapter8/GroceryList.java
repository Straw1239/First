package chapter8;

import java.util.Arrays;

public class GroceryList 
{
	private static final int maxSize = 10;
	private GroceryItemOrder[] items = new GroceryItemOrder[maxSize];
	private int numItems = 0;
	
	public GroceryList()
	{
		
	}
	
	public void add(GroceryItemOrder item)
	{
		if(numItems >= maxSize) throw new IllegalStateException();
		items[numItems] = item;
		numItems++;
	}
	
	public double getTotalCost()
	{
		double cost = 0;
		for(int i = 0; i < numItems;i++)
		{
			cost += items[i].getCost();
		}
		return cost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(items);
		result = prime * result + numItems;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroceryList other = (GroceryList) obj;
		if (!Arrays.equals(items, other.items))
			return false;
		if (numItems != other.numItems)
			return false;
		return true;
	}
	
}
