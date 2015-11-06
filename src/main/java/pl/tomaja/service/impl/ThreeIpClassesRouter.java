package pl.tomaja.service.impl;

import com.google.common.base.Optional;
import com.google.inject.Inject;

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

	private static final int SECOND_CLASS_RANGE = 500;

	private static final int FIRST_CLASS_RANGE = 100;

	private DatabaseService class1Db;

	private DatabaseService class2Db;

	private DatabaseService class3Db;

	private ExampleDataService exampleDataService;

	private LogService logService;

	@Inject
	public ThreeIpClassesRouter(DatabaseService class1Db, DatabaseService class2Db, DatabaseService class3Db,
			ExampleDataService exampleDataService, LogService logService) {
		this.class1Db = class1Db;
		this.class2Db = class2Db;
		this.class3Db = class3Db;
		this.exampleDataService = exampleDataService;
		this.logService = logService;
		init();
	}

	private void init() {
		for (EndPoint endPoint : exampleDataService.getExampleData()) {
			int ip = endPoint.getIp();
			try {
				if (ip < FIRST_CLASS_RANGE) {
					class1Db.addEndPoint(endPoint);
				} else if (ip < SECOND_CLASS_RANGE) {
					class2Db.addEndPoint(endPoint);
				} else {
					class3Db.addEndPoint(endPoint);
				}
			} catch (DatabaseException e) {
				logService.log(LogLevel.ERROR, "Unable to add new endpoint: " + e.getMessage());
			}
		}

		logService.log(LogLevel.DEBUG, "Router initialized");
	}

	@Override
	public void route(int ip, String message) {
		if (ip < FIRST_CLASS_RANGE) {
			findEndPointEndSend(ip, message, class1Db, 1);
		} else if (ip < SECOND_CLASS_RANGE) {
			findEndPointEndSend(ip, message, class2Db, 2);
		} else {
			findEndPointEndSend(ip, message, class3Db, 3);
		}
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
}
