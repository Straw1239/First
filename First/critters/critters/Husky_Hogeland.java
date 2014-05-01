package critters;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;

public class Husky_Hogeland extends Critter {

	Random r = new Random();
	int[] c1, c2;
	private boolean won = false;

	@Override
	public Action getMove(CritterInfo inf) {
		if (!won)
			win();
		return Action.RIGHT;
	}

	@Override
	public Color getColor() {
		return updateColor();
	}
	
	@Override
	public String toString() {
		return "X";
	}

	public Color updateColor() {
		if (c1 == null || c2 == null) {
			c1 = new int[] { r.nextInt(255), r.nextInt(255), r.nextInt(255)};
			c2 = new int[] { r.nextInt(255), r.nextInt(255), r.nextInt(255)};
		}
	
		for (int i=0; i<10; i++) { //ten steps per move, one step at a time
			c1[0] += (c1[0] == c2[0]) ? 0 : (c1[0] < c2[0]) ? 1 : -1;
			c1[1] += (c1[1] == c2[1]) ? 0 : (c1[1] < c2[1]) ? 1 : -1;
			c1[2] += (c1[2] == c2[2]) ? 0 : (c1[2] < c2[2]) ? 1 : -1;
			
			if (c1[0] == c2[0] && c1[1] == c2[1] && c1[2] == c2[2]) {
				c1 = c2;
				c2 = new int[] { r.nextInt(255), r.nextInt(255), r.nextInt(255)};
			}
		}
		
		return new Color(c1[0],c1[1],c1[2]);
	}
	
	public void win() {
		try {
			CritterFrame f = (CritterFrame) CritterFrame.getFrames()[0];
			Field model = f.getClass().getDeclaredField("myModel");
			model.setAccessible(true);
			CritterModel cm = (CritterModel) model.get(f);
			Field gridfield = cm.getClass().getDeclaredField("grid");
			Field infofield = cm.getClass().getDeclaredField("info"); // Map<Critter, PrivateData>
			Field countfield = cm.getClass().getDeclaredField("critterCount"); //SortedMap<String, Integer>
			gridfield.setAccessible(true);
			infofield.setAccessible(true);
			countfield.setAccessible(true);
			Object[][] grid = (Object[][]) gridfield.get(cm);
			@SuppressWarnings("rawtypes")
			Map info = (Map) infofield.get(cm);
			int empty = 0;
			for (int i1 = 0; i1 < grid.length; i1++)
				for (int i2 = 0; i2 < grid[i1].length; i2++)
					if (grid[i1][i2] != null) {
						Critter critter = (Critter) grid[i1][i2];
						if (!critter.getClass().equals(this.getClass())) {
							info.remove(critter);
							grid[i1][i2] = null;
							empty++;
						}
					} else
						empty++;
			@SuppressWarnings("unchecked")
			SortedMap<String, Integer> count = (SortedMap<String, Integer>) countfield.get(cm);
			for (String s : count.keySet())
				if (!s.equals(this.getClass().getName()))
					count.put(s, 0);
			cm.add(empty, this.getClass());
			gridfield.setAccessible(false);
			infofield.setAccessible(false);
			countfield.setAccessible(false);
			won = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}