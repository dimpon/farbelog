package org.farbelog.loggers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.logging.log4j.Logger;
import org.farbelog.core.HasCollor;

import lombok.Getter;
import lombok.experimental.Accessors;

public interface SupporedLogger<R extends HasCollor<L>, L> {

	Class<R> coloredLogger();
	Class<L> originalLogger();
	Function<Class<?>, L> originalLoggerFunction();



	public interface ColorLoggerSlf4j extends HasCollor<org.slf4j.Logger>, org.slf4j.Logger {
	}

	@Getter
	@Accessors(fluent = true)
	public class Slf4jSupported implements SupporedLogger<ColorLoggerSlf4j, org.slf4j.Logger>{

		public static final Slf4jSupported SLF4J = new Slf4jSupported();

		private final Class<ColorLoggerSlf4j> coloredLogger = ColorLoggerSlf4j.class;
		private final Class<org.slf4j.Logger> originalLogger = org.slf4j.Logger.class;
		private final  Function<Class<?>, org.slf4j.Logger> originalLoggerFunction = org.slf4j.LoggerFactory::getLogger;
	}


//////////////////////////////////////////////////



	public interface ColorLoggerLog4j2 extends HasCollor<org.apache.logging.log4j.Logger>, org.apache.logging.log4j.Logger {
	}

	public class Log4j2Supported implements SupporedLogger<ColorLoggerLog4j2, org.apache.logging.log4j.Logger>{

		public static final Log4j2Supported LOG4J2 = new Log4j2Supported();

		@Override
		public Class<ColorLoggerLog4j2> coloredLogger() {
			return ColorLoggerLog4j2.class;
		}

		@Override
		public Class<org.apache.logging.log4j.Logger> originalLogger() {
			return org.apache.logging.log4j.Logger.class;
		}

		@Override
		public Function<Class<?>, org.apache.logging.log4j.Logger> originalLoggerFunction() {
			return org.apache.logging.log4j.LogManager::getLogger;
		}
	}



	public final static Map<String, int[]> methods = new HashMap<String, int[]>() {{
		put("public abstract void org.slf4j.Logger.info(java.lang.String)", new int[]{0});
	}};

	//private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SupporedLogger.class); log4j
	//private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(SupporedLogger.class); log4j2
}

