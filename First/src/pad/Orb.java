package pad;

public enum Orb 
{
	FIRE,WATER,WOOD,DARK,LIGHT,HEART;
	public static Orb getColor(char color)
	{
		color = Character.toLowerCase(color);
		switch (color)
		{
		case 'f': return FIRE;
		case 'u': return WATER;
		case 'w': return WOOD;
		case 'd': return DARK;
		case 'l': return LIGHT;
		case 'h': return HEART;
		default: throw new IllegalArgumentException();
		
		}
	}
}
