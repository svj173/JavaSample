<!--
Тоже самое что и в классе сервлете UploadServlet
http://www.javaatwork.com/java-upload-applet/samplecode.html
 -->

<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>

<%
boolean isMultipart = ServletFileUpload.isMultipartContent(request);

// check if the http request is a multipart request
// with other words check that the http request can have uploaded files
if (isMultipart) {


  // The base upload directory. In this directory all uploaded files will
  // be stored. With the applet param tag 'directory' you can create a
  // subdirectory for a user.
  // See http://www.javaatwork.com/parameters.html#directory for more
  // information about the 'directory' param tag. For a Windows environment
  // the base_directory can be e.g. 'c:/temp' for Linux environment '/tmp'.
  String base_directory = "c:/temp";

  // Create a factory for disk-based file items
  FileItemFactory factory = new DiskFileItemFactory();

  // Create a new file upload handler
  ServletFileUpload servletFileUpload = new ServletFileUpload(factory);

  // Set upload parameters
  // See Apache Commons FileUpload for more information
  // http://jakarta.apache.org/commons/fileupload/using.html
  servletFileUpload.setSizeMax(-1);

  try {

    String directory = "";

    // Parse the request
    List items = servletFileUpload.parseRequest(request);

    // Process the uploaded items
    Iterator iter = items.iterator();

    while (iter.hasNext()) {
      FileItem item = (FileItem) iter.next();

      // the param tag directory is sent as a request parameter to the server
      // check if the upload directory is available
      if (item.isFormField()) {

        String name = item.getFieldName();

        if (name.equalsIgnoreCase("directory")) {

          directory = item.getString();
        }

        // retrieve the files
      } else {

        // the fileNames are urlencoded
        String fileName = URLDecoder.decode(item.getName());

        File file = new File(directory, fileName);
        file = new File(base_directory, file.getPath());

        // retrieve the parent file for creating the directories
        File parentFile = file.getParentFile();

        if (parentFile != null) {
          parentFile.mkdirs();
        }

        // writes the file to the filesystem
        item.write(file);
      }
    }

  } catch (Exception e) {
    e.printStackTrace();
    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
  }
}

%>
