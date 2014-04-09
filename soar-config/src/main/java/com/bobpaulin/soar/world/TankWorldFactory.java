package com.bobpaulin.soar.world;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

import edu.umich.soar.gridmap2d.CognitiveArchitecture;
import edu.umich.soar.gridmap2d.config.SoarConfigFactoryService;
import edu.umich.soar.gridmap2d.config.SoarGameConfig;
import edu.umich.soar.gridmap2d.config.TankSoarConfig;
import edu.umich.soar.gridmap2d.world.TankSoarWorld;
import edu.umich.soar.gridmap2d.world.World;
import edu.umich.soar.gridmap2d.world.WorldFactory;

@Component(metatype=true, immediate=true)
@Service
public class TankWorldFactory implements WorldFactory {
	
	@Reference
	private SoarConfigFactoryService soarConfigFactoryService;
	
	@Property("tanksoar")
	private static final String WORLD_NAME = "worldName";
	
	public World createWorld(CognitiveArchitecture cogArch) {
		SoarGameConfig config = soarConfigFactoryService.getConfig("tanksoar");
		TankSoarConfig tankSoarConfig = (TankSoarConfig)config.getGameConfig();
		return new TankSoarWorld(tankSoarConfig.max_missile_packs, cogArch);
	}
	
	public void setSoarConfigFactoryService(SoarConfigFactoryService soarConfigFactoryService)
	{
		this.soarConfigFactoryService = soarConfigFactoryService;
	}

}
