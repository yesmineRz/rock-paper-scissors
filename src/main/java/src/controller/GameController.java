package src.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import src.dto.request.DtoPostSingleRoundResultRequest;
import src.dto.response.DtoGetMultipleRoundResultResponse;
import src.dto.response.DtoPostSingleRoundResultResponse;
import src.service.GameService;

import javax.validation.Valid;

@RestController
public class GameController {

    private GameService gameService;

    @Value("${rounds.number}")
    private int roundsNumber;

    @Value("${default.playerA}")
    private char defaultPlayerA;

    @Autowired
    public GameController(GameService gameService){
        this.gameService = gameService;
    }

    @PostMapping("/single")
    public DtoPostSingleRoundResultResponse playSingleRound(@Valid @RequestBody DtoPostSingleRoundResultRequest requestDto) throws Exception {
        return gameService.playSingleRound(requestDto.choiceA.choiceCharacter(), requestDto.choiceB.choiceCharacter());
    }

    @GetMapping("/multiple")
    public DtoGetMultipleRoundResultResponse playMultipleRounds() throws Exception {
        return gameService.playMultipleRounds(roundsNumber, defaultPlayerA);
    }


}