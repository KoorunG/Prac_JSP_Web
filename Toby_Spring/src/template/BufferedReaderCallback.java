package template;

import java.io.BufferedReader;
import java.io.IOException;

public interface BufferedReaderCallback {
	Integer doSomethingWithReader(BufferedReader br) throws IOException;
}

// 템플릿으로부터 BufferedReader을 제공받아 무언가를 하는 콜백 메소드
