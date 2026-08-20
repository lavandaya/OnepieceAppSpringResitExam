package be.kdg.programming3.onepiece.presentation.controller;

import be.kdg.programming3.onepiece.business.service.BattleService;
import be.kdg.programming3.onepiece.business.service.CharacterService;
import be.kdg.programming3.onepiece.presentation.viewmodel.BattleViewModel;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
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
        model.addAttribute("battleViewModel", new BattleViewModel());
        model.addAttribute("allCharacters", characterService.getAllCharacters());
        return "addBattle";
    }

    @PostMapping("/battles/add")
    public String addBattle(@Valid @ModelAttribute("battleViewModel") BattleViewModel viewModel,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.debug("Add battle form has {} error(s)", bindingResult.getErrorCount());
            model.addAttribute("allCharacters", characterService.getAllCharacters());
            return "addBattle";
        }
        battleService.addBattle(viewModel.getName(), viewModel.getLocation(),
                viewModel.getDate(), viewModel.getWinner(), viewModel.getCharacterIds());
        return "redirect:/battles";
    }
}
