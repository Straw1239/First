package numbers;

public class Nibbles2D 
{
	private long data;
	
	public Nibbles2D(long data)
	{
		this.data = data;
	}
	
	public byte getSquare(byte row, byte column)
	{
		return getSquare(getIndex(row,column));
	}
	
	public long getData()
	{
		return data;
	}
	
	public void setSquare(byte value,byte row, byte column)
	{
		setSquare(value,getIndex(row,column));
	}
	
	public int index(boolean row, byte number)
	{
		int index = 0;
		if(row)
		{
			for(byte i = 0; i < 4; i++)
			{
				index += (getSquare(i,number)) << (i << 2);
			}
		}
		else
		{
			for(byte i = 0;i < 4;i++)
			{
				index += (getSquare(number,i)) << (i << 2);
			}
		}
		return index; 
	}

	private byte getSquare(byte index)
	{
		if(index < 0 || index > 15) throw new IndexOutOfBoundsException(Integer.toString(index));
		return  (byte)(((byte) (data >>> (index * 4))) & (byte)0B00001111);
	}
	
	private void setSquare(byte value, byte index)
	{
		//TODO implement index-based nibble setting
	}
	
	private byte getIndex(byte row, byte column)
	{
		return (byte) ((row << 2) ^ column);
	}
	
}
