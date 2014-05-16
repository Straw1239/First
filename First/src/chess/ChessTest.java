package chess;

import java.awt.Graphics;

public class ChessTest {

	public static void main(String[] args) {
		State start = State.getStartingPosition();
		State e4 = start.move(1, 4, 3, 4);
		State d5 = e4.move(6, 3, 4, 3);
		State check = d5.move(0, 5, 4, 1);
		State recovery = check.move(6, 2, 5, 2);
		System.out.println(start.isWhiteToPlay());
		System.out.println(e4.isWhiteToPlay());
		System.out.println();
		System.out.println(e4.isBlackinCheck());
		System.out.println(d5.isBlackinCheck());
		System.out.println(check.isBlackinCheck());
		System.out.println(recovery.isBlackinCheck());
		DrawingPanel panel = new DrawingPanel(800,800);
		Graphics g = panel.getGraphics();
		check.drawBoard(g, 800);
		try {
		    Thread.sleep(1000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		recovery.drawBoard(g, 800);

	}

}
