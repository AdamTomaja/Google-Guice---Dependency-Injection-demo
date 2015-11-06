package pl.tomaja.app;

import com.google.inject.AbstractModule;

import pl.tomaja.service.DatabaseService;
import pl.tomaja.service.ExampleDataService;
import pl.tomaja.service.LogService;
import pl.tomaja.service.RouterService;
import pl.tomaja.service.impl.InMemoryDatabaseService;
import pl.tomaja.service.impl.StaticExampleDataService;
import pl.tomaja.service.impl.StdoutLogService;
import pl.tomaja.service.impl.ThreeIpClassesRouter;

/**
 * @author Adam Tomaja
 */
public class RouterModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(DatabaseService.class).to(InMemoryDatabaseService.class);
		bind(RouterService.class).to(ThreeIpClassesRouter.class);
		bind(ExampleDataService.class).to(StaticExampleDataService.class);
		bind(LogService.class).to(StdoutLogService.class);
	}
}
