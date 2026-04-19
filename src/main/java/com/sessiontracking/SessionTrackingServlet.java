package com.sessiontracking;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SessionTracker")
public class SessionTrackingServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Get the session object (create one if it doesn't exist)
        HttpSession session = request.getSession(true);
        
        // 2. Set session expiry to 1 minute (60 seconds)
        session.setMaxInactiveInterval(60);

        // 3. Get session details
        String sessionId = session.getId();
        Date createTime = new Date(session.getCreationTime());
        Date lastAccessTime = new Date(session.getLastAccessedTime());

        // 4. Logic for Visit Count
        Integer visitCount = (Integer) session.getAttribute("visitCount");
        if (visitCount == null) {
            visitCount = 1;
        } else {
            visitCount++;
        }
        session.setAttribute("visitCount", visitCount);

        // 5. Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // 6. Generate HTML response directly from Servlet
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>Session Tracking Demo</title></head>");
        out.println("<body>");
        out.println("<h2>Session Tracking Information</h2>");
        out.println("<table border='1'>");
        out.println("<tr><td>Session ID</td><td>" + sessionId + "</td></tr>");
        out.println("<tr><td>Creation Time</td><td>" + createTime + "</td></tr>");
        out.println("<tr><td>Last Access Time</td><td>" + lastAccessTime + "</td></tr>");
        out.println("<tr><td>Visit Count</td><td>" + visitCount + "</td></tr>");
        out.println("</table>");
        out.println("<p><b>Note:</b> If you wait for more than 1 minute and refresh, " +
                    "the Visit Count will reset to 1.</p>");
        out.println("<button onclick='location.reload()'>Refresh Page</button>");
        out.println("</body>");
        out.println("</html>");
    }
}
