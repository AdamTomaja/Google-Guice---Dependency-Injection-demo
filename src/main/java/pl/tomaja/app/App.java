package pl.tomaja.app;

import com.google.inject.Guice;
import com.google.inject.Injector;

import pl.tomaja.service.RouterService;

/**
 * @author Adam Tomaja
 */
public class App {

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new RouterModule());
		RouterService router = injector.getInstance(RouterService.class);
		
		router.route(10, "Hello world!");
		router.route(25, "Hello world!");
		router.route(150, "Hello world!");
		router.route(300, "Hello world!");
		router.route(600, "Hello world!");
		router.route(1200, "Hello world!");
		router.route(9999, "Hello world!");

	}

}
