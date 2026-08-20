package be.kdg.programming3.onepiece.presentation.controller;

import be.kdg.programming3.onepiece.business.service.BattleService;
import be.kdg.programming3.onepiece.business.service.CharacterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class BattleController {
    private static final Logger logger = LoggerFactory.getLogger(BattleController.class);

    private final BattleService battleService;
    private final CharacterService characterService;

    public BattleController(BattleService battleService, CharacterService characterService) {
        this.battleService = battleService;
        this.characterService = characterService;
    }

    @GetMapping("/battles")
    public String showBattles(@RequestParam(required = false) String location,
                               @RequestParam(required = false)
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                               Model model) {
        logger.debug("Loading battles page (location='{}', date={})", location, date);
        model.addAttribute("battles", battleService.findBattles(location, date));
        model.addAttribute("location", location);
        model.addAttribute("date", date);
        return "battles";
    }

    @GetMapping("/battles/add")
    public String showAddBattleForm(Model model) {
        model.addAttribute("allCharacters", characterService.getAllCharacters());
        return "addBattle";
    }

    @PostMapping("/battles/add")
    public String addBattle(@RequestParam String name, @RequestParam String location,
                             @RequestParam
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                             @RequestParam String winner,
                             @RequestParam(required = false) List<Integer> characterIds) {
        logger.debug("Adding battle '{}' via web form", name);
        battleService.addBattle(name, location, date, winner, characterIds);
        return "redirect:/battles";
    }
}
