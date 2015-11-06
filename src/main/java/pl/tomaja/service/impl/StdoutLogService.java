package pl.tomaja.service.impl;

import java.util.Date;

import javax.inject.Singleton;

import pl.tomaja.service.LogLevel;
import pl.tomaja.service.LogService;

/**
 * @author atomaja
 */
@Singleton
public class StdoutLogService implements LogService {

	@Override
	public void log(LogLevel level, String message) {
		System.out.println(String.format("[%s] %s: %s", level, new Date().toString(), message));
	}
}
