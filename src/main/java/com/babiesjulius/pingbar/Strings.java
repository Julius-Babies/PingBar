package com.babiesjulius.pingbar;

public class Strings {

    private final String locale;

    public Strings(String locale) {
        this.locale = locale;
    }

    public String property_set_to() {
        switch (locale) {
            case "de_DE":
                return "%s auf &s gesetzt";
            default:
                return "%s set to %s";
        }
    }

    public String please_wait() {
        switch (locale) {
            case "de_DE":
                return "Bitte warten";
            default:
                return "Please wait";
        }
    }

    public String calculating_ping() {
        switch (locale) {
            case "de_DE":
                return "Ping wird berechnet...";
            default:
                return "Calculating ping...";
        }
    }
}
