package edu.school21.impl;

import edu.school21.interfaces.PreProcessor;
import edu.school21.interfaces.Renderer;

public class RendererErrImpl implements Renderer {
    private final PreProcessor preProcessor;
    public RendererErrImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    public void print(String s) {
        System.err.println(preProcessor.process(s));
    }
}
