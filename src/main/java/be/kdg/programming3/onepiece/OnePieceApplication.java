package be.kdg.programming3.onepiece;

import be.kdg.programming3.onepiece.presentation.view.MenuView;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnePieceApplication implements CommandLineRunner {

    private final MenuView menuView;

    public OnePieceApplication(MenuView menuView) {
        this.menuView = menuView;
    }

    public static void main(String[] args) {
        SpringApplication.run(OnePieceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        menuView.show();
    }
}
