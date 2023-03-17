package cz.tmobile.conf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import cz.tmobile.conf.ConfApplication;

@SpringBootApplication(scanBasePackages = { "cz.tmobile.conf"})
@EnableScheduling
public class ConfApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfApplication.class, args);
    }
}
