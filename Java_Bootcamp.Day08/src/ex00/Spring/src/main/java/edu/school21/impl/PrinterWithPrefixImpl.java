package edu.school21.impl;

import edu.school21.interfaces.Printer;
import edu.school21.interfaces.Renderer;

public class PrinterWithPrefixImpl implements Printer {
    private final Renderer renderer;
    private String prefix;

    public PrinterWithPrefixImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void print(String s) {
        renderer.print(prefix + " " + s);
    }
}
