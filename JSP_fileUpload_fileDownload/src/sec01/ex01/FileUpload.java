package sec01.ex01;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class FileUpload
 */

@WebServlet("/upload.do")
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
			String encoding = "utf-8";
			
			// 파일 경로를 지정함 (업로드 할)
			File currentDirPath = new File("C:\\file_repo");
			
			// 저장 위치와 업로드 가능한 최대 파일 크기를 설정함
			DiskFileItemFactory factory = new DiskFileItemFactory();
			
			// 위에서 저정한 경로로 파일을 저장할 경로 설정
			factory.setRepository(currentDirPath);
			
			// 최대 파일 크기 설정
			factory.setSizeThreshold(1024 * 1024);

			// 파일 업로드 창에서 업로드된 파일과 매개변수에 대한 정보를 가져와 파일을 업로드하고 매개변수 값을 출력함
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				
				// multipart/form-data로 파싱하므로, 파싱 관련 메소드인 parseRequest를 호출하여 리스트에 담음
				List<?> items = upload.parseRequest(request);
				
				// 위의 리스트에서 iterate로 데이터를 읽음
				for (int i = 0; i < items.size(); i++) {
					FileItem fileItem = (FileItem) items.get(i);
	
					// isFormField => 일반 파라미터인지 여부를 검사하는 메소드 (boolean 반환), 일반 파라미터일 경우 true 반환
					
					// 일반 파라미터일 경우
					if (fileItem.isFormField()) {
						System.out.println(fileItem.getFieldName() + "=" + fileItem.getString(encoding));
					} else {
						
						// 일반 파라미터가 아닐 경우
						System.out.println("파라미터명:" + fileItem.getFieldName());			// 필드 이름 반환 (input 태그의 name 속성)
						System.out.println("파일명:" + fileItem.getName());					// 클라이언트에 저장된 파일의 이름 반환
						System.out.println("파일크기:" + fileItem.getSize() + "bytes");		// 파일의 사이즈 반환
	
						if (fileItem.getSize() > 0) {
							int idx = fileItem.getName().lastIndexOf("\\");
							if (idx == -1) {
								idx = fileItem.getName().lastIndexOf("/");
							}
							String fileName = fileItem.getName().substring(idx + 1);
							File uploadFile = new File(currentDirPath + "\\" + fileName);
							fileItem.write(uploadFile);
						} // end if
					} // end if
				} // end for
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

}
