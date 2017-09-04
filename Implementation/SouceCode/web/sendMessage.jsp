

<%@page import="java.nio.charset.StandardCharsets"%>
<%@page import="java.security.MessageDigest"%>
<%@page import="org.apache.tomcat.util.codec.binary.Base64"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.security.PublicKey"%>
<%@page import="java.security.PrivateKey"%>
<%@ page import="encryption.Encryption" %>
<%@ page import="yao.Utils" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Garbled Circuit TOR AWS-Cloud</title>
        <script type="text/javascript">
            window.onload = function () {
                document.forms['sendMessage'].submit();
            }
        </script>
    </head>
    <body>
        <form name="sendMessage" method="post" action="StoreMessage">
            <h4>Message Sending...</h4>
            <%
                String str = new String(request.getParameter("textbox"));
                System.out.println(str);
                int fixd = 8;
                Encryption en = new Encryption();
                PrivateKey garbledPrivateKey = en.getGarbledPrivateKey();
                PublicKey garbledPublicKey = en.getGarbledPublicKey();
                String key = Base64.encodeBase64String(garbledPrivateKey.getEncoded());
               //String to Tokens
                int noTokens = (str.length() / fixd) + 1;
                if (str.length() % fixd == 0) {
                    noTokens--;
                }
                if (noTokens > 0) {
                    out.print("<input type=\"hidden\" name=\"noMsg\" value=\"" + noTokens + "\">");
                    out.print("<input type=\"hidden\" name=\"key\" value=\"" + key + "\">");
                    String tokens[] = new String[noTokens];
                    for (int i = 0; i < noTokens; i++) {
                        if (i == noTokens - 1) {
                            tokens[i] = str.substring(i * fixd);
                        } else {
                            tokens[i] = str.substring(i * fixd, (i + 1) * fixd);
                        }
                        System.out.println(tokens[i]);
                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                        byte[] hash = digest.digest(tokens[i].getBytes(StandardCharsets.UTF_8));
                        System.out.println("Encrypted String=" + Utils.getHex(hash));
                        byte[] cipher = Utils.RSAencrypt(hash, garbledPublicKey);
                        System.out.println("Garbled encrypted=" + Utils.getHex(cipher));
                        String msgByte = Base64.encodeBase64String(cipher);
                        out.print("<input type=\"hidden\" name=\"message" + i + "\" value=\"" + msgByte + "\">");
                    }
                }
            %>
        </form>
    </body>
</html>
