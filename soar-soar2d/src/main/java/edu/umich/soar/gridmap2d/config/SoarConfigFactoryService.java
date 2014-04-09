package edu.umich.soar.gridmap2d.config;

public interface SoarConfigFactoryService {
	public SoarGameConfig getConfig(String configName);
	
	public SoarGameConfig getDefaultConfig();
}
