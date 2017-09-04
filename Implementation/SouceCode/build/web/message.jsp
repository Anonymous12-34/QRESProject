

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Garbled Circuit TOR AWS-Cloud</title>
        <script type="text/javascript">
            window.onpageshow = function (event) {
                if (event.persisted) {
                    window.location.reload()
                }
            };
            window.onload = function () {
                document.getElementById("textbox").value = "";
            }
            function validateForm() {
                var x = document.getElementById("textbox").value.toString().trim();
                if (x == "") {
                    alert("Write Message and Click button");
                    return false;
                }
            }
        </script>
    </head>
    <body>
        <%--
        Message transfer to AWS Cloud using
        Onion Routing(TOR) and Garbled Circuit Encryption        
        --%>    
        <form name="message" action="sendMessage.jsp" method="post" onsubmit="return validateForm()">
            <h4>Write Message for Cloud Storage</h4>
            <input type="text" id="textbox" name="textbox" size="50"/>
            <br><br>
            <input type="submit" value="Send Message">
        </form>
    </body>
</html>
