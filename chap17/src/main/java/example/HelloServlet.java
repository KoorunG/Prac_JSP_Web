package example;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");							// 요청 파라미터의 인코딩을 설정해주는 부분
		response.setContentType("text/html; charset=utf-8");			// 응답의 contentType을 설정해주는 부분
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>인사</title></head>");
		out.println("<body>");
		out.println("안녕하세요!");
		out.println(request.getParameter("name"));
		out.println("님");
		out.println("</body></html>");
		
		
	}
	
	
}
