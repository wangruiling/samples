package org.wrl.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-06-09 15:23
 */
public class BillingModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(BillingService.class).to(BillingService.class).in(Scopes.SINGLETON);
	}
}
