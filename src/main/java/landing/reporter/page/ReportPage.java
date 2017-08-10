/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landing.reporter.page;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class ReportPage {
    
    String phplocation="http://10.11.1.180/obpmanager/php/process.php";
    
    String htmlContent1="<html lang=\"en\">\n" +
"<head>\n" +
"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
"  <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
"  <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>\n" +
"  <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n" +
"</head>\n" +
"<body>\n" +
"<form class=\"form-horizontal\" action= >\n" +
"  <br>\n" +
"  <div class=\"form-group\">\n" +
"    <label class=\"control-label col-sm-2\" for=\"usr\" >Name:</label>\n" +
"    <div class=\"col-sm-9\"> \n" +
"      <input type=\"text\" class=\"form-control\" id=\"idName\" placeholder=\"Enter your name\">\n" +
"    </div>\n" +
"     <div class=\"col-sm-1\"></div>\n" +
"  </div>\n" +
"  <div class=\"form-group\">\n" +
"    <label class=\"control-label col-sm-2\" for=\"email\" >Email:</label>\n" +
"    <div class=\"col-sm-9\">\n" +
"      <input type=\"email\" class=\"form-control\" id=\"idEmail\" placeholder=\"Enter your email\">\n" +
"    </div>\n" +
"    <div class=\"col-sm-1\"></div>\n" +
"  </div>\n" +
"   <div class=\"row \">\n" +
"     <div class=\"col-sm-2\"></div>\n" +
"  <div class=\"dropdown  col-sm-9\">\n" +
"  <button id=\"idLocButton\" class=\"btn btn-primary dropdown-toggle btn-block\" type=\"button \" data-toggle=\"dropdown\">Location\n" +
"  <span class=\"caret\"></span></button>\n" +
"  <ul id=\"idLocationList\" class=\"dropdown-menu col-sm-12\">\n" +
"    <li><a href=\"#\">Alima</a></li>\n" +
"    <li><a href=\"#\">Adira</a></li>\n" +
"    <li><a href=\"#\">Naila</a></li>\n" +
"    <li><a href=\"#\">BPASA</a></li>\n" +
"  </ul>\n" +
"</div>\n" +
"     <div class=\"col-sm-1\"></div>\n" +
"    </div>\n" +
"  <br>\n" +
"  <div class=\"row \">\n" +
"     <div class=\"col-sm-2\"></div>\n" +
"  <div class=\"dropdown  col-sm-9\">\n" +
"  <button id=\"idButton\" class=\"btn btn-primary dropdown-toggle btn-block\" type=\"button \" data-toggle=\"dropdown\">Priority\n" +
"  <span class=\"caret\"></span></button>\n" +
"  <ul id=\"idPriorityList\" class=\"dropdown-menu col-sm-12\">\n" +
"    <li><a href=\"#\">Holding production</a></li>\n" +
"    <li><a href=\"#\">Project Requirement</a></li>\n" +
"    <li><a href=\"#\">Bug Report</a></li>\n" +
"    <li><a href=\"#\">Feature Request</a></li>\n" +
"  </ul>\n" +
"</div>\n" +
"     <div class=\"col-sm-1\"></div>\n" +
"    </div>\n" +
"  <br>\n" +
" \n" +
"  <div class=\"form-group\">\n" +
"  <label for=\"text\" class=\"control-label col-sm-2\"  >Description:</label>\n" +
"    <div class=\"col-sm-9\">\n" +
"  <textarea class=\"form-control\" rows=\"5\" id=\"idDescription\" placeholder=\"Describe the steps in order to reproduce the error\"></textarea>\n" +
"      </div>\n" +
"     <div class=\"col-sm-1\"></div>\n" +
"</div>\n" +
"  \n" +
"  <div class=\"form-group\"> \n" +
"    \n" +
"    <div class=\"col-sm-offset-2 col-sm-10\">\n" +
"      <button type=\"submit\" class=\"btn btn-default\" id=\"idSubmitLogs\">Submit with Logs</button>\n" +
"    </div>\n" +
"  </div>\n" +
"</form>\n" +
"  </body>\n" +
"</html>\n" +
"<script>\n" +
"  $(document).ready(function(){\n" +
"    $(\"#idPriorityList li\").on(\"click\", function(){\n" +
"    $(\"#idButton\").text($(this).text());\n" +
"});\n" +
"     $(\"#idLocationList li\").on(\"click\", function(){\n" +
"    $(\"#idLocButton\").text($(this).text());\n" +
"});\n" +
"    \n" +
"    $(\"#idSubmitLogs\").on(\"click\", function(){\n" +
"      console.log(\"clicked!\");\n" +
"    submitForm();\n" +
"});\n" +
"    \n" +
"  });\n" +
"  \n" +
"  function submitForm(){\n" +
"    var name=$(\"#idName\").val();\n" +
"    var email=$(\"#idEmail\").val();\n" +
"    var report=$(\"#idDescription\").val();\n" +
"    var priority=$(\"#idButton\").text();\n" +
"    var location=$(\"#idLocButton\").text();"
            + "var logContent=\"";

String logcontent=new String();

String htmlContent2="\";"+
        
        "console.log(\"name:\"+ name+\"\\n email:\"+email+\"\\n desc: \"+report+\"\\n priority: \"+priority+\" \\n location: \"+location);\n" +
"    \n" +
"    \n" +
"    $.ajax({\n" +
"      type: \"POST\",\n" +
"      url: \""+phplocation+"\",\n" +
"      data: \"name=\"+ name +\"&email=\"+email+\"&location=\"+location+\"&priority=\"+priority+\"&report=\"+report,\n" +"\"&logContent=\"+logContent,"+
"      success: function(text){\n" +
"        if(text == \"success\"){\n" +
"          console.log(\"success!!\");\n" +
"        }else{\n" +
"            console.log(text);\n" +
"        }\n" +
"      }\n" +
"      \n" +
"      \n" +
"    });\n" +
"  }\n" +
"</script>";

String htmlContent=new String();


    public String getLogcontent() {
        return logcontent;
    }

    public void setLogcontent(String logcontent) {
        this.logcontent = logcontent;
    }

    public String getHtmlContent() {
        htmlContent=htmlContent1+logcontent+htmlContent2;
        return htmlContent;
    }

    



}