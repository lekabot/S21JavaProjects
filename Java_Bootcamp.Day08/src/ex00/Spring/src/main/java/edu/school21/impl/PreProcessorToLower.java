package edu.school21.impl;

import edu.school21.interfaces.PreProcessor;

public class PreProcessorToLower implements PreProcessor {
    public String process(String s) {
        return s.toLowerCase();
    }
}
