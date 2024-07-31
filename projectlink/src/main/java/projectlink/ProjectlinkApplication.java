package projectlink;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import projectlink.models.AppUser;
import projectlink.models.Board;
import projectlink.models.BoardList;
import projectlink.models.Card;
import projectlink.repositories.*;

import java.util.List;


@SpringBootApplication
public class ProjectlinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectlinkApplication.class, args);
		//test
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}

}
