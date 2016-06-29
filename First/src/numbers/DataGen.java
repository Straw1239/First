package numbers;

import static numbers.LongStates.addRandomTile;
import static numbers.LongStates.move;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import main.LongList;
import net.openhft.koloboke.collect.map.LongObjCursor;
import net.openhft.koloboke.collect.map.hash.HashLongObjMap;
import net.openhft.koloboke.collect.map.hash.HashLongObjMaps;

public class DataGen
{
	static File recordLocation = new File("G:\\Data\\2048D4Stats.dat");
	static HashLongObjMap<LongList> record = HashLongObjMaps.newUpdatableMap();
	
	static
	{
		
		try
		{
			FileInputStream fis = new FileInputStream(recordLocation);
			DataInputStream dis = new DataInputStream(new BufferedInputStream(fis));
			long entries = dis.readLong();
			System.out.println(entries);
			record = HashLongObjMaps.newUpdatableMap((int) entries);
			for(long i = 0; i < entries; i++)
			{
				long key = dis.readLong();
				long num = dis.readLong();
				LongList v = new LongList((int)num);
				for(long j = 0; j < num; j++)
				{
					v.add(dis.readLong());
				}
				record.put(key, v);
			}
			dis.close();
			fis.close();
		}
		catch(IOException e)
		{
			System.err.println("Unable to load records");
		}
	}
	
	static void save()
	{
		recordLocation.delete();
	
		try
		{	
			recordLocation.getParentFile().mkdirs();
			recordLocation.createNewFile();
			FileOutputStream fos = new FileOutputStream(recordLocation);
			DataOutputStream oos = new DataOutputStream(new BufferedOutputStream(fos));
			
			oos.writeLong(record.sizeAsLong());
			LongObjCursor<LongList> c = record.cursor();
			while(c.moveNext())
			{
				oos.writeLong(c.key());
				oos.writeLong(c.value().size());
				for(long i = 0; i < c.value().size(); i++)
				{
					oos.writeLong(c.value().get((int)i));
				}
			}
			oos.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	static final int DEPTH = 4;
	public static void main(String[] args)
	{
		long time = System.nanoTime();
		List<Future<?>> fs = new ArrayList<>();
		for(int k = 0; k < 4; k++)
		{
			fs.add(Utils.compute.submit(() -> 
			{
				for(int i = 0; i < 300; i++)
				{
					long state = 0;
					LongList data = new LongList();
					data.add(state);
					state = addRandomTile(state);
					state = addRandomTile(state);
					
					while(true)
					{
						data.add(state);
						int d = Expectimax.bestMove(state, DEPTH);
						if(d == -1) break;
						state = move(state, d);
						state = addRandomTile(state);
					}
					synchronized(record)
					{
						for(int j = 0; j < data.size(); j++)
						{
							long b = data.get(j);
							record.computeIfAbsent(b, x -> new LongList()).add(state);
						}
					}
				}
			}));
		}
		for(Future<?> f : fs)
			try
			{
				f.get();
			}
			catch (InterruptedException | ExecutionException e)
			{
				throw new RuntimeException(e);
			}
		System.out.println((System.nanoTime() - time) * 1.0 / 1_000_000_000);
		save();
		System.exit(0);
	}
	
	
	
	
}
