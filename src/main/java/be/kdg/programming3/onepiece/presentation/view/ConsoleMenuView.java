package be.kdg.programming3.onepiece.presentation.view;

import be.kdg.programming3.onepiece.presentation.presenter.MenuPresenter;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

@Component
public class ConsoleMenuView implements MenuView {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleMenuView.class);

    private final MenuPresenter presenter;
    private final Scanner scanner;

    public ConsoleMenuView(MenuPresenter presenter, Scanner scanner) {
        this.presenter = presenter;
        this.scanner = scanner;
    }

    @Override
    public void show() {
        logger.debug("Console menu started");
        int choice;
        do {
            printMenu();
            choice = readChoice();
            switch (choice) {
                case 0 -> presenter.exit();
                case 1 -> presenter.onShowAllCharacters();
                case 2 -> presenter.onShowCharactersByPowertype();
                case 3 -> presenter.onShowAllBattles();
                case 4 -> presenter.onShowBattlesFiltered();
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
}
