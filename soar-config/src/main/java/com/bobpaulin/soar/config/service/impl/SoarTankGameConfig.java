package com.bobpaulin.soar.config.service.impl;

import java.util.Dictionary;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.PropertyUnbounded;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;

import com.bobpaulin.soar.config.service.AbstractSoarGameConfig;

import edu.umich.soar.gridmap2d.config.GameConfig;
import edu.umich.soar.gridmap2d.config.SoarGameConfig;
import edu.umich.soar.gridmap2d.config.TankSoarConfig;

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
public class SoarTankGameConfig extends AbstractSoarGameConfig implements SoarGameConfig {
	
	//Tank Specific
	@Property(intValue=15)
	private static final String MAX_MISSILES = "max_missiles";
	@Property(intValue=1000)
	private static final String MAX_ENERGY = "max_energy";
	@Property(intValue=1000)
	private static final String MAX_HEALTH = "max_health";
	@Property(intValue=-100)
	private static final String COLLISION_PENALTY = "collision_penalty";
	@Property(intValue=3)
	private static final String MAX_MISSILE_PACKS = "max_missile_packs";
	@Property(intValue=5)
	private static final String MISSILE_PACK_RESPAWN_CHANCE = "missile_pack_respawn_chance";
	@Property(intValue=-20)
	private static final String SHIELD_ENERGY_USAGE = "shield_energy_usage";
	@Property(intValue=2)
	private static final String MISSILE_HIT_AWARD = "missile_hit_award";
	@Property(intValue=-1)
	private static final String MISSILE_HIT_PENALTY = "missile_hit_penalty";
	@Property(intValue=3)
	private static final String FRAG_AWARD = "frag_award";
	@Property(intValue=-2)
	private static final String FRAG_PENALTY = "frag_penalty";
	@Property(intValue=7)
	private static final String MAX_SOUND_DISTANCE = "max_sound_distance";
	@Property(intValue=100)
	private static final String MISSILE_RESET_THRESHOLD = "missile_reset_threshold";
	
	private TankSoarConfig tankSoarConfig;
	
	protected void activate(ComponentContext context) {
		
		
		
		this.tankSoarConfig = new TankSoarConfig();
		
		final Dictionary<?, ?> properties = context.getProperties();
		
		processProperties(properties);
		
		//Tank
		this.tankSoarConfig.collision_penalty = (Integer)properties.get(COLLISION_PENALTY);
		this.tankSoarConfig.frag_award = (Integer) properties.get(FRAG_AWARD);
		this.tankSoarConfig.frag_penalty = (Integer)properties.get(FRAG_PENALTY);
		this.tankSoarConfig.max_energy = (Integer)properties.get(MAX_ENERGY);
		this.tankSoarConfig.max_health = (Integer)properties.get(MAX_HEALTH);
		this.tankSoarConfig.max_missile_packs = (Integer)properties.get(MAX_MISSILE_PACKS);
		this.tankSoarConfig.max_missiles = (Integer) properties.get(MAX_MISSILES);
		this.tankSoarConfig.max_sound_distance = (Integer) properties.get(MAX_SOUND_DISTANCE);
		this.tankSoarConfig.missile_hit_award = (Integer) properties.get(MISSILE_HIT_AWARD);
		this.tankSoarConfig.missile_hit_penalty = (Integer) properties.get(MISSILE_HIT_PENALTY);
		this.tankSoarConfig.missile_pack_respawn_chance  = (Integer) properties.get(MISSILE_PACK_RESPAWN_CHANCE);
		this.tankSoarConfig.missile_reset_threshold = (Integer) properties.get(MISSILE_RESET_THRESHOLD);
		this.tankSoarConfig.shield_energy_usage = (Integer) properties.get(SHIELD_ENERGY_USAGE);
	}
	
	public String getConfigName() {
		return "tanksoar";
	}
	
	public GameConfig getGameConfig() {
		return this.tankSoarConfig;
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
