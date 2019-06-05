package org.farbelog.loggers;

import org.farbelog.core.HasCollor;

public interface Log4jSupport {
    interface Logger extends HasCollor<org.apache.logging.log4j.Logger>, org.apache.logging.log4j.Logger {
    }
}
