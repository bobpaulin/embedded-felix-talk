package edu.umich.soar.modules;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HostActivator implements BundleActivator {
	private BundleContext context = null;

	public void start(BundleContext context) {
		this.context = context;
	}

	public void stop(BundleContext context) {
		this.context = null;
	}

	public BundleContext getContext() {
		return this.context;
	}

}