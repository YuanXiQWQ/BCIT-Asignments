import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.time.LocalDate;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.StringBuilder;

@MultipartConfig
public class UploadServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dirList = getListing("C:\\tomcat\\webapps\\upload\\images");
        response.setContentType("text/plain");
        response.setContentLength(dirList.length());
        PrintWriter out = response.getWriter();
        out.println(dirList);	
        out.flush();  
    }    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
System.out.println("??????? in do Post??????\n");
        Part filePart = request.getPart("File");
        String fileName = filePart.getSubmittedFileName();
        if(fileName.equals("")){
            response.setStatus(302);
            return;
        }
System.out.println("??????? inside do Post??????\n");
        filePart.write(System.getProperty("catalina.base") + "/webapps/upload/images/" + fileName);
       
    }   
    private String getListing(String path) {
     System.out.println("??????? in getListing??????\n");
     String dirList =  null;
      File dir = new File(path);
      String[] chld = dir.list();
      for(int i = 0; i < chld.length; i++){
         dirList += "," + chld[i];      
      }
      System.out.println(dirList);
      return dirList;
   } 
}


