package edu.umich.soar.modules.services;

import java.util.List;

import edu.umich.soar.gridmap2d.CognitiveArchitecture;
import edu.umich.soar.gridmap2d.config.ClientConfig;
import edu.umich.soar.gridmap2d.config.GameConfig;
import edu.umich.soar.gridmap2d.config.GeneralConfig;
import edu.umich.soar.gridmap2d.config.PlayerConfig;
import edu.umich.soar.gridmap2d.config.SoarConfig;
import edu.umich.soar.gridmap2d.config.TerminalsConfig;
import edu.umich.soar.gridmap2d.world.World;

public interface SoarGameConfig {
	
	public String getConfigName();
	public GameConfig getGameConfig();
	public List<PlayerConfig> getPlayerConfigList();
	public GeneralConfig getGeneralConfig();
	public TerminalsConfig getTerminalsConfig();
	public SoarConfig getSoarConfig();
	public ClientConfig getClientConfig();
	public WorldFactory getWorldFactory();
	
}