package org.wrl.spring.source;

import org.springframework.core.io.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-11-10 14:28
 */
public class ResourceInjectBean {
    private Resource resource;

    public Resource getResource() {
        return resource;
    }
    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
