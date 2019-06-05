package org.farbelog.core;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ASCIColors implements ASCIColor {

    RED("\u001b[31m%s\u001b[0m"),
    GREEN("\u001b[32m%s\u001b[0m"),
    BLUE("\u001b[34m%s\u001b[0m");

    private String pattern;

    @Override
    public String pattern() {
        return pattern;
    }
}
