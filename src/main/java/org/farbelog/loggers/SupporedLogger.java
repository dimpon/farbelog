package org.farbelog.loggers;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.farbelog.core.HasCollor;

public final class SupporedLogger {

	public Set<Class<?>> interfaces = new HashSet<>();

	public SupporedLogger() {

		load("org.farbelog.loggers.SupporedLogger$ColorLoggerSlf4j");
		load("org.farbelog.loggers.SupporedLogger$ColorLoggerLog4j2");
	}

	private void load(String className) {
		try {
			interfaces.add(this.getClass().getClassLoader().loadClass(className));
		} catch (java.lang.NoClassDefFoundError | ClassNotFoundException e) {
			//e.printStackTrace();
		}
	}

	public Class<?> findInterface(Class<?>[] parents) {

		Supplier<Stream<Class<?>>> su = () -> Arrays.stream(parents);
		Function<Class<?>[], Stream<Class<?>>> fu = Arrays::stream;

		Optional<Class<?>> first = interfaces.stream()
				.filter(candidate -> fu.apply(candidate.getInterfaces()).anyMatch(aClass -> su.get().anyMatch(aClass1 -> aClass1 == aClass)))
				.findFirst();

		return first.orElseThrow(() -> new IllegalArgumentException("Interface not found"));

	}

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

