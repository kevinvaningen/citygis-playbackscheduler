package nl.hr.cmi.inf.citygis;

import nl.hr.cmi.inf.citygis.database.UserRepository;
import nl.hr.cmi.inf.citygis.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {CitygisApplication.class,UserRepository.class})
public class CitygisApplicationTests {

	private UserRepository ur;

	@Autowired
	public void setProductRepository(UserRepository u) {
		this.ur = u;
	}

	@Test
	public void testRepo() {
		Calendar c = Calendar.getInstance();

		User u = new User();
		u.setCreatedDate(c.getTime());
		u.setFirstName("Kevin");
		u.setLastName("van Ingen");
		u.setIsActive(true);
		ur.save(u);

	}


}
