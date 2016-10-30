package org.wrl.elasticsearch.natives.admin.cluster;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;
import org.wrl.elasticsearch.natives.AbstractApi;


public class UpdateSettingsTest extends AbstractApi {

	@Test
	public void test() {
		
		Map<String, Object> map = new HashMap();
		map.put("indices.ttl.interval", "10m");
		
		Client client = getClient();
		ClusterUpdateSettingsResponse response = client.admin().cluster()
			.prepareUpdateSettings()
				.setTransientSettings(map)
			.execute().actionGet();
		
		System.out.println(response.getTransientSettings().toDelimitedString(','));

	}

}
