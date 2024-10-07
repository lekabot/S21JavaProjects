package edu.school21.impl;

import edu.school21.interfaces.Printer;
import edu.school21.interfaces.Renderer;

import java.time.LocalDateTime;

public class PrinterWithDateTimeImpl implements Printer {
    private final Renderer renderer;

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    public void print(String s) {
        renderer.print(LocalDateTime.now() + " " + s);
    }
}
