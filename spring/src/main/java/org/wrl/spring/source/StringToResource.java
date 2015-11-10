package org.wrl.spring.source;

import org.springframework.core.io.Resource;

/**
 * Spring 提供了一个 PropertyEditor “ResourceEditor”用于在注入的字符串和 Resource 之间进行转换。因此可以使用注入方式注入 Resource
 * Created by wrl on 2015/11/9.
 */
public class StringToResource {
    private Resource resource;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
