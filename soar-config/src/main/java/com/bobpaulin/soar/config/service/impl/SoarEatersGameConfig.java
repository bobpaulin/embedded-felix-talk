package com.bobpaulin.soar.config.service.impl;

import java.util.Dictionary;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.PropertyUnbounded;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.component.ComponentContext;

import com.bobpaulin.soar.config.service.AbstractSoarGameConfig;

import edu.umich.soar.gridmap2d.CognitiveArchitecture;
import edu.umich.soar.gridmap2d.config.EatersConfig;
import edu.umich.soar.gridmap2d.config.GameConfig;
import edu.umich.soar.gridmap2d.config.SoarGameConfig;
import edu.umich.soar.gridmap2d.visuals.EatersVisualWorld;
import edu.umich.soar.gridmap2d.visuals.VisualWorld;
import edu.umich.soar.gridmap2d.world.EatersWorld;
import edu.umich.soar.gridmap2d.world.World;

@Component(immediate=true,metatype=true)
@Service
@Properties({
    @Property(name = AbstractSoarGameConfig.CYCLE_TIME_SLICE, doubleValue = 50),
    @Property(name = AbstractSoarGameConfig.DEFAULT_POINTS, intValue=0),
	@Property(name = AbstractSoarGameConfig.GAME, value="eaters"),
	@Property(name = AbstractSoarGameConfig.MAP, value="config/maps/eaters/random-walls.txt"),
	@Property(name = AbstractSoarGameConfig.HEADLESS,boolValue=false),
	@Property(name = AbstractSoarGameConfig.PREFERENCES_FILE, value="preferences"),
	@Property(name = AbstractSoarGameConfig.RUNS, intValue=0),
	@Property(name = AbstractSoarGameConfig.SEED, intValue=0),
	@Property(name = AbstractSoarGameConfig.FORCE_HUMAN, boolValue=false),
	//Soar Config
	@Property(name = AbstractSoarGameConfig.MAX_MEMORY_USAGE, intValue=-1),
	@Property(name = AbstractSoarGameConfig.PORT, intValue=12121),
	@Property(name = AbstractSoarGameConfig.SPAWN_DEBUGGERS, boolValue=true),
	@Property(name = AbstractSoarGameConfig.SOAR_PRINT, boolValue=false),
	@Property(name = AbstractSoarGameConfig.OPTIMIZED, boolValue=false),
	//Player
	@Property(name = AbstractSoarGameConfig.ACTIVE_PLAYERS, value = ""),
	@Property(name = AbstractSoarGameConfig.PLAYER_ATTRIBUTES, unbounded=PropertyUnbounded.ARRAY),
	//Terminals
	@Property(name = AbstractSoarGameConfig.MAX_UPDATES, intValue=0),
	@Property(name = AbstractSoarGameConfig.AGENT_COMMAND, boolValue=false),
	@Property(name = AbstractSoarGameConfig.POINTS_REMAINING, boolValue=false),
	@Property(name = AbstractSoarGameConfig.WINNING_SCORE, intValue=50),
	@Property(name = AbstractSoarGameConfig.FOOD_REMAINING, boolValue=true),
	@Property(name = AbstractSoarGameConfig.UNOPENED_BOXES, boolValue=false),
	@Property(name = AbstractSoarGameConfig.FUEL_REMAINING, boolValue=false),
	@Property(name = AbstractSoarGameConfig.PASSENGER_DELIVERED, boolValue=false),
	@Property(name = AbstractSoarGameConfig.PASSANGER_PICK_UP, boolValue=false),
	//Client
	@Property(name = AbstractSoarGameConfig.TIMEOUT, intValue=15),
	@Property(name = AbstractSoarGameConfig.AFTER, boolValue=true)
})
public class SoarEatersGameConfig extends AbstractSoarGameConfig implements SoarGameConfig {
	
	//Eaters Specific
	@Property(intValue=2)
	private static final String VISION = "vision";
	@Property(intValue=-5)
	private static final String WALL_PENALTY = "wall_penalty";
	@Property(intValue=-5)
	private static final String JUMP_PENALTY = "jump_penalty";
	@Property(doubleValue=0.25)
	private static final String LOW_PROBABILITY = "low_probability";
	@Property(doubleValue=0.75)
	private static final String HIGH_PROBABILITY = "high_probability";
	
	private EatersConfig eatersConfig;

	public void activate(ComponentContext context)
	{
		Dictionary<?, ?> properties = context.getProperties();
		processProperties(properties);
		this.eatersConfig = new EatersConfig();
		
		this.eatersConfig.high_probability = (Double)properties.get(HIGH_PROBABILITY);
		this.eatersConfig.jump_penalty = (Integer)properties.get(JUMP_PENALTY);
		this.eatersConfig.low_probability = (Double)properties.get(LOW_PROBABILITY);
		this.eatersConfig.vision = (Integer)properties.get(VISION);
		this.eatersConfig.wall_penalty = (Integer)properties.get(WALL_PENALTY);
	}
	public String getConfigName() {
		return "eaters";
	}
	
	public GameConfig getGameConfig() {
		return this.eatersConfig;
	}
	
	@Override
	public String getVisualWorldName() {
		return getConfigName();
	}
	
	@Override
	public String getWorldName() {
		return getConfigName();
	}
}
