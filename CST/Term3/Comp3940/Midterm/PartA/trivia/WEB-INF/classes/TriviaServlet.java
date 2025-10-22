import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet("/triviaapi/trivia")
public class TriviaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        response.setContentType("application/json");
        String imagePath = "";
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/quizapp",
                    "root",
                    "password"
            );
            PreparedStatement stmt =
                    conn.prepareStatement("SELECT path FROM images WHERE id = ?");
            stmt.setInt(1, 1);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
                imagePath = rs.getString("path");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        response.getWriter().write("{\"path\": \"" + imagePath + "\"}");
    }
}
