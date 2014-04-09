package com.bobpaulin.soar.config.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.umich.soar.gridmap2d.config.ClientConfig;
import edu.umich.soar.gridmap2d.config.GeneralConfig;
import edu.umich.soar.gridmap2d.config.PlayerConfig;
import edu.umich.soar.gridmap2d.config.SoarConfig;
import edu.umich.soar.gridmap2d.config.TerminalsConfig;
import edu.umich.soar.modules.OsgiMain;

public abstract class AbstractSoarGameConfig {
	
	private static final Log LOGGER = LogFactory.getLog(AbstractSoarGameConfig.class);

	// General
	public static final String CYCLE_TIME_SLICE = "cycle_time_slice";
	public static final String DEFAULT_POINTS = "default_points";
	public static final String GAME = "game";
	public static final String MAP = "map";
	public static final String HEADLESS = "headless";
	public static final String PREFERENCES_FILE = "preferences_file";
	public static final String RUNS = "runs";
	public static final String SEED = "seed";
	public static final String FORCE_HUMAN = "force_human";

	// Soar Config
	public static final String MAX_MEMORY_USAGE = "max_memory_usage";
	public static final String PORT = "port";
	// @Property("")
	// public static final String REMOTE = "remote";
	public static final String SPAWN_DEBUGGERS = "spawn_debuggers";
	public static final String SOAR_PRINT = "soar_print";
	public static final String OPTIMIZED = "optimized";

	// Player
	public static final String ACTIVE_PLAYERS = "active_players";
	public static final String PLAYER_ATTRIBUTES = "player_attributes";

	// Terminals
	public static final String MAX_UPDATES = "max_updates";
	public static final String AGENT_COMMAND = "agent_command";
	public static final String POINTS_REMAINING = "points_remaining";
	public static final String WINNING_SCORE = "winning_score";
	public static final String FOOD_REMAINING = "food_remaining";
	public static final String UNOPENED_BOXES = "unopened_boxes";
	public static final String FUEL_REMAINING = "fuel_remaining";
	public static final String PASSENGER_DELIVERED = "passenger_delivered";
	public static final String PASSANGER_PICK_UP = "passenger_pick_up";

	// Client
	public static final String TIMEOUT = "timeout";
	public static final String AFTER = "after";

	private GeneralConfig generalConfig;

	private SoarConfig soarConfig;

	private TerminalsConfig terminalsConfig;

	private ClientConfig clientConfig;

	private List<PlayerConfig> playerConfigList;

	public AbstractSoarGameConfig() {
		this.generalConfig = new GeneralConfig();
		this.soarConfig = new SoarConfig();
		this.terminalsConfig = new TerminalsConfig();
		this.clientConfig = new ClientConfig();
		this.playerConfigList = new ArrayList<PlayerConfig>();
	}
	
	public void processProperties(Dictionary<?, ?> properties)
	{
		//General
				getGeneralConfig().cycle_time_slice = (Double)properties.get(CYCLE_TIME_SLICE);
				getGeneralConfig().default_points = (Integer)properties.get(DEFAULT_POINTS);
				getGeneralConfig().force_human = (Boolean) properties.get(FORCE_HUMAN);
				getGeneralConfig().game = (String) properties.get(GAME);
				getGeneralConfig().headless = (Boolean) properties.get(HEADLESS);
				getGeneralConfig().map = (String) properties.get(MAP);
				getGeneralConfig().preferences_file = (String) properties.get(PREFERENCES_FILE);
				getGeneralConfig().runs = (Integer)	properties.get(RUNS);
				getGeneralConfig().seed = (Integer) properties.get(SEED);
				
				//Soar
				getSoarConfig().max_memory_usage = (Integer) properties.get(MAX_MEMORY_USAGE);
				getSoarConfig().optimized = (Boolean) properties.get(OPTIMIZED);
				getSoarConfig().port = (Integer) properties.get(PORT);
				//this.soarConfig.remote = (String) properties.get(REMOTE);
				getSoarConfig().soar_print = (Boolean) properties.get(SOAR_PRINT);
				getSoarConfig().spawn_debuggers = (Boolean) properties.get(SPAWN_DEBUGGERS);
				
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
										LOGGER.warn("Could not Set Property", e);
									} catch (IllegalAccessException e) {
										LOGGER.warn("Could not Set Property", e);
									} catch (InvocationTargetException e) {
										LOGGER.warn("Could not Set Property", e);
									}
									
								}
							}
						}
						
						getPlayerConfigList().add(currentPlayerConfig);
					}
				}
				
				//Terminal
				getTerminalsConfig().agent_command = (Boolean) properties.get(AGENT_COMMAND);
				getTerminalsConfig().food_remaining = (Boolean) properties.get(FOOD_REMAINING);
				getTerminalsConfig().fuel_remaining = (Boolean) properties.get(FUEL_REMAINING);
				getTerminalsConfig().max_updates = (Integer) properties.get(MAX_UPDATES);
				getTerminalsConfig().passenger_delivered = (Boolean) properties.get(PASSENGER_DELIVERED);
				getTerminalsConfig().passenger_pick_up = (Boolean) properties.get(PASSANGER_PICK_UP);
				getTerminalsConfig().points_remaining = (Boolean) properties.get(POINTS_REMAINING);
				getTerminalsConfig().unopened_boxes = (Boolean) properties.get(UNOPENED_BOXES);
				getTerminalsConfig().winning_score = (Integer) properties.get(WINNING_SCORE);
				
				//Client
				getClientConfig().after = (Boolean) properties.get(AFTER);
				//this.clientConfig.command = (String) properties.get(COMMAND);
				getClientConfig().timeout = (Integer) properties.get(TIMEOUT);
				
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

}
