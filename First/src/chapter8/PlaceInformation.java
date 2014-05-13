//Rajan Troll
//Straw1239@gmail.com

package chapter8;

public class PlaceInformation {

	private String name,address,tag;
	private GeoLocation location;
	
	public PlaceInformation(String name, String address, String tag, double latitude, double longtitude) 
	{
		this.name = name;
		this.address = address;
		this.tag = tag;
		location = new GeoLocation(latitude,longtitude);
	}

	public String getName() 
	{
		return name;
	}

	public String getAddress() 
	{
		return address;
	}

	public String getTag() 
	{
		return tag;
	}
	
	public double getLatitude()
	{
		return location.getLatitude();
	}
	
	public double getLongtitude()
	{
		return location.getLongitude();
	}

	public double distanceFrom(GeoLocation location) 
	{
		return this.location.distanceFrom(location);
	}
	
	@Override
	public String toString()
	{
		return name + ", " + address;
	}

}
