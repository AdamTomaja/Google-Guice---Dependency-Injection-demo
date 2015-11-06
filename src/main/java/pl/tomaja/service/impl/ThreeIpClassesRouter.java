package pl.tomaja.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.google.common.base.Optional;

import pl.tomaja.model.EndPoint;
import pl.tomaja.service.DatabaseService;
import pl.tomaja.service.ExampleDataService;
import pl.tomaja.service.LogLevel;
import pl.tomaja.service.LogService;
import pl.tomaja.service.RouterService;

/**
 * @author Adam Tomaja
 */
public class ThreeIpClassesRouter implements RouterService {

	private static final int 
		THIRD_CLASS_NUMBER = 3,
		SECOND_CLASS_NUMBER = 2,
		FIRST_CLASS_NUMBER = 1,
		SECOND_CLASS_RANGE = 500,
		FIRST_CLASS_RANGE = 100;

	private final Map<Integer, DatabaseService> databases = new HashMap<>();
	
	@Inject
	private LogService logService;

	@Inject
	public ThreeIpClassesRouter(DatabaseService class1Db, DatabaseService class2Db, DatabaseService class3Db) {
		databases.put(FIRST_CLASS_NUMBER, class1Db);
		databases.put(SECOND_CLASS_NUMBER, class2Db);
		databases.put(THIRD_CLASS_NUMBER, class3Db);
	}
	
	@Inject
	private void init(ExampleDataService exampleDataService) {
		for (EndPoint endPoint : exampleDataService.getExampleData()) {
			try {
				DatabaseService service = getDatabase(determineClass(endPoint.getIp()));
				service.addEndPoint(endPoint);
			} catch (DatabaseException e) {
				logService.log(LogLevel.ERROR, "Unable to add new endpoint: " + e.getMessage());
			}
		}

		logService.log(LogLevel.DEBUG, "Router initialized");
	}

	@Override
	public void route(int ip, String message) {
		int classNumber = determineClass(ip);
		DatabaseService service = getDatabase(classNumber);
		findEndPointEndSend(ip, message, service, classNumber);
	}
	
	private void findEndPointEndSend(int ip, String message, DatabaseService db, int classId) {
		Optional<EndPoint> endPoint = db.findEndPoint(ip);
		if (!endPoint.isPresent()) {
			logService.log(LogLevel.ERROR, String.format("End point with ip: %d not found in ip class: %d", ip, classId));
			return;
		}
		final EndPoint endPointValue = endPoint.get();
		endPointValue.receive(message);
		logService.log(LogLevel.INFO, String.format("Endpoint %s received the message: %s", endPointValue.getName(), message));
	}
	
	private int determineClass(int ip) {
		if(ip < FIRST_CLASS_RANGE) {
			return FIRST_CLASS_NUMBER;
		} else if(ip < SECOND_CLASS_RANGE) {
			return SECOND_CLASS_NUMBER;
		} else {
			return THIRD_CLASS_NUMBER;
		}
	}
	
	private DatabaseService getDatabase(int classNumber) {
		return databases.get(classNumber);
	}
}
