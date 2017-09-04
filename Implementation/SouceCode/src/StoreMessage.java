/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import yao.Utils;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author darshan
 */
@WebServlet(urlPatterns = {"/StoreMessage"})
public class StoreMessage extends HttpServlet {

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
        long time = System.currentTimeMillis();
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StoreMessage</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h4>Keywords Storing at AWS Cloud ...</h4>");
            System.out.println("From the Store Message");
            String keyWords[] = null;
            try {
                int noMsg = Integer.parseInt(request.getParameter("noMsg"));
                if (noMsg > 0) {
                    String message[] = new String[noMsg];
                    keyWords = new String[noMsg];
                    for (int i = 0; i < noMsg; i++) {
                        message[i] = request.getParameter("message" + i);
                    }
                    //Get Garbled Circuit Private Key for decryption
                    byte[] keyBytes = Base64.decodeBase64(request.getParameter("key"));
                    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
                    KeyFactory kf = KeyFactory.getInstance("RSA");
                    PrivateKey garbledPrivateKey = kf.generatePrivate(spec);
                    for (int i = 0; i < noMsg; i++) {
                        byte[] cipher = Base64.decodeBase64(message[i]);
                        byte dectoken[] = Utils.RSAdecrypt(cipher, garbledPrivateKey);
                        System.out.println("Garbled decrypted=" + Utils.getHex(dectoken));
                        keyWords[i] = Utils.getHex(dectoken);
                    }
                }
            } catch (Exception ex) {
                out.println("<h4><font color=\"red\">There is some problem in Garbled Decryption</font></h4>");
                System.out.println("Exception :" + ex);
            }
            try {
                if (keyWords != null) {
                    //Store Data at AWS Cloud
                    String tableName = "encryptedKeywords";
                    AWSCredentials credentials = new BasicAWSCredentials("AKIAJUYRPNFZFOMK332A", "nm4y2sCDr38UquKoKoGK5iKJ7stpA5AbGQsy6kEg");
                    AmazonDynamoDBClient dynamoDB = new AmazonDynamoDBClient(credentials);

                    // Describe our new table
                    DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName);
                    TableDescription tableDescription = dynamoDB.describeTable(describeTableRequest).getTable();
                    System.out.println("Table Description: " + tableDescription);
                    
                    //Add Item
                    for (String keyWord : keyWords) {
                        try {
                            // Add an item
                            Map<String, AttributeValue> item = newItem(keyWord);
                            PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
                            PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
                            System.out.println("Result: " + putItemResult);
                        } catch (Exception ex) {
                            System.out.println("Problem in Storing Message :" + keyWord);
                        }
                    }
                }
                out.println("<h4><font color=\"green\">Keywords Stored Successfully</font></h4>");
                time = System.currentTimeMillis() - time;
                double sec = (double) time / 1000.0;
                out.println("<h4><font color=\"blue\">Total Time : " + sec + " (seconds)</font></h4>");
            } catch (Exception ex) {
                out.println("<h4><font color=\"red\">There is some problem in Storing the data</font></h4>");
                System.out.println("Exception :" + ex);
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
