package pl.tomaja.service;

import com.google.common.base.Optional;

import pl.tomaja.model.EndPoint;
import pl.tomaja.service.impl.DatabaseException;

/**
 * @author Adam Tomaja
 */
public interface DatabaseService {
	
	void addEndPoint(EndPoint endPoint) throws DatabaseException;
	void remoteEndPoint(EndPoint endPoint) throws DatabaseException;
	Optional<EndPoint> findEndPoint(int ip);

}
