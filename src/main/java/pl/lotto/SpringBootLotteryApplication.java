package pl.lotto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.lotto.domain.numbergenerator.WinningNumberGeneratorFacadeConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({WinningNumberGeneratorFacadeConfigurationProperties.class})
public class SpringBootLotteryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLotteryApplication.class, args);
    }
}
