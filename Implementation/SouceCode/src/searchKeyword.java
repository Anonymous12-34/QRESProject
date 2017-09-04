/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import yao.Utils;

/**
 *
 * @author darshan
 */
@WebServlet(urlPatterns = {"/searchKeyword"})
public class searchKeyword extends HttpServlet {

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
            long time = System.currentTimeMillis();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet searchKeyword</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h4>Searching Keywords from AWS Cloud ...</h4>");
            try {
                //Key Word Encryption
                String keyword = request.getParameter("keyword");
                System.out.println(keyword);
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(keyword.getBytes(StandardCharsets.UTF_8));
                String searchKeyword = Utils.getHex(hash);
                System.out.println(searchKeyword);

                //Intial table 
                String tableName = "encryptedKeywords";
                AWSCredentials credentials = new BasicAWSCredentials("AKIAJUYRPNFZFOMK332A", "nm4y2sCDr38UquKoKoGK5iKJ7stpA5AbGQsy6kEg");
                AmazonDynamoDBClient dynamoDB = new AmazonDynamoDBClient(credentials);

                // Describe our new table
                DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName);
                TableDescription tableDescription = dynamoDB.describeTable(describeTableRequest).getTable();
                System.out.println("Table Description: " + tableDescription);

                //Check Key word inside the Table
                if (dynamoDB.getItem(tableName, newItem(searchKeyword)).toString().contains(searchKeyword)) {
                    out.println("<h4><font color=\"green\">Keyword Found : " + keyword + "</font></h4>");
                    //Add keyword to table
                    Map<String, AttributeValue> item = newItem(keyword);
                    PutItemRequest putItemRequest = new PutItemRequest("searchKeywords", item);
                    PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
                    System.out.println("Result: " + putItemResult);
                } else {
                    out.println("<h4><font color=\"red\">Keyword not Found : " + keyword + "</font></h4>");
                }
                time = System.currentTimeMillis() - time;
                double sec = (double) time / 1000.0;
                out.println("<h4><font color=\"blue\">Searching Time : " + sec + " (seconds)</font></h4>");
            } catch (Exception ex) {
                System.out.println("Exception: " + ex);
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

    private static Map<String, AttributeValue> newItem(String name) {
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("keyword", new AttributeValue(name));
        return item;
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
        processRequest(request, response);
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
