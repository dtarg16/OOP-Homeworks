package hw5.controllers;

import hw5.model.AccountManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

@WebListener
public class loginListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext sc =  servletContextEvent.getServletContext();
        AccountManager accountManager = new AccountManager();
        sc.setAttribute("accountManager",accountManager);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
