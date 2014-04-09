package com.bobpaulin.soar.visuals;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.swt.widgets.Composite;

import edu.umich.soar.gridmap2d.visuals.EatersVisualWorld;
import edu.umich.soar.gridmap2d.visuals.VisualWorld;
import edu.umich.soar.gridmap2d.visuals.VisualWorldFactory;

@Component(metatype=true, immediate=true)
@Service
public class EatersVisualWorldFactory implements VisualWorldFactory{

	@Property("eaters")
	private static final String VISUAL_WORLD_NAME = "visualWorldName";
	
	@Override
	public VisualWorld createVisualWorld(Composite parent, int style, int cellSize) {
		return new EatersVisualWorld(parent, style, cellSize);
	}

}
