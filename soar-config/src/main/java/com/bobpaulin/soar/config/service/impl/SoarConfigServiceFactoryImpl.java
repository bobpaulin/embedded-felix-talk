package com.bobpaulin.soar.config.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;

import edu.umich.soar.modules.services.SoarConfigFactoryService;
import edu.umich.soar.modules.services.SoarGameConfig;

@Component(metatype = true,immediate=true)
@Service
@Reference(name = "SoarConfigServiceProvider", referenceInterface = SoarGameConfig.class, cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC)
public class SoarConfigServiceFactoryImpl implements SoarConfigFactoryService {
	
	@Property("tanksoar")
	private static final String DEFAULT_CONFIG = "default_config";
	
	private List<SoarGameConfig> gameConfigProviderList = new ArrayList<SoarGameConfig>();
	
	private SoarGameConfig[] gameConfigProviders;
	
	private String defaultConfig;
	
	protected void activate(ComponentContext context) {
		this.defaultConfig = (String)context.getProperties().get(DEFAULT_CONFIG);
	}
	
	protected void bindSoarConfigServiceProvider(SoarGameConfig gameConfigProvider) {
        synchronized (this.gameConfigProviderList) {
            this.gameConfigProviderList.add(gameConfigProvider);
            this.gameConfigProviders = null;
        }
    }

    protected void unbindSoarConfigServiceProvider(SoarGameConfig gameConfigProvider) {
        synchronized (this.gameConfigProviderList) {
            this.gameConfigProviderList.remove(gameConfigProvider);
            this.gameConfigProviders = null;
        }
    }
    
    private SoarGameConfig[] getSoarGameConfigProviders() {
    	SoarGameConfig[] list = this.gameConfigProviders;

        if (list == null) {
            synchronized (this.gameConfigProviderList) {
                this.gameConfigProviders = this.gameConfigProviderList.toArray(new SoarGameConfig[this.gameConfigProviderList.size()]);
                list = this.gameConfigProviders;
            }
        }

        return list;
    }
	
	public SoarGameConfig getDefaultConfig()
	{
		return getConfig(this.defaultConfig);
	}
	
	
	public SoarGameConfig getConfig(String configName) {
		
		SoarGameConfig result = null;
		
		SoarGameConfig[] serviceProviders = getSoarGameConfigProviders();
		
		if(serviceProviders != null)
		{
			for(SoarGameConfig currentService: serviceProviders)
			{
				if(currentService.getConfigName().equals(configName))
				{
					result = currentService;
				}
			}
		}
		
		if(result == null)
		{
			throw new RuntimeException("No Config Found");
		}
		
		return result;
	}

}
