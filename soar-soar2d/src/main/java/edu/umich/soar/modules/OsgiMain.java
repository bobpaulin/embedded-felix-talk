package edu.umich.soar.modules;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.FelixConstants;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;

public class OsgiMain {

	private HostActivator m_activator = null;
	private Felix m_felix = null;

	public OsgiMain() {
		// Create a configuration property map.
		Map configMap = new HashMap();
		// Export the host provided service interface package.
		configMap.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA,
				"edu.umich.soar,edu.umich.soar.gridmap2d.config,edu.umich.soar.modules.services,edu.umich.soar.gridmap2d,edu.umich.soar.gridmap2d.world,edu.umich.soar.gridmap2d.visuals,org.eclipse.swt.widgets,org.eclipse.swt.graphics; version=0.0.1");

		// Create host activator;
		m_activator = new HostActivator();
		List list = new ArrayList();
		list.add(m_activator);
		configMap.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP, list);

		File[] files = new File("bundles").listFiles();
		List jars = new ArrayList();
		if (files != null) {
			Arrays.sort(files);
			for (int i = 0; i < files.length; i++)
				if (files[i].getName().toLowerCase().endsWith(".jar"))
					jars.add(files[i]);
		}

		try {

			// Now create an instance of the framework with
			// our configuration properties.
			m_felix = new Felix(configMap);
			// Now start Felix instance.
			m_felix.start();

			// Create, configure, and start an OSGi framework instance
			// using the ServiceLoader to get a factory.
			List bundleList = new ArrayList();

			// Install bundle JAR files and remember the bundle objects.
			BundleContext ctxt = m_activator.getContext();
			for (int i = 0; i < jars.size(); i++) {
				Bundle b = ctxt.installBundle(((File) jars.get(i)).toURI()
						.toString());
				bundleList.add(b);
			}
			// Start all installed bundles.
			for (int i = 0; i < bundleList.size(); i++) {
				((Bundle) bundleList.get(i)).start();
			}
		} catch (Exception ex) {
			System.err.println("Could not create framework: " + ex);
			ex.printStackTrace();
		}

	}
	
	public <S> S getService(Class<S> serviceClass)
    {
        ServiceReference<S> ref = m_activator.getContext().getServiceReference(serviceClass);
        return m_activator.getContext().getService(ref);
    }

	public static OsgiMain getInstance() {
		return OsgiMainHolder.INSTANCE;
	}

	private static class OsgiMainHolder {
		private static final OsgiMain INSTANCE = new OsgiMain();
	}
}
