package org.farbelog;

import static org.farbelog.core.ASCIColors.*;

import java.util.HashMap;

import org.farbelog.core.ColorLoggerFactory;
import org.farbelog.core.HasCollor;
import org.farbelog.experiments.Get2Interfaces;
import org.farbelog.experiments.Godzilla;
import org.farbelog.loggers.SupporedLogger;
import org.slf4j.Logger;

import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 */

public class App {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(App.class);

	private static final SupporedLogger.ColorLoggerSlf4j LOG = ColorLoggerFactory.wrapLogger(logger, RED);

	//private static final ColorLoggerSlf4j LOG = ColorLoggerFactory.type(SLF4J).color(RED).getLogger(App.class);

	//private static final ColorLoggerSlf4j LOG = ColorLoggerFactory.getLogger(LOG);


/*
    private static final SupporedLogger.ColorLoggerLog4j2 LOG = ColorLoggerFactory.type(LOG4J2)
            //.color(RED)
            .getLogger(App.class);*/

	public static void main(String[] args) {

		Class<? super Object> superclass = Object.class.getSuperclass();

		String aaa = String.format("\u001b[33m%s\u001b[0m", "AAA");
		System.out.println(aaa);

		LOG.info("hi 1");
		LOG.color(GREEN).info("hi 2");
		LOG.color(BLUE).info("hi 3");
		LOG.color(YELLOW).info("hi 4");

		new App().dodo();

	}

	public void dodo() {
		LOG.color(YELLOW).info("hi 5");
	}
}
