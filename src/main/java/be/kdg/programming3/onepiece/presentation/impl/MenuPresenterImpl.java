package be.kdg.programming3.onepiece.presentation.impl;

import be.kdg.programming3.onepiece.business.domain.Powertype;
import be.kdg.programming3.onepiece.business.service.BattleService;
import be.kdg.programming3.onepiece.business.service.CharacterService;
import be.kdg.programming3.onepiece.presentation.presenter.MenuPresenter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class MenuPresenterImpl implements MenuPresenter {
    private final CharacterService characterService;
    private final BattleService battleService;
    private final Scanner scanner;

    public MenuPresenterImpl(CharacterService characterService, BattleService battleService, Scanner scanner) {
        this.characterService = characterService;
        this.battleService = battleService;
        this.scanner = scanner;
    }

    @Override
    public void onShowAllCharacters() {
        System.out.println("\nAll characters");
        System.out.println("===============");
        characterService.getAllCharacters().forEach(System.out::println);
    }

    @Override
    public void onShowCharactersByPowertype() {
        Powertype selected = readPowertype();
        characterService.getCharactersByPowertype(selected).forEach(System.out::println);
    }

    private Powertype readPowertype() {
        Powertype[] values = Powertype.values();
        String options = IntStream.range(0, values.length)
                .mapToObj(i -> (i + 1) + "=" + values[i].name())
                .collect(Collectors.joining(", "));
        while (true) {
            System.out.print("\nPowertype (" + options + "): ");
            String input = scanner.nextLine().trim();
            try {
                int idx = Integer.parseInt(input);
                if (idx >= 1 && idx <= values.length) {
                    return values[idx - 1];
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Please enter one of the listed powertypes");
        }
    }

    @Override
    public void onShowAllBattles() {
        System.out.println("\nAll battles");
        System.out.println("===========");
        battleService.getAllBattles().forEach(System.out::println);
    }

    @Override
    public void onShowBattlesFiltered() {
        System.out.print("\nEnter (part of) a location or leave blank: ");
        String location = scanner.nextLine().trim();
        System.out.print("Enter a full date (yyyy-mm-dd) or leave blank: ");
        String dateInput = scanner.nextLine().trim();
        LocalDate date = dateInput.isBlank() ? null : LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);

        battleService.findBattles(location, date).forEach(System.out::println);
    }

    @Override
    public void exit() {
        System.out.println("Bye!");
        System.exit(0);
    }
}
