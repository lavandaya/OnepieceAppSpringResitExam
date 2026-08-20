package be.kdg.programming3.onepiece.presentation.controller;

import be.kdg.programming3.onepiece.business.domain.Character;
import be.kdg.programming3.onepiece.business.domain.Powertype;
import be.kdg.programming3.onepiece.business.service.CharacterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CharacterController {
    private static final Logger logger = LoggerFactory.getLogger(CharacterController.class);

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping({"/", "/characters"})
    public String showCharacters(@RequestParam(required = false) Powertype powertype, Model model) {
        logger.debug("Loading characters page (powertype filter = {})", powertype);
        List<Character> characters = powertype == null
                ? characterService.getAllCharacters()
                : characterService.getCharactersByPowertype(powertype);

        model.addAttribute("characters", characters);
        model.addAttribute("powertypes", Powertype.values());
        model.addAttribute("selectedPowertype", powertype);
        return "characters";
    }

    @GetMapping("/characters/add")
    public String showAddCharacterForm(Model model) {
        model.addAttribute("powertypes", Powertype.values());
        return "addCharacter";
    }

    @PostMapping("/characters/add")
    public String addCharacter(@RequestParam String name, @RequestParam int age,
                                @RequestParam String appearance, @RequestParam Powertype powertype,
                                @RequestParam double power) {
        logger.debug("Adding character '{}' via web form", name);
        characterService.addCharacter(name, age, appearance, powertype, power);
        return "redirect:/characters";
    }

    @GetMapping("/characters/{id}")
    public String showCharacterDetail(@PathVariable int id, Model model) {
        logger.debug("Loading detail page for character id={}", id);
        return characterService.getCharacterById(id)
                .map(character -> {
                    model.addAttribute("character", character);
                    return "characterDetail";
                })
                .orElse("redirect:/characters");
    }
}
