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
 */
package com.nateyolles.aem.slashconfdemo.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.settings.SlingSettingsService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.adobe.granite.confmgr.Conf;
import com.day.cq.wcm.api.Page;

///@Model(adaptables=Resource.class)
@Model(adaptables = SlingHttpServletRequest.class)
public class HelloWorldModel {


	  @SlingObject
	  private Resource currentResource;
	
	  @Inject
	  private Page currentPage;
	  
    @Inject
    private SlingSettingsService settings;

    @Inject @Named("sling:resourceType") @Default(values="No resourceType")
    protected String resourceType;

    private String message;

    @PostConstruct
    protected void init() {
        message = "\tHello World!\n";
        message += "\tThis is instance: " + settings.getSlingId() + "\n";
        message += "\tResource type is: " + resourceType + "\n";
    }

    public String getMessage() {
    	Conf conf = currentPage.adaptTo(Conf.class);

    	ValueMap facebookSettings = conf.getItem("socialmedia/facebook");
    	boolean facebookEnabled = facebookSettings.get("enabled", false);
    	String facebookAccount = facebookSettings.get("account", String.class);
    	
    	ValueMap twitterSettings = conf.getItem("socialmedia/twitter");
    	boolean twitterEnabled = facebookSettings.get("enabled", false);
    	String twitterAccount = facebookSettings.get("account", String.class);
    	
        return message;
    }
}
