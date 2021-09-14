package template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
	
//	public Integer calcSum(String filePath) throws IOException {
//		
//		BufferedReader br = null;
//		try {
//			br = new BufferedReader(new FileReader(filePath));
//			Integer sum = 0;
//			String line = null;
//			while((line = br.readLine()) != null) {
//				sum += Integer.valueOf(line);					// 메소드 동작
//			}
//			
//			return sum;
//		} catch(IOException e) {
//			System.out.println(e.getMessage());					// 예외가 발생하면 메시지 출력 + 예외 발생 (throw e)
//			throw e;
//		} finally {
//			if(br != null) {
//				try {
//					br.close();									// 반드시 닫히게끔 설정
//				} catch(IOException e) {
//					System.out.println(e.getMessage());
//				}
//			}
//		}
//	}
	
	public Integer fileReadTemplate(String filePath, BufferedReaderCallback callback) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filePath));
			return callback.doSomethingWithReader(br);			// 콜백으로부터 작업결과를 전달받아 리턴함
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	// fileReadTemplate 메소드를 미리 구현해놔야 한다는 것을 인지할 것!		-- V1
	
	
	public <T> T lineReadTemplate(String filePath, LineCallback<T> callback, T initVal) throws IOException {
		BufferedReader br = null;
		T res = initVal;
		try {
			br = new BufferedReader(new FileReader(filePath));
			String line = null;
			while((line = br.readLine()) != null) {
				res = callback.doSomethingWithLine(line, res);
			}
			return res;
		} catch(IOException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	// 초기값과 변동사항을 따로 콜백메소드로 만든 lineReadTemplate			-- V2
	
	
	
	public Integer calcSumV1(String filePath) throws IOException {
		
		BufferedReaderCallback sumCallback = new BufferedReaderCallback() {
			
			@Override
			public Integer doSomethingWithReader(BufferedReader br) throws IOException {
				Integer sum = 0;									/* 초기값 */ 	
				String line = null;
				while((line = br.readLine()) != null) {
					sum += Integer.valueOf(line);					/* 수행동작 */ 
				}
				return sum;
			}
		};
		
		return fileReadTemplate(filePath, sumCallback);
	}
	
	public Integer calcSumV2(String filePath) throws IOException {
		LineCallback<Integer> sumCallback = new LineCallback<Integer>() {
			@Override
			public Integer doSomethingWithLine(String line, Integer value) {
				return value + Integer.valueOf(line);
			}
		};
		return lineReadTemplate(filePath, sumCallback, 0);
	}
	
	
	public Integer calcMultiV1(String filePath) throws IOException {
		
		BufferedReaderCallback multiCallback = new BufferedReaderCallback() {
			
			@Override
			public Integer doSomethingWithReader(BufferedReader br) throws IOException {
				Integer multi = 1;									/* 초기값 */ 
				String line = null;
				while((line = br.readLine()) != null) {
					multi *= Integer.valueOf(line);					/* 수행동작 */ 
				}
				return multi;
			}
		};
		return fileReadTemplate(filePath, multiCallback);
	}
	
	public Integer calcMultiV2(String filePath) throws IOException {
		LineCallback<Integer> multiCallback = new LineCallback<Integer>() {
			
			@Override
			public Integer doSomethingWithLine(String line, Integer value) {
				return value * Integer.valueOf(line);
			}
		};
		return lineReadTemplate(filePath, multiCallback, 1);
	}
	
	public String concatenate(String filePath) throws IOException {
		LineCallback<String> concatCallback = new LineCallback<String>() {
			
			@Override
			public String doSomethingWithLine(String line, String value) {
				return value + line;
			}
		};
		return lineReadTemplate(filePath, concatCallback, "");
	}
}

class Execute {
	public static void main(String[] args) throws IOException {
		
		String filePath = "numbers.txt";
		Calculator cal = new Calculator();
		System.out.println("=== V1 ======================");
		System.out.println(cal.calcSumV1(filePath));
		System.out.println(cal.calcMultiV1(filePath));
		System.out.println("=== V2 ======================");
		System.out.println(cal.calcSumV2(filePath));
		System.out.println(cal.calcMultiV2(filePath));
		System.out.println("=== ETC ======================");
		System.out.println(cal.concatenate(filePath));
	}
}
