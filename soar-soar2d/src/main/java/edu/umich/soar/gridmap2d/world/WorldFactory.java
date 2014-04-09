package edu.umich.soar.gridmap2d.world;

import edu.umich.soar.gridmap2d.CognitiveArchitecture;

public interface WorldFactory {
	public World createWorld(CognitiveArchitecture cogArch);
}
