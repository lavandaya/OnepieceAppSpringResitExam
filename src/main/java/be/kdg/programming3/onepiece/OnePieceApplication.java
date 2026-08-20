package be.kdg.programming3.onepiece;

import be.kdg.programming3.onepiece.data.DataFactory;
import be.kdg.programming3.onepiece.presentation.ConsoleMenuView;

public class OnePieceApplication {

    public static void main(String[] args) {
        DataFactory.seed();
        new ConsoleMenuView().show();
    }
}
//sfsf