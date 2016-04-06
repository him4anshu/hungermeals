
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%><%@page import="com.oreilly.servlet.MultipartRequest" %> 

<%@page import="java.util.*" %> 
<%@page import="com.oreilly.servlet.multipart.*"%> 
<%@page import="java.io.*" %>
<%@page import="org.apache.commons.httpclient.*"%>
<%@page import="org.apache.commons.httpclient.methods.*"%>
<%@page import="org.apache.commons.httpclient.params.HttpMethodParams"%>

<%@page import="javax.xml.parsers.*"%>
<%@page import="org.xml.sax.InputSource"%>
<%@page import="org.w3c.dom.*"%>
<%@page import="java.sql.*"%>
<%@page import="com.sun.*"%>
<%@page import="javax.swing.*"%>
<%@page import="java.io.*"%>
<%@page import="java.awt.*"%>

<%@page import="java.awt.image.BufferedImage"%>
<%@page import="java.awt.image.*"%>
<%@page import="com.sun.image.codec.jpeg.*"%>
<%
File file ;
int maxFileSize = 5000 * 1024;
int maxMemSize = 5000 * 1024;
ServletContext context = pageContext.getServletContext();
String filePath = "/home/himanshu/Downloads/File/EventsImg/";

// Verify the content type
String contentType = request.getContentType();
if ((contentType.indexOf("multipart/form-data") >= 0)) {

	
   DiskFileItemFactory factory = new DiskFileItemFactory();
   // maximum size that will be stored in memory
   factory.setSizeThreshold(maxMemSize);
   // Location to save data that is larger than maxMemSize.
   factory.setRepository(new File("/home/himanshu/apache-tomcat-7.0.53/webapps/mailer/template_images/"));

   // Create a new file upload handler
   ServletFileUpload upload = new ServletFileUpload(factory);
   // maximum file size to be uploaded.
   upload.setSizeMax( maxFileSize );
   try{ 
      // Parse the request to get file items.
      List fileItems = upload.parseRequest(request);

      // Process the uploaded file items
      Iterator i = fileItems.iterator();

      out.println("<html>");
      out.println("<head>");
      out.println("<title>JSP File upload</title>");  
      out.println("</head>");
      out.println("<body>");
      while ( i.hasNext () ) 
      {
    	  
         FileItem fi = (FileItem)i.next();
         if ( !fi.isFormField () )	
         {
         // Get the uploaded file parameters
         String fieldName = fi.getFieldName();
        
         String fileName = fi.getName();
         System.out.println(fileName);
         boolean isInMemory = fi.isInMemory();
         long sizeInBytes = fi.getSize();
         // Write the file
         if( fileName.lastIndexOf("\\") >= 0 ){
         file = new File( filePath + 
         fileName.substring( fileName.lastIndexOf("\\"))) ;
         }else{
         file = new File( filePath + 
         fileName.substring(fileName.lastIndexOf("\\")+1)) ;
         }
         fi.write( file ) ;
         out.println("Uploaded Filename: " + filePath + 
         fileName + "<br>");
         }
      }
      out.println("</body>");
      out.println("</html>");
   }catch(Exception ex) {
      System.out.println(ex);
   }
}else{
   out.println("<html>");
   out.println("<head>");
   out.println("<title>Servlet upload</title>");  
   out.println("</head>");
   out.println("<body>");
   out.println("<p>No file uploaded</p>"); 
   out.println("</body>");
   out.println("</html>");
}
%> 

