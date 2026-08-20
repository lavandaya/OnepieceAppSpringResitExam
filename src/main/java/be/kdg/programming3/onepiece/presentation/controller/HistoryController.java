package be.kdg.programming3.onepiece.presentation.controller;

import be.kdg.programming3.onepiece.presentation.session.SessionHistory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HistoryController {

    private final SessionHistory sessionHistory;

    public HistoryController(SessionHistory sessionHistory) {
        this.sessionHistory = sessionHistory;
    }

    @GetMapping("/history")
    public String showHistory(Model model) {
        model.addAttribute("visits", sessionHistory.getVisits());
        return "history";
    }
}
