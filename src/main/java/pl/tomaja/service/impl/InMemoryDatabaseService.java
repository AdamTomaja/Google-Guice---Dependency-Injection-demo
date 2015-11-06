package pl.tomaja.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import com.google.common.base.Optional;

import pl.tomaja.model.EndPoint;
import pl.tomaja.service.DatabaseService;

/**
 * @author Adam Tomaja
 */
@Singleton
public class InMemoryDatabaseService implements DatabaseService {

	private final Map<Integer, EndPoint> endpoints = new HashMap<Integer, EndPoint>();
	
	public void addEndPoint(EndPoint endPoint) throws DatabaseException {
		final int newIp = endPoint.getIp();
		Object existing = endpoints.get(newIp);
		if(existing != null) {
			throw new DatabaseException(String.format("End point with ip: %d already exists!", newIp));
		}
		endpoints.put(newIp, endPoint);
	}

	public void remoteEndPoint(EndPoint endPoint) throws DatabaseException {
		final int ip = endPoint.getIp();
		Object existing = endpoints.get(ip);
		if(existing == null) {
			throw new DatabaseException(String.format("End point with ip: %d not found!", ip));
		}
		endpoints.remove(ip);
	}

	public Optional<EndPoint> findEndPoint(int ip) {
		EndPoint endPoint = endpoints.get(ip);
		return Optional.fromNullable(endPoint);
	}
}
