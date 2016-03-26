package com.nateyolles.aem.slashconfdemo.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;

import com.adobe.granite.confmgr.Conf;

import com.day.cq.wcm.api.Page;

@Model(adaptables = SlingHttpServletRequest.class)
public class SocialMediaModel {

    @Inject
    private Page currentPage;

    private ValueMap facebookSettings;
    private ValueMap twitterSettings;

    @PostConstruct
    protected void init() {
        Conf conf = currentPage.adaptTo(Conf.class);

        facebookSettings = conf.getItem("socialmedia/facebook");
        twitterSettings = conf.getItem("socialmedia/twitter");
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
