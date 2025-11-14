package dev.louisa.kata.infrastructure.adapter;

import dev.louisa.kata.domain.port.PrinterDriver;

public class ConsolePrinterDriver implements PrinterDriver {

    @Override
    public void sendToDevice(String content) {
        System.out.println(content);
    }
}
