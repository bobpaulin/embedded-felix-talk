package com.bobpaulin.soar.alttank.service.impl;

import java.io.IOException;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.BundleContext;

import edu.umich.soar.gridmap2d.Direction;
import edu.umich.soar.gridmap2d.visuals.TankSoarVisualWorld;

public class AltTankSoarVisualWorld extends TankSoarVisualWorld {
	
	public AltTankSoarVisualWorld(BundleContext bundleContext, Composite parent, int style, int cellSize) {
		super(parent, style, cellSize);
		try{
			tanks[Direction.SOUTH.ordinal()] = new Image(display, bundleContext.getBundle().getEntry("cat.gif").openStream());
			tanks[Direction.NORTH.ordinal()] = new Image(display, bundleContext.getBundle().getEntry("cat.gif").openStream());
			tanks[Direction.EAST.ordinal()] = new Image(display, bundleContext.getBundle().getEntry("cat.gif").openStream());
			tanks[Direction.WEST.ordinal()] = new Image(display, bundleContext.getBundle().getEntry("cat.gif").openStream());
		}catch( IOException e)
		{
			e.printStackTrace();
		}
	}

}
