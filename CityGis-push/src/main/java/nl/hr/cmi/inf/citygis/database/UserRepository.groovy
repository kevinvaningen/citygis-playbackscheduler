package nl.hr.cmi.inf.citygis.database

import nl.hr.cmi.inf.citygis.model.User
import org.springframework.data.repository.CrudRepository;


/**
 * Created by cmi on 29-09-15.
 */
public interface UserRepository extends CrudRepository<User, Long> {
}