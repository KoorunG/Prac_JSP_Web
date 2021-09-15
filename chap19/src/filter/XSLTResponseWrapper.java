package filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

// 서블릿과 JSP가 ResponseBufferWriter를 출력 스트림으로 사용하도록
// 응답 래퍼 클래스 XSLTResponseWrapper를 만들어줌
public class XSLTResponseWrapper extends HttpServletResponseWrapper {
	
	private ResponseBufferWriter buffer = null;
	
	public XSLTResponseWrapper(HttpServletResponse response) {
		super(response);
		buffer = new ResponseBufferWriter();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return buffer;
	}

	@Override
	public void setContentType(String contentType) {
		
	}
	
	public String getBufferedStirng() {
		return buffer.toString();
	}
}
