package jdbc;

import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class DBCPInit extends HttpServlet {

	@Override
	public void init() throws ServletException {
		loadJDBCDriver();
		initConnectionPool();
	}

	private void loadJDBCDriver() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException("fail to load JDBC Driver", ex);
		}

		// 커넥션 풀이 내부에서 사용할 JDBC 드라이버를 로딩한다. 예제의 경우, MySQL에 연결 MySQL용 드라이버를 로딩한다.

	}

	private void initConnectionPool() {
		try {
			String jdbcUrl =  "jdbc:mysql://localhost:3306/guestbook" 	// guestbook DB를 쓸 예정
							+ "?characterEncoding=UTF-8" 
							+ "&serverTimezone=UTC"
							+ "&useSSL=false" 
							+ "&allowpublickeyretrieval=true";

			String username = "jspexam";
			String pw = "jsppw";

			ConnectionFactory connFactory = new DriverManagerConnectionFactory(jdbcUrl, username, pw);
			// MySQL 연결에 사용할 jdbcUrl, username, pw를 받아서
			// 커넥션 풀이 새로운 커넥션을 생성할 때 사용할 커넥션 팩토리(ConnectionFactory)를 생성한다.

			PoolableConnectionFactory poolableConnFactory = new PoolableConnectionFactory(connFactory, null);
			poolableConnFactory.setValidationQuery("select 1");
			// setValidationQuery(String) => 커넥션을 검사할 때 사용할 쿼리 설정
			
			// PoolableConnection을 생성하는 팩토리를 생성한다.
			// DBCP는 커넥션 풀에 커넥션을 보관할 때 PoolableConnection을 사용한다.
			// 이 클래스는 내부적으로 실제 커넥션을 담고 있으며,
			// 커넥션 풀을 관리하는 데 필요한 기능을 추가로 제공한다.
			// 예를들면, close() 메서드를 실행하면 실제 커넥션을 종료하지 않고 풀에 커넥션을 반환한다.

			// DB 커넥션이 유효한지 여부를 검사할 때 사용할 쿼리를 지정합니다.
			// 이것은 특정 시간마다 트랜잭션 DB 세션 재접속을 검증하는 validationQuery를 실행하게 됩니다.
			// 오라클의 경우 : validationQuery="select 1 from dual" 환경을 셋팅합니다.

			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
			poolConfig.setTimeBetweenEvictionRunsMillis(1000L * 60L * 5L); // 유효 커넥션 검사 주기 설정
			poolConfig.setTestWhileIdle(true); // 풀에 보관중인 커넥션 유효 검사 설정 => true
			poolConfig.setMinIdle(4); // 커넥션 최소 개수를 4개로 설정
			poolConfig.setMaxTotal(50); // 커넥션 최대 개수를 50개로 설정
			// 커넥션 설정 정보
			
			// setMaxTotal => 풀이 관리하는 커넥션의 최대 개수 설정
			// setMinIdle => 커넥션 풀이 보관할 수 있는 최소 유휴 개수 설정
			// setMaxIdle => 커넥션 풀이 보관할 수 있는 최대 유휴 개수 설정 ( MaxTotal과는 다른 개념이라는 것 주의... )
			
			// DBCP는 커넥션이 최소 유휴 시간보다 오래 풀에 있는 경우와, 커넥션이 유효한지 여부를 검증하여 주기적으로 커넥션을 제거해준다
			
			// JSP책 424페이지 참조!

			GenericObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnFactory,
					poolConfig);
			poolableConnFactory.setPool(connectionPool);
			// 커넥션 팩토리와 커넥션 설정정보를 파라미터로 받아서 커넥션 풀 생성

			Class.forName("org.apache.commons.dbcp2.PoolingDriver");
			PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
			driver.registerPool("guestbook", connectionPool);	
			// 커넥션 풀의 이름을 guestbook으로 설정

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
