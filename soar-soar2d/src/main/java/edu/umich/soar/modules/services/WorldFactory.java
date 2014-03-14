package edu.umich.soar.modules.services;

import org.eclipse.swt.widgets.Composite;
import edu.umich.soar.gridmap2d.CognitiveArchitecture;
import edu.umich.soar.gridmap2d.visuals.VisualWorld;
import edu.umich.soar.gridmap2d.world.World;

public interface WorldFactory {
	public World createWorld(CognitiveArchitecture cogArch);
	public VisualWorld createVisualWorld(Composite parent, int style, int cellSize);
}
