package filter;

import java.io.PrintWriter;
import java.io.StringWriter;
		// 출력 버퍼 역할 ResponseBufferWriter 클래스 제작
		// ResponseBufferWriter는 print(), println(), write() 등의 메서드를 통해서 전달된
		// 데이터를 StringWriter에 저장한다.
public class ResponseBufferWriter extends PrintWriter {
	
	public ResponseBufferWriter() {
		super(new StringWriter(4096));
	}

	// toString() 메서드는 StringWriter에 저장된 데이터를 String 타입으로 변환해 준다.
	public String toString() {
		return ((StringWriter) super.out).toString();
	}
		
}
