/**
 * Created on 2007-8-8
 * Created by Sunteya
 */
package com.sunteya.flyer.acegi.web;

import org.acegisecurity.ConfigAttributeDefinition;

/**
 * @author Sunteya
 *
 */
public class PathEntryHolder {
    private ConfigAttributeDefinition configAttributeDefinition;
    private String antPath;

    public PathEntryHolder(String antPath, ConfigAttributeDefinition attr) {
        this.antPath = antPath;
        this.configAttributeDefinition = attr;
    }

    protected PathEntryHolder() {
        throw new IllegalArgumentException("Cannot use default constructor");
    }

    public String getAntPath() {
        return antPath;
    }

    public ConfigAttributeDefinition getConfigAttributeDefinition() {
        return configAttributeDefinition;
    }
}
