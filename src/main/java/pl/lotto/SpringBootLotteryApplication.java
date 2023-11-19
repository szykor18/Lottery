package pl.lotto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pl.lotto.domain.numbergenerator.WinningNumberGeneratorFacadeConfigurationProperties;
import pl.lotto.infrastructure.numbergenerator.http.RandomNumberGeneratorRestTemplateConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableConfigurationProperties({WinningNumberGeneratorFacadeConfigurationProperties.class, RandomNumberGeneratorRestTemplateConfigurationProperties.class})
@EnableMongoRepositories
public class SpringBootLotteryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLotteryApplication.class, args);
    }
}
