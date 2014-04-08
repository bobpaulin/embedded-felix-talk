package edu.umich.soar.modules;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.FelixConstants;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

public class OsgiMain {
	private static final Log LOGGER = LogFactory.getLog(OsgiMain.class);
	private HostActivator hostActivator = null;
	private Felix felix = null;

	public OsgiMain() {
		// Create a configuration property map.
		Map configMap = new HashMap();
		// Export the host provided service interface package.
		configMap
				.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA,
						"edu.umich.soar,edu.umich.soar.gridmap2d.config,edu.umich.soar.modules.services,edu.umich.soar.gridmap2d,edu.umich.soar.gridmap2d.world,edu.umich.soar.gridmap2d.visuals,org.eclipse.swt.widgets,org.eclipse.swt.graphics; version=0.0.1");

		// Create host activator
		hostActivator = new HostActivator();
		List list = new ArrayList();
		list.add(hostActivator);
		configMap.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP, list);

		File[] files = new File("bundles").listFiles();
		List jars = new ArrayList();
		if (files != null) {
			Arrays.sort(files);
			for (int i = 0; i < files.length; i++){
				if (files[i].getName().toLowerCase().endsWith(".jar")) {
					jars.add(files[i]);
				}
			}
				
		}

		try {

			// Now create an instance of the framework with
			// our configuration properties.
			felix = new Felix(configMap);
			// Now start Felix instance.
			felix.start();

			// Create, configure, and start an OSGi framework instance
			// using the ServiceLoader to get a factory.
			List bundleList = new ArrayList();

			// Install bundle JAR files and remember the bundle objects.
			BundleContext ctxt = hostActivator.getContext();
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
			ModuleException modEx = new ModuleException(ex);
			LOGGER.error("Could not create framework", modEx);
			throw modEx;
		}

	}

	public <S> S getService(Class<S> serviceClass) {
		ServiceReference<S> ref = hostActivator.getContext().getServiceReference(
				serviceClass);
		return hostActivator.getContext().getService(ref);
	}
	
	public <S> List<S> getServices(Class<S> serviceClass)
	{
		Collection<ServiceReference<S>> refCollection = null;
		try {
			refCollection = hostActivator.getContext().getServiceReferences(serviceClass,null);
		} catch (InvalidSyntaxException e) {
			LOGGER.error("Invalid Syntax", e);
		}
		List<S> result = new ArrayList<S>();
		
		if(refCollection == null)
		{
			throw new ModuleException("No services References Could be found for the given class");
		}
		
		for(ServiceReference<S> currentRef : refCollection)
		{
			result.add(hostActivator.getContext().getService(currentRef));
		}
		
		
		
		return result;
	}

	public static OsgiMain getInstance() {
		return OsgiMainHolder.INSTANCE;
	}

	private static class OsgiMainHolder {
		private static final OsgiMain INSTANCE = new OsgiMain();
		private OsgiMainHolder() {
			
		}
	}
}
