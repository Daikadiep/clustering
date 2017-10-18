/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import Model.RssCrawler;
import com.sun.faces.config.ConfigureListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Diep
 */
public class Crawler extends HttpServlet {

    
    

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*try {
            ServletContextListener c = new ConfigureListener();
            System.load(getServletContext().getRealPath("/WEB-INF")+"/chilkat.dll");
            TimerTask crawler = new RssCrawler();
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(crawler, 0, 10*1000);
            Thread.sleep(10*1000);
            
        } catch (Exception ex) {
            ex.toString();
            response.sendRedirect(getServletContext().getContextPath()+"/index.jsp");
        }*/
        response.sendRedirect(getServletContext().getContextPath()+"/index.jsp");
    }

    
}
