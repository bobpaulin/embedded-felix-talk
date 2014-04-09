package com.bobpaulin.soar.alttank.visuals.service.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.component.ComponentContext;

import edu.umich.soar.gridmap2d.visuals.VisualWorld;
import edu.umich.soar.gridmap2d.visuals.VisualWorldFactory;

@Component(metatype=true, immediate=true)
@Service
public class AltTankVisualWorldFactory implements VisualWorldFactory{
	
	@Property("alttanksoar")
	private static final String VISUAL_WORLD_NAME = "visualWorldName";
	
	private ComponentContext componentContext;
	
	protected void activate(ComponentContext context) {
		
		this.componentContext = context;
	}

	@Override
	public VisualWorld createVisualWorld(Composite parent, int style, int cellSize) {
		return new AltTankSoarVisualWorld(componentContext.getBundleContext(), parent, style, cellSize);
	}
}
