package edu.umich.soar.modules.services;

public interface SoarConfigFactoryService {
	public SoarGameConfig getConfig(String configName);
	
	public SoarGameConfig getDefaultConfig();
}
