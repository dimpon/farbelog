package org.farbelog;



import static org.farbelog.core.ASCIColors.*;
import static org.farbelog.loggers.SupporedLogger.Log4j2Supported.*;
import static org.farbelog.loggers.SupporedLogger.Slf4jSupported.*;

import org.farbelog.core.ColorLoggerFactory;
import org.farbelog.loggers.SupporedLogger;

import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 *
 */

public class App 
{



private static final ColorLoggerSlf4j LOG = ColorLoggerFactory.type(SLF4J)
        //.color(RED)
        .getLogger(App.class);


/*
    private static final SupporedLogger.ColorLoggerLog4j2 LOG = ColorLoggerFactory.type(LOG4J2)
            //.color(RED)
            .getLogger(App.class);*/

    public static void main( String[] args )
    {



        String aaa = String.format("\u001b[33m%s\u001b[0m", "AAA");
        System.out.println(aaa);



        LOG.info("hi 1");
        LOG.color(GREEN).info("hi 2");
        LOG.color(BLUE).info("hi 3");
        LOG.color(YELLOW).info("hi 4");

    }
}
