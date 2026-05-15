package com.fashionstore.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // This runs when Tomcat starts — loads DB config from web.xml
        DBConnection.init(sce.getServletContext());
        System.out.println("✅ App initialized — DB config loaded from web.xml");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("App shutting down.");
    }
}
