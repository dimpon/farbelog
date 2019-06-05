package org.farbelog;



import static org.farbelog.core.ASCIColors.*;

import org.farbelog.core.ColorLoggerFactory;
import org.farbelog.loggers.SupporedLogger;
import org.slf4j.Logger;

import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 *
 */
@Slf4j
public class App 
{


private static final SupporedLogger.ColorLoggerSlf4j LOG = ColorLoggerFactory.type(SupporedLogger.SLF4J).color(RED).getLogger(App.class);

    public static void main( String[] args )
    {



        String aaa = String.format("\u001b[33m%s\u001b[0m", "AAA");
        System.out.println(aaa);

        log.info(aaa);

        LOG.info("hi 1");
        LOG.color(GREEN).info("hi 2");
        LOG.color(BLUE).info("hi 3");

    }
}
