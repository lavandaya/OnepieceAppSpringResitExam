package be.kdg.programming3.onepiece.presentation;

import be.kdg.programming3.onepiece.data.DataFactory;
import be.kdg.programming3.onepiece.domain.Powertype;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConsoleMenuView {
    private final Scanner scanner = new Scanner(System.in);

    public void show() {
        int choice;
        do {
            printMenu();
            choice = readChoice();
            switch (choice) {
                case 0 -> System.out.println("Bye!");
                case 1 -> showAllCharacters();
                case 2 -> showCharactersByPowertype();
                case 3 -> showAllBattles();
                case 4 -> showBattlesFiltered();
                default -> System.out.println("Please choose a number between 0 and 4");
            }
        } while (choice != 0);
    }

    private void printMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("==========================");
        System.out.println("0) Quit");
        System.out.println("1) Show all characters");
        System.out.println("2) Show characters of a powertype");
        System.out.println("3) Show all battles");
        System.out.println("4) Show battles with location and/or date");
        System.out.print("Choice (0-4): ");
    }

    private int readChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void showAllCharacters() {
        System.out.println("\nAll characters");
        System.out.println("===============");
        DataFactory.characters.forEach(System.out::println);
    }

    private void showCharactersByPowertype() {
        Powertype selected = readPowertype();
        DataFactory.characters.stream()
                .filter(c -> c.getPowertype() == selected)
                .forEach(System.out::println);
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

    private void showAllBattles() {
        System.out.println("\nAll battles");
        System.out.println("===========");
        DataFactory.battles.forEach(System.out::println);
    }

    private void showBattlesFiltered() {
        System.out.print("\nEnter (part of) a location or leave blank: ");
        String location = scanner.nextLine().trim();
        System.out.print("Enter a full date (yyyy-mm-dd) or leave blank: ");
        String dateInput = scanner.nextLine().trim();
        LocalDate date = dateInput.isBlank() ? null : LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);

        DataFactory.battles.stream()
                .filter(b -> location.isBlank() || b.getLocation().toLowerCase().contains(location.toLowerCase()))
                .filter(b -> date == null || b.getDate().toLocalDate().isEqual(date))
                .forEach(System.out::println);
    }
}
