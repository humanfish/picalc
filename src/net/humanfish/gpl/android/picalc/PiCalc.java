package net.humanfish.gpl.android.picalc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PiCalc extends Activity implements OnClickListener
{
	private Button calcBut;
	private Button resetBut;
	
	private TextView piLabel;
	
	private long totalTime;
	
	private int runs;
	
	private int cycles;
	private long totalCycles;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        totalTime = 0;
        runs = 0;
        cycles = 0;
        
        setContentView(R.layout.main);
        
        calcBut = (Button)findViewById(R.id.calculate);
        calcBut.setOnClickListener(this);
        
        resetBut = (Button)findViewById(R.id.reset);
        resetBut.setOnClickListener(this);
        
        piLabel = (TextView)findViewById(R.id.pi);
    }
    
    public double pi(int i, int j)
	{
    	cycles++;
		double pi = 4.0;
		int denom = -3;
		
		// i >= 0 and j >= 1
		if (i < 0 || j < 1) return 0;
		
		if (i == 0)
			while ((Math.abs(denom) - 1)/2 < j)
			{
				pi += 4.0/denom;
				
				if (denom < 1) denom -= 2;
				else denom += 2;
				
				denom *= -1;
			}
		
		else pi = (pi(i-1,j) + pi(i-1,j+1))/2;
		
		return pi;
	}

	@Override
	public void onClick(View v)
	{
		if (v.getId() == R.id.calculate)
		{
			cycles = 0;
			
			final long start = System.currentTimeMillis();
			double pi = pi(16,16);
			final long end = System.currentTimeMillis();
			
			totalTime += end-start;
			totalCycles += cycles;
			runs++;
			
			piLabel.setText(pi + "\n" + (end-start) + getString(R.string.thisCalc) +
					(double)cycles/(end-start)*1000 + " " + getString(R.string.thisCycles) +
					(double)totalTime/runs + getString(R.string.averageCalc) +
					(double)totalCycles/totalTime*1000 + " " + getString(R.string.averageCycles));
		}
		else if (v.getId() == R.id.reset)
		{
			totalTime = 0;
			runs = 0;
			cycles = 0;
			totalCycles = 0;
			
			piLabel.setText("");
		}
     }
}