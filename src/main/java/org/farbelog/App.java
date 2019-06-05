package org.farbelog;

import lombok.extern.slf4j.Slf4j;
import org.farbelog.core.ColorLoggerFactory;
import org.farbelog.loggers.Slf4jSupport.Logger;
import org.slf4j.LoggerFactory;

import static org.farbelog.core.ASCIColors.*;

/**
 * Hello world!
 *
 */
@Slf4j
public class App 
{
    private static final Logger LOG = ColorLoggerFactory.getLogger(Logger.class, LoggerFactory.getLogger(App.class),GREEN);

    public static void main( String[] args )
    {



        String aaa = String.format("\u001b[33m%s\u001b[0m", "AAA");
        System.out.println(aaa);

        log.info(aaa);

        LOG.info("hi 1");
        LOG.color(RED).info("hi 2");
        LOG.color(BLUE).info("hi 3");

    }
}
