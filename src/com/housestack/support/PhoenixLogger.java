package com.housestack.support;

import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author NARENDRA JADHAV
 */
public class PhoenixLogger {

    private static Logger logger;
    private static FileHandler handler;
    private static SimpleFormatter formate;

    static {
        try {
            logger = Logger.getLogger("HTM");
            handler = new FileHandler("HTMLog.log");
            logger.addHandler(handler);
            formate = new SimpleFormatter() {
                @Override
                public synchronized String format(LogRecord lr) {
                    return new Date() + " || " + lr.getLevel().getName() + " Message : " + lr.getMessage() + "\n";
                }

            };
            handler.setFormatter(formate);
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(PhoenixLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void info(String logdata) {
        logger.info(logdata);
    }

    public static void server(String logdata) {
        logger.severe(logdata);
    }

    public static void error(String sourceClass, String sourceMethod, Throwable t) {
        logger.throwing(sourceClass, sourceMethod, t);
    }

}
