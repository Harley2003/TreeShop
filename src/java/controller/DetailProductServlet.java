/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.DAO;
import java.util.*;
import model.*;

/**
 *
 * @author nguye
 */
public class DetailProductServlet extends HttpServlet {

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
            out.println("<title>Servlet DetailProductServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DetailProductServlet at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        DAO dao = DAO.INSTANCE;
        String productRaw = request.getParameter("product");
        String productCidParam = request.getParameter("cid");
        List<Categories> categories = dao.getCategory();
        request.setAttribute("categories", categories);
        int idP;
        int getProductCID;
        boolean isValidateP = false;
        boolean isValidateC = false;
        try {
            if (productRaw != null && !productRaw.isEmpty()
                    && productCidParam != null
                    && !productCidParam.isEmpty()) {
                idP = Integer.parseInt(productRaw);
                getProductCID = Integer.parseInt(productCidParam);
                List<Products> detailProduct = dao.getProductsByID(idP);
                List<Products> product = dao.getRandomProductsForCategory(getProductCID);

                // Cách để khi khách hàng lỡ nhập số linh tinh trên thanh URL     
                // chạy vòng lặp để lấy data về pid trùng thì trả về true
                for (Products pdetail : detailProduct) {
                    if (pdetail.getIdP() == idP) {
                        isValidateP = true;
                        break;
                    }
                }

                // chạy vòng lặp để lấy data về cid trùng thì trả về true
                for (Products pRan : product) {
                    if (pRan.getIdC().getIdC() == getProductCID) {
                        isValidateC = true;
                        break;
                    }
                }

                // nếu trả về true thì sẽ đưa ra sản phẩm của danh mục đó
                if (isValidateP && isValidateC) {
                    request.setAttribute("detailProduct", detailProduct);
                    request.setAttribute("sameProduct", product);
                    request.getRequestDispatcher("/view/DetailProduct.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/category");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
//        processRequest(request, response);

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
