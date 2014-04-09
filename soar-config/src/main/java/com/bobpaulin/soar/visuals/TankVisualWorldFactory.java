package com.bobpaulin.soar.visuals;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.swt.widgets.Composite;

import edu.umich.soar.gridmap2d.visuals.TankSoarVisualWorld;
import edu.umich.soar.gridmap2d.visuals.VisualWorld;
import edu.umich.soar.gridmap2d.visuals.VisualWorldFactory;

@Component(metatype=true, immediate=true)
@Service
public class TankVisualWorldFactory implements VisualWorldFactory{
	
	@Property("tanksoar")
	private static final String VISUAL_WORLD_NAME = "visualWorldName";
	
	@Override
	public VisualWorld createVisualWorld(Composite parent, int style, int cellSize) {
		return new TankSoarVisualWorld(parent, style, cellSize);
	}
	
	

}
