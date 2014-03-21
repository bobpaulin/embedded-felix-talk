package edu.umich.soar.gridmap2d.config;

import java.util.HashMap;
import java.util.Map;

import edu.umich.soar.gridmap2d.CognitiveArchitecture;
import edu.umich.soar.gridmap2d.Game;
import edu.umich.soar.gridmap2d.Gridmap2D;
import edu.umich.soar.gridmap2d.Names;
import edu.umich.soar.gridmap2d.world.World;
import edu.umich.soar.modules.services.SoarGameConfig;
import edu.umich.soar.modules.services.WorldFactory;


public class SimConfig implements GameConfig {	
	
	public static SimConfig newInstance(SoarGameConfig config) {
		return new SimConfig(config);
	}
	
	private static class Keys {
		private static final String last_productions = "last_productions";
		private static final String window_position_x = "window_position.x";
		private static final String window_position_y = "window_position.y";
	}
	
	private Game game;
	
	private WorldFactory worldFactory;
	
	private GeneralConfig generalConfig;
	private SoarConfig soarConfig;
	private TerminalsConfig terminalsConfig;
	
	private GameConfig gameConfig;
	
	private Map<String, PlayerConfig> playerConfigs = new HashMap<String, PlayerConfig>();
	private Map<String, ClientConfig> clientConfigs = new HashMap<String, ClientConfig>();;
	
	private SimConfig(SoarGameConfig config)
	{
		generalConfig = config.getGeneralConfig();
		soarConfig = config.getSoarConfig();
		terminalsConfig = config.getTerminalsConfig();
		
		try {
			game = Game.valueOf(generalConfig.game.toUpperCase());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			StringBuilder sb = new StringBuilder();
			sb.append("Unknown game type: ");
			sb.append(generalConfig.game);
			sb.append("\nKnown game types:");
			for (Game gameType : Game.values()) {
				sb.append(" ");
				sb.append(gameType.id());
			}
			Gridmap2D.control.errorPopUp(sb.toString());
			throw new IllegalArgumentException(sb.toString(), e);
		}
		
		worldFactory = config.getWorldFactory();
		
		gameConfig = config.getGameConfig();
		clientConfigs.put(Names.kDebuggerClient, config.getClientConfig());
		
	}
	
	public boolean hasSeed() {
		return generalConfig.seed != 0;
		//return config.hasKey("general.seed");
	}
	
	public Game game() {
		return game;
	}
	
	public World createWorld(CognitiveArchitecture cogArch)
	{
		return worldFactory.createWorld(cogArch);
	}
	
	public WorldFactory getWorldFactory()
	{
		return worldFactory;
	}
	
	public GeneralConfig generalConfig() {
		return generalConfig;
	}
	
	public SoarConfig soarConfig() {
		return soarConfig;
	}
	
	public TerminalsConfig terminalsConfig() {
		return terminalsConfig;
	}
	
	public EatersConfig eatersConfig() {
		return (EatersConfig)gameConfig;
	}
	
	public TankSoarConfig tanksoarConfig() {
		return (TankSoarConfig)gameConfig;
	}
	
	public TaxiConfig taxiConfig() {
		return (TaxiConfig)gameConfig;
	}
	
	public Map<String, PlayerConfig> playerConfigs() {
		return playerConfigs;
	}
	
	public Map<String, ClientConfig> clientConfigs() {
		return clientConfigs;
	}
	
	public void saveLastProductions(String productionsPath) {
		String game_specific_key = game.id() + "." + Keys.last_productions;
		Gridmap2D.preferences.put(game_specific_key, productionsPath);
	}
	
	public String getLastProductions() {
		String game_specific_key = game.id() + "." + Keys.last_productions;
		return Gridmap2D.preferences.get(game_specific_key, null);
	}
	
	public void saveWindowPosition(int [] xy) {
		Gridmap2D.preferences.putInt(Keys.window_position_x, xy[0]);
		Gridmap2D.preferences.putInt(Keys.window_position_y, xy[1]);
	}
	
	public int [] getWindowPosition() {
		int [] xy = new int[] { 0, 0 };
		xy[0] = Gridmap2D.preferences.getInt(Keys.window_position_x, xy[0]);
		xy[1] = Gridmap2D.preferences.getInt(Keys.window_position_y, xy[1]);
		return xy;
	}
	
	public String title() {
		return gameConfig.title();
	}
}
