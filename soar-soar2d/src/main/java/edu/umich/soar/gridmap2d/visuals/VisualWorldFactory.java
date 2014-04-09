package edu.umich.soar.gridmap2d.visuals;

import org.eclipse.swt.widgets.Composite;

public interface VisualWorldFactory {
	
	public VisualWorld createVisualWorld(Composite parent, int style, int cellSize);

}