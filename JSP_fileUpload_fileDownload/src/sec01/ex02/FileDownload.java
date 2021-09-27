package sec01.ex02;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileDownload
 */
@WebServlet("/download.do")
public class FileDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		// 다운로드할 폴더 경로 지정
		String file_repo = "C:\\file_repo";
		
		// 다운로드할 파일 이름을 파라미터로 얻어옴
		String fileName = request.getParameter("fileName");
		System.out.println("fileName=" + fileName);
		
		// OutputStream을 이용하여 응답을 바이트로 받음
		OutputStream out = response.getOutputStream();
		
		// 다운로드할 파일 경로 지정
		String downFile = file_repo + "\\" + fileName;
		File f = new File(downFile);
		
		// 헤더 설정
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Content-disposition", "attachment; fileName=" + fileName);
		
		// FileInputStream을 이용하여 바이트를 읽음
		FileInputStream in = new FileInputStream(f);
		
		// 바이트 버퍼 크기 설정 (8KB)
		// 버퍼기능을 이용해 파일에서 버퍼로 데이터를 읽어와 한번에 출력함
		byte[] buffer = new byte[1024 * 8];
		
		// 읽어들인 값이 -1일 때 까지 (즉, 파일을 다 읽을 때 까지) 루프를 돔
		while (true) {
			int count = in.read(buffer);
			if (count == -1)
				break;
			
			// 읽어들인 버퍼를 write함 => 다시 브라우저로 출력
			out.write(buffer, 0, count);
		}
		in.close();
		out.close();
	}

}
