package pl.tomaja.model;

import pl.tomaja.service.LogService;

/**
 * @author Adam Tomaja
 */
public class EndPoint {

	private final int ip;
	private final String name;
	
	public EndPoint(int ip, String name) {
		this.ip = ip;
		this.name = name;
	}

	public int getIp() {
		return ip;
	}

	public String getName() {
		return name;
	}

	public void receive(String message) {
		// Do something with this message
	}
}
