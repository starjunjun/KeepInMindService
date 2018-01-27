package com.cross.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Timer;

public class TimerLisenter implements ServletContextListener {


    Timer timer;
    public void contextInitialized(ServletContextEvent sce) {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new Task(), 5 * 1000, 12*60 * 60 * 1000);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        if (timer != null) {
            timer.cancel();
        }
    }
}
