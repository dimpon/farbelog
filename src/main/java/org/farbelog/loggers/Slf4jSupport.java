package org.farbelog.loggers;

import org.farbelog.core.HasCollor;

public interface Slf4jSupport {
    interface Logger extends HasCollor<org.slf4j.Logger>, org.slf4j.Logger {}
}
