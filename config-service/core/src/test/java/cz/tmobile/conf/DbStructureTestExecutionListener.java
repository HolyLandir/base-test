package cz.tmobile.conf;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class DbStructureTestExecutionListener extends AbstractTestExecutionListener {

	@Override
	public void prepareTestInstance(TestContext testContext) throws Exception {
//		Object dataSource = testContext.getApplicationContext().getBean("dataSource");
	}

	@Override
	public void beforeTestMethod(TestContext testContext) throws Exception {
		DataSource dataSource = (DataSource) testContext.getApplicationContext().getBean("dataSource");
		try (BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/expectedStructure.sql")));
				Connection con = dataSource.getConnection();
				Statement st = con.createStatement()) {
			StringBuilder command = new StringBuilder();
			for (String line = in.readLine(); line != null; line = in.readLine()) {
				if (StringUtils.isNotBlank(line)) {
					command.append(line);
					command.append('\n');
					if (line.endsWith(";")) {
						st.execute(command.toString());
						command = new StringBuilder();
					}
				}
			}
			con.commit();
		}
	}

	@Override
	public void afterTestMethod(TestContext testContext) throws Exception {
		DataSource dataSource = (DataSource) testContext.getApplicationContext().getBean("dataSource");
		try (Connection con = dataSource.getConnection(); Statement st = con.createStatement()) {
			st.execute("DROP ALL OBJECTS;");
		}
	}

}