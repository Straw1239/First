package pad;

public class BoardState 
{
	Orb[][] state;
	public BoardState(String state)
	{
		this.state = new Orb[5][6];
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 6; j++)
			{
				this.state[i][j] = Orb.getColor(state.charAt(6 * i + j));
			}
		}
	}
	public BoardState(Orb[][] state)
	{
		this.state = state;
	}
}
