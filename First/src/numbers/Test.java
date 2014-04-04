package numbers;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Scanner;

import chess.DrawingPanel;

public class Test {

	public static void main(String[] args) {
		byte[][] test = new byte[4][4];
		test[0] = new byte[]{1,0,0,1};
		test[1] = new byte[]{0,0,0,0};
		test[2] = new byte[]{0,1,0,0};
		test[3] = new byte[]{0,0,0,1};
		FastState s = new FastState(test);
		s.print(System.out);
		DrawingPanel panel = new DrawingPanel(600,6000);
		Graphics g = panel.getGraphics();
		g.setColor(Color.black);
		s.draw(g, 600);
		Scanner console = new Scanner(System.in);
		while(true)
		{
			s.addRandomTile(true);
			s.draw(g, 600);
			s.print(System.out);
			console.nextLine();
			
		}
		

	}
	
}
