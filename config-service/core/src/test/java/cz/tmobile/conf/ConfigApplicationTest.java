package cz.tmobile.conf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

import cz.tmobile.conf.api.IUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({ 
	TransactionalTestExecutionListener.class, 
	DependencyInjectionTestExecutionListener.class, 
	DbStructureTestExecutionListener.class, 
	DbUnitTestExecutionListener.class })
@Transactional
public class ConfigApplicationTest {

	@Autowired
	private IUserService testedService;
	
	@Test
	public void contextLoads() {
		testedService.listValues();
	}

}
