package edu.school21.impl;

import edu.school21.interfaces.PreProcessor;
import edu.school21.interfaces.Renderer;

public class RendererStandardImpl implements Renderer {
    private final PreProcessor preProcessor;
    public RendererStandardImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    public void print(String s) {
        System.out.println(preProcessor.process(s));
    }
}
