package nl.hr.cmi.inf.citygis

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@SpringBootApplication
 class CitygisApplication {

    @RequestMapping("/")
    public String home() {
        return "index"
    }

    public static void main(String[] args) {


        SpringApplication.run(CitygisApplication.class, args);
    }
}