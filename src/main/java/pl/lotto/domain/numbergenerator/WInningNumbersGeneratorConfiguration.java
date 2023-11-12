package pl.lotto.domain.numbergenerator;

import pl.lotto.domain.numberreceiver.NumberReceiverFacade;

public class WInningNumbersGeneratorConfiguration {
    WinningNumbersGeneratorFacade createForTests(RandomNumbersGenerable generator, WinningNumbersRepository repository, NumberReceiverFacade numberReceiverFacade) {
        WinningNumbersValidator validator = new WinningNumbersValidator();
        return  new WinningNumbersGeneratorFacade(generator, validator, numberReceiverFacade, repository);
    }
}
