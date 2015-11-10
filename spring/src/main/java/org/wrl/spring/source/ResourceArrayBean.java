package org.wrl.spring.source;

import org.springframework.core.io.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-11-10 14:33
 */
public class ResourceArrayBean {
    private Resource[] resources;

    public Resource[] getResources() {
        return resources;
    }

    public void setResources(Resource[] resources) {
        this.resources = resources;
    }
}
