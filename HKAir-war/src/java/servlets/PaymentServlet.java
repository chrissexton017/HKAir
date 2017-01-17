/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import businesslogic.FareBean;
import businesslogic.MailBean;
import businesslogic.PaymentBean;
import database.DBHandler;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lokesh
 */
public class PaymentServlet extends HttpServlet {

    @EJB
    private MailBean mailBean;

    @EJB
    private PaymentBean paymentBean;


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PaymentServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PaymentServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        /*
        Enumeration<String> params = request.getParameterNames();
        Map<String, String[]> map = request.getParameterMap();
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ResultsServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Params received:</h1>");
            while(params.hasMoreElements()) {
                String[] s = map.get(params.nextElement());
                for(String string: s) {
                    out.println("<h2>"+string+"</h2>");
                }   
            }
            out.println("</body>");
            out.println("</html>");
        }
        */
        
        int outID = Integer.parseInt(request.getParameter("outID"));
        int backID = Integer.parseInt(request.getParameter("backID"));
        String title = request.getParameter("title");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String dob = request.getParameter("dob");
        String email = request.getParameter("email");
        String nationality = request.getParameter("nationality");
        String chosenSeat = request.getParameter("chosenSeat");
        double total = Double.parseDouble(request.getParameter("total"));
        String cardNumber = request.getParameter("card-no");
        String security = request.getParameter("security");
        int month = Integer.parseInt(request.getParameter("month"));
        int year = Integer.parseInt(request.getParameter("year"));
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todaysDate = sdf.format(cal.getTime());
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        String expires = sdf.format(cal.getTime());
        
        String paymentResponse = paymentBean.debit(firstname, lastname, expires, security, email, total);
        if(paymentResponse.equalsIgnoreCase("Success")) {
            int pax_id = DBHandler.addPassenger(firstname, lastname, dob, nationality, 0, email, title);
            DBHandler.makeBooking(outID, pax_id, todaysDate, chosenSeat);
            DBHandler.makeBooking(backID, pax_id, todaysDate, chosenSeat);
            try{
                mailBean.sendMail(email, "a320");
            } catch(Exception ex) {
                ex.printStackTrace();
            }
            response.sendRedirect("confirmation.jsp");
        } else {
            response.sendRedirect("payment.jsp?error="+paymentResponse);
        }
        
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
}
