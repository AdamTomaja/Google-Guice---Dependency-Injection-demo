package pl.tomaja.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import pl.tomaja.model.EndPoint;
import pl.tomaja.service.ExampleDataService;

/**
 * @author Adam Tomaja
 */
@Singleton
public class StaticExampleDataService implements ExampleDataService {

	private final List<EndPoint> data = new ArrayList<>();
	
	public StaticExampleDataService() {
		data.add(new EndPoint(10, "Endpoint 10 Google"));
		data.add(new EndPoint(25, "Endpoint 25 Microsoft"));
		data.add(new EndPoint(150, "Endpoint 150 Twitter"));
		data.add(new EndPoint(250, "Endpoint 250 Interia"));
		data.add(new EndPoint(300, "Endpoint 300 WP"));
		data.add(new EndPoint(600, "Endpoint 600 Oracle"));
		data.add(new EndPoint(1000, "Endpoint 1000 HackADay"));
		data.add(new EndPoint(1200, "Endpoint 1200 Kickstarter"));
	}
	
	@Override
	public List<EndPoint> getExampleData() {		
		return data;
	}

}
