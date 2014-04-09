package com.bobpaulin.soar.alttank.visuals.service.impl;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.BundleContext;

import edu.umich.soar.gridmap2d.Direction;
import edu.umich.soar.gridmap2d.visuals.TankSoarVisualWorld;

public class AltTankSoarVisualWorld extends TankSoarVisualWorld {
	
	private static final Log LOGGER = LogFactory.getLog(AltTankSoarVisualWorld.class);
	
	public AltTankSoarVisualWorld(BundleContext bundleContext, Composite parent, int style, int cellSize) {
		super(parent, style, cellSize);
		try{
			tanks[Direction.SOUTH.ordinal()] = new Image(display, bundleContext.getBundle().getEntry("cat.gif").openStream());
			tanks[Direction.NORTH.ordinal()] = new Image(display, bundleContext.getBundle().getEntry("cat.gif").openStream());
			tanks[Direction.EAST.ordinal()] = new Image(display, bundleContext.getBundle().getEntry("cat.gif").openStream());
			tanks[Direction.WEST.ordinal()] = new Image(display, bundleContext.getBundle().getEntry("cat.gif").openStream());
		}catch( IOException e)
		{
			LOGGER.error("Cat Images not found.", e);
		}
	}

}
