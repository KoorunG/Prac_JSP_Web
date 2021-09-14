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

public class DBCPInit2 extends HttpServlet {

	@Override
	public void init() throws ServletException {
		loadJDBCDriver();
		initConnectinonPool();
	}

	private void loadJDBCDriver() {
		String driverClass = getInitParameter("jdbcdriver");	// getInitParameter => web.xml에서 <init-param>으로 설정한 값을 연결할 수 있다
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("fail to load JDBC Driver", e);
		}
	}

	private void initConnectinonPool() {
		try {
			String jdbcUrl = getInitParameter("jdbcUrl");
			String username = getInitParameter("dbUser");
			String pw = getInitParameter("dbPass");
			
			ConnectionFactory connFactory = new DriverManagerConnectionFactory(jdbcUrl,username,pw);
			
			PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connFactory, null);
			poolableConnectionFactory.setValidationQuery("select 1");
			
			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
			poolConfig.setTimeBetweenEvictionRunsMillis(1000L * 60L * 5L);
			poolConfig.setMinIdle(4);
			poolConfig.setMaxTotal(50);
			
			GenericObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<PoolableConnection>(poolableConnectionFactory, poolConfig);
			poolableConnectionFactory.setPool(connectionPool);
			
			Class.forName("org.apache.commons.dbcp2.PoolingDriver");
			PoolingDriver driver = (PoolingDriver)DriverManager.getDriver("jdbc:apache:commons:dbcp:");
			String poolName = getInitParameter("poolName");
			driver.registerPool(poolName, connectionPool);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

}
