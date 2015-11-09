package nl.hr.cmi.inf.citygis.controller

import nl.hr.cmi.inf.citygis.database.UserRepository
import nl.hr.cmi.inf.citygis.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
@EnableAutoConfiguration
public class UserController {

    @Autowired
    def UserRepository repository

    @RequestMapping(method=[RequestMethod.GET])
    def get(Long id) {
        id ? repository.findOne(id) : repository.findAll()
    }

    @RequestMapping(value="/add",method=[RequestMethod.PUT])
    def create(@RequestBody User user) {
        user.setIsActive(true)
        user.setCurrentTimes()
        user = repository.save(user)
        user.reCreateHash();
        repository.save(user);
        return user;
    }
}