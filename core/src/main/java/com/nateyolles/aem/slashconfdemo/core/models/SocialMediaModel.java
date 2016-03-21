/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.


wget --user admin --password pass http://localhost:4502/libs/cq/commons/install/com.adobe.granite.confmgr-1.0.0.jar
mvn install:install-file -Dfile=com.adobe.granite.confmgr-1.0.0.jar -DgroupId=com.adobe.granite -DartifactId=com.adobe.granite.confmgr -Dversion=1.0.0 -Dpackaging=jar


 */
package com.nateyolles.aem.slashconfdemo.core.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.settings.SlingSettingsService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.adobe.granite.confmgr.Conf;
import com.adobe.granite.confmgr.ConfMgr;
import com.day.cq.wcm.api.Page;

@Model(adaptables = SlingHttpServletRequest.class)
public class SocialMediaModel {


	  @SlingObject
	  private Resource currentResource;
	
	  @Inject
	  private Page currentPage;
	  
//    @Inject
//    private SlingSettingsService settings;
	  
	  @Inject
	  private ConfMgr confMgr;
	  
	  @Inject
	  private ResourceResolverFactory resolverFactory;

//    @Inject @Named("sling:resourceType") @Default(values="No resourceType")
//    protected String resourceType;

    private ValueMap facebookSettings;
    private ValueMap twitterSettings;

    @PostConstruct
    protected void init() {

    	Conf confFromService = confMgr.getConf(currentPage.adaptTo(Resource.class));
    	
    	
    	try {
    		Map<String, Object> serviceParams = new HashMap<String, Object>();
            serviceParams.put(ResourceResolverFactory.SUBSERVICE, "readService");
			ResourceResolver serviceResolver = resolverFactory.getServiceResourceResolver(serviceParams);
			
			Conf confFromResolver = confMgr.getConf(currentPage.adaptTo(Resource.class), serviceResolver);
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Conf conf = currentPage.adaptTo(Conf.class);

    	facebookSettings = conf.getItem("socialmedia/facebook");    	
    	twitterSettings = conf.getItem("socialmedia/twitter");
    	
    	List<ValueMap> facebookSettingsList = conf.getList("socialmedia/facebook");
    	
    	for (ValueMap properties : facebookSettingsList) {
    		properties.get("account", String.class);
    	}
    }

    public boolean isFacebook() {
    	return facebookSettings.get("enabled", false);
    }
    
    public boolean isTwitter() {
    	return twitterSettings.get("enabled", false);
    }
    
    public String getFacebookAccount() {
    	return facebookSettings.get("account", String.class);
    }
    
    public String getTwitterAccount() {
    	return twitterSettings.get("account", String.class);
    }
}
