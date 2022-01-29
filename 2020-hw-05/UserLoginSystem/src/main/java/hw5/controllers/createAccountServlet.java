package hw5.controllers;

import hw5.model.AccountManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//@WebServlet(name = "createAccountServlet", urlPatterns = {"java/hw5/controllers/createAccountServlet"})
public class createAccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        ServletContext sc =  httpServletRequest.getServletContext();
        AccountManager accountManager = (AccountManager) sc.getAttribute("accountManager");
        String userName  = httpServletRequest.getParameter("username");
        String password  = httpServletRequest.getParameter("password");
        RequestDispatcher rd = httpServletRequest.getRequestDispatcher("accountWelcome.jsp");
        if(accountManager.createAccount(userName,password)){
                rd.forward(httpServletRequest,httpServletResponse);
        }else{
                rd = httpServletRequest.getRequestDispatcher("createAccountFail.jsp");
                rd.forward(httpServletRequest,httpServletResponse);
        }
    }
}
