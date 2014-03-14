package com.bobpaulin.soar.alttank.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.PropertyUnbounded;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.component.ComponentContext;

import edu.umich.soar.gridmap2d.CognitiveArchitecture;
import edu.umich.soar.gridmap2d.config.ClientConfig;
import edu.umich.soar.gridmap2d.config.GameConfig;
import edu.umich.soar.gridmap2d.config.GeneralConfig;
import edu.umich.soar.gridmap2d.config.PlayerConfig;
import edu.umich.soar.gridmap2d.config.SoarConfig;
import edu.umich.soar.gridmap2d.config.TankSoarConfig;
import edu.umich.soar.gridmap2d.config.TerminalsConfig;
import edu.umich.soar.gridmap2d.visuals.TankSoarVisualWorld;
import edu.umich.soar.gridmap2d.visuals.VisualWorld;
import edu.umich.soar.gridmap2d.world.TankSoarWorld;
import edu.umich.soar.gridmap2d.world.World;
import edu.umich.soar.modules.services.SoarGameConfig;
import edu.umich.soar.modules.services.WorldFactory;

@Component(immediate=true,metatype=true)
@Service
public class AltTankConfig implements SoarGameConfig {
	//General
		@Property(doubleValue=50)
		private static final String CYCLE_TIME_SLICE = "cycle_time_slice";
		@Property(intValue=0)
		private static final String DEFAULT_POINTS = "default_points";
		@Property("tanksoar")
		private static final String GAME = "game";
		@Property("config/maps/tanksoar/default.txt")
		private static final String MAP = "map";
		@Property(boolValue=false)
		private static final String HEADLESS = "headless";
		@Property("preferences")
		private static final String PREFERENCES_FILE = "preferences_file";
		@Property(intValue=0)
		private static final String RUNS = "runs";
		@Property(intValue=0)
		private static final String SEED = "seed";
		@Property(boolValue=false)
		private static final String FORCE_HUMAN = "force_human";
		
		//Soar Config
		@Property(intValue=-1)
		private static final String MAX_MEMORY_USAGE = "max_memory_usage";
		@Property(intValue=12121)
		private static final String PORT = "port";
		//@Property("")
		//private static final String REMOTE = "remote";
		@Property(boolValue=true)
		private static final String SPAWN_DEBUGGERS = "spawn_debuggers";
		@Property(boolValue=false)
		private static final String SOAR_PRINT = "soar_print";
		@Property(boolValue=false)
		private static final String OPTIMIZED = "optimized";
		
		//Player
		@Property("")
		private static final String ACTIVE_PLAYERS = "active_players";
		@Property(unbounded=PropertyUnbounded.ARRAY)
		private static final String PLAYER_ATTRIBUTES = "player_attributes";
		
		//Terminals
		@Property(intValue=0)
		private static final String MAX_UPDATES = "max_updates";
		@Property(boolValue=false)
		private static final String AGENT_COMMAND = "agent_command";
		@Property(boolValue=false)
		private static final String POINTS_REMAINING = "points_remaining";
		@Property(intValue=50)
		private static final String WINNING_SCORE = "winning_score";
		@Property(boolValue=false)
		private static final String FOOD_REMAINING = "food_remaining";
		@Property(boolValue=false)
		private static final String UNOPENED_BOXES = "unopened_boxes";
		@Property(boolValue=false)
		private static final String FUEL_REMAINING = "fuel_remaining";
		@Property(boolValue=false)
		private static final String PASSENGER_DELIVERED = "passenger_delivered";
		@Property(boolValue=false)
		private static final String PASSANGER_PICK_UP = "passenger_pick_up";
		
		//Client
		//@Property("")
		//private static final String COMMAND = "command";
		@Property(intValue=15)
		private static final String TIMEOUT = "timeout";
		@Property(boolValue=true)
		private static final String AFTER = "after";
		
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
		
		private GeneralConfig generalConfig;
		
		private SoarConfig soarConfig;
		
		private TerminalsConfig terminalsConfig;
		
		private ClientConfig clientConfig;
		
		private List<PlayerConfig> playerConfigList;
		
		private ComponentContext componentContext;
		
		protected void activate(ComponentContext context) {
			
			this.componentContext = context;
			this.tankSoarConfig = new TankSoarConfig();
			this.generalConfig = new GeneralConfig();
			this.soarConfig = new SoarConfig();
			this.clientConfig = new ClientConfig();
			this.playerConfigList = new ArrayList<PlayerConfig>();
			this.terminalsConfig = new TerminalsConfig();
			
			final Dictionary<?, ?> properties = context.getProperties();
			
			//General
			this.generalConfig.cycle_time_slice = (Double)properties.get(CYCLE_TIME_SLICE);
			this.generalConfig.default_points = (Integer)properties.get(DEFAULT_POINTS);
			this.generalConfig.force_human = (Boolean) properties.get(FORCE_HUMAN);
			this.generalConfig.game = (String) properties.get(GAME);
			this.generalConfig.headless = (Boolean) properties.get(HEADLESS);
			this.generalConfig.map = (String) properties.get(MAP);
			this.generalConfig.preferences_file = (String) properties.get(PREFERENCES_FILE);
			this.generalConfig.runs = (Integer)	properties.get(RUNS);
			this.generalConfig.seed = (Integer) properties.get(SEED);
			
			//Soar
			this.soarConfig.max_memory_usage = (Integer) properties.get(MAX_MEMORY_USAGE);
			this.soarConfig.optimized = (Boolean) properties.get(OPTIMIZED);
			this.soarConfig.port = (Integer) properties.get(PORT);
			//this.soarConfig.remote = (String) properties.get(REMOTE);
			this.soarConfig.soar_print = (Boolean) properties.get(SOAR_PRINT);
			this.soarConfig.spawn_debuggers = (Boolean) properties.get(SPAWN_DEBUGGERS);
			
			//Player
			String playerListCSV = (String) properties.get(ACTIVE_PLAYERS);
			String[] playerAttributes = (String[]) properties.get(PLAYER_ATTRIBUTES);
			if(playerListCSV != null && !playerListCSV.isEmpty())
			{
				String[] playerList = playerListCSV.split(",");
				for(String playerName: playerList)
				{
					PlayerConfig currentPlayerConfig = new PlayerConfig();
					currentPlayerConfig.name = playerName;
					
					if(playerAttributes != null)
					{
						for(String currentPlayerAttribute: playerAttributes)
						{
							if(currentPlayerAttribute.startsWith("players."+ playerName))
							{
								try{
									String[] playerKeyValue = currentPlayerAttribute.replace("players." + playerName + ".", "").split("=");
									
									BeanUtils.setProperty(currentPlayerConfig, playerKeyValue[0], playerKeyValue[1]);
						
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
								
							}
						}
					}
					
					this.playerConfigList.add(currentPlayerConfig);
				}
			}
			
			//Terminal
			this.terminalsConfig.agent_command = (Boolean) properties.get(AGENT_COMMAND);
			this.terminalsConfig.food_remaining = (Boolean) properties.get(FOOD_REMAINING);
			this.terminalsConfig.fuel_remaining = (Boolean) properties.get(FUEL_REMAINING);
			this.terminalsConfig.max_updates = (Integer) properties.get(MAX_UPDATES);
			this.terminalsConfig.passenger_delivered = (Boolean) properties.get(PASSENGER_DELIVERED);
			this.terminalsConfig.passenger_pick_up = (Boolean) properties.get(PASSANGER_PICK_UP);
			this.terminalsConfig.points_remaining = (Boolean) properties.get(POINTS_REMAINING);
			this.terminalsConfig.unopened_boxes = (Boolean) properties.get(UNOPENED_BOXES);
			this.terminalsConfig.winning_score = (Integer) properties.get(WINNING_SCORE);
			
			//Client
			this.clientConfig.after = (Boolean) properties.get(AFTER);
			//this.clientConfig.command = (String) properties.get(COMMAND);
			this.clientConfig.timeout = (Integer) properties.get(TIMEOUT);
			
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
			return "alttanksoar";
		}
		
		public GameConfig getGameConfig() {
			return this.tankSoarConfig;
		}
		
		public GeneralConfig getGeneralConfig() {
			return this.generalConfig;
		}
		
		public List<PlayerConfig> getPlayerConfigList() {
			return this.playerConfigList;
		}
		
		public SoarConfig getSoarConfig() {
			return this.soarConfig;
		}
		
		public TerminalsConfig getTerminalsConfig() {
			return this.terminalsConfig;
		}
		
		public ClientConfig getClientConfig() {
			return this.clientConfig;
		}
		
	public WorldFactory getWorldFactory() {
		return new WorldFactory() {
			
			public World createWorld(CognitiveArchitecture cogArch) {
				
				return new TankSoarWorld(tankSoarConfig.max_missile_packs, cogArch);
			}
			public VisualWorld createVisualWorld(Composite parent, int style, int cellSize) {
				return new AltTankSoarVisualWorld(componentContext.getBundleContext(), parent, style, cellSize);
			}
			
		};
	}
	

}
