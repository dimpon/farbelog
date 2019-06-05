package org.farbelog.loggers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.farbelog.core.HasCollor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Accessors(fluent = true)
public final class SupporedLogger<R extends HasCollor<L>, L, A> {

	public final static SupporedLogger<ColorLoggerSlf4j, org.slf4j.Logger, Class<?>> SLF4J
			= new SupporedLogger<>(ColorLoggerSlf4j.class, org.slf4j.Logger.class, org.slf4j.LoggerFactory::getLogger);

	public final static SupporedLogger<ColorLoggerLog4j2, org.apache.logging.log4j.Logger, Class<?>> LOG4J2
			= new SupporedLogger<>(ColorLoggerLog4j2.class, org.apache.logging.log4j.Logger.class, org.apache.logging.log4j.LogManager::getLogger);



	private final Class<R> coloredLogger;
	private final Class<L> originalLogger;
	private final Function<A, L> createOriginalLogger;

	public interface ColorLoggerSlf4j extends HasCollor<org.slf4j.Logger>, org.slf4j.Logger {
	}

	public interface ColorLoggerLog4j2 extends HasCollor<org.apache.logging.log4j.Logger>, org.apache.logging.log4j.Logger {
	}

	public final static Map<String, int[]> methods = new HashMap<String, int[]>() {{
		put("public abstract void org.slf4j.Logger.info(java.lang.String)", new int[]{0});
	}};

	//private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SupporedLogger.class); log4j
	//private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(SupporedLogger.class); log4j2
}

