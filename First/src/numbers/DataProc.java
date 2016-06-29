package numbers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import main.LongList;
import net.openhft.koloboke.collect.map.LongObjCursor;

public class DataProc
{
	static short[] center = new short[1 << 16];
	static short[] corner = new short[1 << 16];
	static short[] inner = new short[1 << 16];
	static short[] outer = new short[1 << 16];
	public static void main(String[] args)
	{
		proc();
		System.out.println(estimateError());
	}
	public static void proc()
	{
		double[] center = new double[1 << 16];
		double[] corner = new double[1 << 16];
		double[] inner = new double[1 << 16];
		double[] outer = new double[1 << 16];
		int[] centerCounts = new int[1 << 16];
		int[] cornerCounts = new int[1 << 16];
		int[] innerCounts = new int[1 << 16];
		int[] outerCounts = new int[1 << 16];
		LongObjCursor<LongList> c = DataGen.record.cursor();
		double exp = 1;
		double exp2 = 1;
		while(c.moveNext())
		{
			long start = c.key();
			int value = LongStates.totalValue(start);
			LongList results = c.value();
			double valueGain = 0;
			for(int i = 0; i < results.size(); i++)
			{
				long result = results.get(i);
				valueGain += Math.pow(LongStates.totalValue(result) - value, exp2) ;
			}
			valueGain /= results.size();
			valueGain = Math.pow(valueGain, 1.0 / exp2);
				//System.out.println(valueGain);
				center[Short.toUnsignedInt(LongStates.center(start))] += Math.pow(valueGain, exp);
				centerCounts[Short.toUnsignedInt(LongStates.center(start))]++;
				corner[Short.toUnsignedInt(LongStates.ULCorner(start))] += Math.pow(valueGain, exp);
				corner[Short.toUnsignedInt(LongStates.URCorner(start))] += Math.pow(valueGain, exp);
				corner[Short.toUnsignedInt(LongStates.LLCorner(start))] += Math.pow(valueGain, exp);
				corner[Short.toUnsignedInt(LongStates.LRCorner(start))] += Math.pow(valueGain, exp);
				
				cornerCounts[Short.toUnsignedInt(LongStates.ULCorner(start))]++;
				cornerCounts[Short.toUnsignedInt(LongStates.URCorner(start))]++;
				cornerCounts[Short.toUnsignedInt(LongStates.LLCorner(start))]++;
				cornerCounts[Short.toUnsignedInt(LongStates.LRCorner(start))]++;
				
				inner[Short.toUnsignedInt(LongStates.rowAt(start, 1))] += Math.pow(valueGain, exp);
				inner[Short.toUnsignedInt(LongStates.rowAt(start, 2))] += Math.pow(valueGain, exp);
				inner[Short.toUnsignedInt(LongStates.columnAt(start, 1))] += Math.pow(valueGain, exp);
				inner[Short.toUnsignedInt(LongStates.columnAt(start, 2))] += Math.pow(valueGain, exp);
				
				innerCounts[Short.toUnsignedInt(LongStates.rowAt(start, 1))]++;
				innerCounts[Short.toUnsignedInt(LongStates.rowAt(start, 2))]++;
				innerCounts[Short.toUnsignedInt(LongStates.columnAt(start, 1))]++;
				innerCounts[Short.toUnsignedInt(LongStates.columnAt(start, 2))]++;
				
				outer[Short.toUnsignedInt(LongStates.rowAt(start, 0))] += Math.pow(valueGain, exp);
				outer[Short.toUnsignedInt(LongStates.rowAt(start, 3))] += Math.pow(valueGain, exp);
				outer[Short.toUnsignedInt(LongStates.columnAt(start, 0))] += Math.pow(valueGain, exp);
				outer[Short.toUnsignedInt(LongStates.columnAt(start, 3))] += Math.pow(valueGain, exp);
				
				outerCounts[Short.toUnsignedInt(LongStates.rowAt(start, 0))]++;
				outerCounts[Short.toUnsignedInt(LongStates.rowAt(start, 3))]++;
				outerCounts[Short.toUnsignedInt(LongStates.columnAt(start, 0))]++;
				outerCounts[Short.toUnsignedInt(LongStates.columnAt(start, 3))]++;
				
			
		}
		for(int i = 0; i < center.length; i++)
		{
			DataProc.center[i] = centerCounts[i] == 0 ? -1 : (short)Math.pow(center[i] / centerCounts[i], 1.0 / exp);
			DataProc.corner[i] = cornerCounts[i] == 0 ? -1 : (short)Math.pow(corner[i] / cornerCounts[i], 1.0 / exp);
			DataProc.inner[i] = innerCounts[i] == 0 ? -1 : (short)Math.pow(inner[i] / innerCounts[i], 1.0 / exp);
			DataProc.outer[i] = outerCounts[i] == 0 ? -1 : (short)Math.pow(outer[i] / outerCounts[i], 1.0 / exp);
		}
		
		saveData();
		
	}
	
	static double estimateError()
	{
		double totalError = 0;
		LongObjCursor<LongList> c = DataGen.record.cursor();
		while(c.moveNext())
		{
			int total = 0;
			
			long start = c.key();
			int sValue = LongStates.totalValue(start);
			LongList results = c.value();
			for(int i = 0; i < results.size(); i++)
			{
				total += (LongStates.totalValue(results.get(i)) - sValue);
			}
			double averageGain = 1.0 / results.size() * total;
			double predictedGain = Heuristics.scoreWithAdvancedTables(start);
			totalError += (averageGain - predictedGain) * (averageGain - predictedGain);
		}
		totalError /= DataGen.record.sizeAsLong();
		return Math.sqrt(totalError);
	}
	
	static File dataLocation = new File("G:\\Data\\tables.dat");
	private static void saveData()
	{
		try
		{
			DataOutputStream d = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dataLocation)));
			for(int i = 0; i < center.length; i++)
			{
				d.writeShort(center[i]);
				d.writeShort(corner[i]);
				d.writeShort(inner[i]);
				d.writeShort(outer[i]);
			}
			d.flush();
			d.close();
		}
		catch (IOException e)
		{
		
			throw new RuntimeException(e);
		}
		
	}
	
	public static void readData()
	{
		try
		{
			DataInputStream d = new DataInputStream(new BufferedInputStream(new FileInputStream(dataLocation)));
			for(int i = 0; i < center.length; i++)
			{
				center[i] = d.readShort();
				corner[i] = d.readShort();
				inner[i]  = d.readShort();
				outer[i]  = d.readShort();
			}
			d.close();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}
