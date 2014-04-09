package edu.umich.soar.gridmap2d.config;

import java.util.List;

public interface SoarGameConfig {
	
	public String getConfigName();
	public GameConfig getGameConfig();
	public List<PlayerConfig> getPlayerConfigList();
	public GeneralConfig getGeneralConfig();
	public TerminalsConfig getTerminalsConfig();
	public SoarConfig getSoarConfig();
	public ClientConfig getClientConfig();	
	public String getWorldName();
	public String getVisualWorldName();
}