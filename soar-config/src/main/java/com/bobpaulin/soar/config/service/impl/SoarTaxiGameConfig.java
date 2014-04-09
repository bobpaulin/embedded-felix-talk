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
import edu.umich.soar.gridmap2d.config.GameConfig;
import edu.umich.soar.gridmap2d.config.SoarGameConfig;
import edu.umich.soar.gridmap2d.config.TaxiConfig;
import edu.umich.soar.gridmap2d.visuals.TaxiVisualWorld;
import edu.umich.soar.gridmap2d.visuals.VisualWorld;
import edu.umich.soar.gridmap2d.world.TaxiWorld;
import edu.umich.soar.gridmap2d.world.World;

@Component(immediate=true,metatype=true)
@Service
@Properties({
    @Property(name = AbstractSoarGameConfig.CYCLE_TIME_SLICE, doubleValue = 50),
    @Property(name = AbstractSoarGameConfig.DEFAULT_POINTS, intValue=0),
	@Property(name = AbstractSoarGameConfig.GAME, value="tanksoar"),
	@Property(name = AbstractSoarGameConfig.MAP, value="config/maps/tanksoar/default.txt"),
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
	@Property(name = AbstractSoarGameConfig.FOOD_REMAINING, boolValue=false),
	@Property(name = AbstractSoarGameConfig.UNOPENED_BOXES, boolValue=false),
	@Property(name = AbstractSoarGameConfig.FUEL_REMAINING, boolValue=false),
	@Property(name = AbstractSoarGameConfig.PASSENGER_DELIVERED, boolValue=false),
	@Property(name = AbstractSoarGameConfig.PASSANGER_PICK_UP, boolValue=false),
	//Client
	@Property(name = AbstractSoarGameConfig.TIMEOUT, intValue=15),
	@Property(name = AbstractSoarGameConfig.AFTER, boolValue=true)
})
public class SoarTaxiGameConfig extends AbstractSoarGameConfig implements SoarGameConfig {
	//Taxi Specific
	@Property(boolValue=false)
	private static final String DISABLE_FUEL = "disable_fuel";
	@Property(intValue=5)
	private static final String FUEL_STARTING_MINIMUM = "fuel_starting_minimum";
	@Property(intValue=12)
	private static final String FUEL_STARTING_MAXIMUM = "fuel_starting_maximum";
	@Property(intValue=14)
	private static final String FUEL_MAXIMUM = "fuel_maximum";
	
	private TaxiConfig taxiConfig;

	public void activate(ComponentContext context)
	{
		Dictionary<?, ?> properties = context.getProperties();
		processProperties(properties);
		this.taxiConfig = new TaxiConfig();
		
		this.taxiConfig.disable_fuel = (Boolean)properties.get(DISABLE_FUEL);
		this.taxiConfig.fuel_maximum = (Integer)properties.get(FUEL_MAXIMUM);
		this.taxiConfig.fuel_starting_maximum = (Integer)properties.get(FUEL_STARTING_MAXIMUM);
		this.taxiConfig.fuel_starting_minimum = (Integer)properties.get(FUEL_STARTING_MINIMUM);

	}
	public String getConfigName() {
		return "taxi";
	}
	
	public GameConfig getGameConfig() {
		return this.taxiConfig;
	}
	
	@Override
	public String getWorldName() {
		return getConfigName();
	}
	
	@Override
	public String getVisualWorldName() {
		return getConfigName();
	}
}
