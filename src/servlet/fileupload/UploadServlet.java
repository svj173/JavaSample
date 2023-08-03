package servlet.fileupload;


/**
 * <BR/> Взято с http://www.javaatwork.com/java-upload-applet/samplecode.html
 * <BR/>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 29.12.2010 9:39:58
 */
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Class for storing the uploaded files.
 *
 * @author JavaAtWork
 */
public class UploadServlet extends HttpServlet {

  /**
    * The base upload directory. In this directory all uploaded files will
    * be stored. With the applet param tag 'directory' you can create a
    * subdirectory for a user.
    * See http://www.javaatwork.com/parameters.html#directory for more
    * information about the 'directory' param tag. For a Windows environment
    * the BASE_DIRECTORY can be e.g. * 'c:/temp' for Linux environment '/tmp'.
    */
  private static final String BASE_DIRECTORY = "/tmp";

  protected void doPost ( HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {

    boolean isMultipart = ServletFileUpload.isMultipartContent(request);

    // check if the http request is a multipart request
    // with other words check that the http request can have uploaded files
    if (isMultipart)
    {

      //  Create a factory for disk-based file items
      FileItemFactory factory = new DiskFileItemFactory();

      //  Create a new file upload handler
      ServletFileUpload servletFileUpload = new ServletFileUpload(factory);

      // Set upload parameters
      // See Apache Commons FileUpload for more information
      // http://jakarta.apache.org/commons/fileupload/using.html
      servletFileUpload.setSizeMax(-1);

      try
      {
        String directory = "";

        // Parse the request
        List items = servletFileUpload.parseRequest(request);

        // Process the uploaded items
        Iterator iter = items.iterator();

        while (iter.hasNext())
        {
          FileItem item = (FileItem) iter.next();

          // the param tag directory is sent as a request parameter to
          // the server
          // check if the upload directory is available
          if (item.isFormField())
          {

            String name = item.getFieldName();

            if (name.equalsIgnoreCase("directory")) {
              directory = item.getString();
            }

            // retrieve the files
          } else {

            // the fileNames are urlencoded
            String fileName = URLDecoder.decode(item.getName());

            File file = new File(directory, fileName);
            file = new File(BASE_DIRECTORY, file.getPath());

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

      response.setStatus(HttpServletResponse.SC_OK);

    } else {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
  }
}

