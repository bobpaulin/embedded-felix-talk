package com.bobpaulin.soar.world;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;

import edu.umich.soar.gridmap2d.CognitiveArchitecture;
import edu.umich.soar.gridmap2d.world.EatersWorld;
import edu.umich.soar.gridmap2d.world.World;
import edu.umich.soar.gridmap2d.world.WorldFactory;

@Component(metatype=true, immediate=true)
@Service
public class EatersWorldFactory implements WorldFactory {

	@Property("eaters")
	private static final String WORLD_NAME = "worldName";
	
	@Override
	public World createWorld(CognitiveArchitecture cogArch) {
		
		return new EatersWorld(cogArch);
	}

}
