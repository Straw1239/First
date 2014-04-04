package chapter8;

public class GroceryItemOrder 
{
	private String name;
	private int quantity;
	private double price;
	public GroceryItemOrder(String name, int quantity, double price)
	{
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}
	public double getCost()
	{
		return quantity * price;
	}
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantity;
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
		GroceryItemOrder other = (GroceryItemOrder) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}
}
