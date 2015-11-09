package nl.hr.cmi.inf.citygis.controller

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/queue")
@EnableAutoConfiguration
public class QueueController {

    @RequestMapping(value = "/add", method = [RequestMethod.GET])
    def create(@RequestBody String name) {

        return "TEST";
    }
}