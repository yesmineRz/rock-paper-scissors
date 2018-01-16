package src.service;

import org.springframework.stereotype.Service;
import src.dto.response.DtoGetMultipleRoundResultResponse;
import src.dto.response.DtoPostSingleRoundResultResponse;


@Service
public class GameService {

    public DtoPostSingleRoundResultResponse playSingleRound(Character playerA, Character playerB) throws Exception{

        DtoPostSingleRoundResultResponse resultDto = new DtoPostSingleRoundResultResponse();

        char choiceA = playerA!=null ? playerA : getRandomChoice();
        char choiceB = playerB!=null ? playerB : getRandomChoice();


        int winnerIndex = getWinner(choiceA, choiceB);
        if(winnerIndex>=0 && winnerIndex<=2){
            resultDto.winner = winnerIndex;
        }else {
            throw new Exception("Invalid winner index. Winner index should be between 0 and 2.");
        }

        return resultDto;
    }

    public DtoGetMultipleRoundResultResponse playMultipleRounds(int roundsNumber, Character defaultPlayerA) throws Exception{

        DtoGetMultipleRoundResultResponse resultDto = new DtoGetMultipleRoundResultResponse();

        int winsA = 0;
        int winsB = 0;
        int ties = 0;

        int winnerIndex;

        for(int i=0; i<roundsNumber; i++){
            char playerB = getRandomChoice();
            winnerIndex = getWinner(defaultPlayerA,playerB);
            switch (winnerIndex){
                case 0:
                    ties++;
                    break;
                case 1:
                    winsA++;
                    break;
                case 2:
                    winsB++;
                    break;
                default:
                    throw new Exception("Invalid winner index. Winner index should be between 0 and 2.");
            }
        }

        String resultString = String.format("Player A wins %d of %d games.\n" +
                        "Player B wins %d of %d games.\n" +
                        "Tie: %d of %d games.",
                winsA, roundsNumber, winsB, roundsNumber, ties, roundsNumber);

        System.out.print(resultString);

        resultDto.winsPlayerA = winsA;
        resultDto.winsPlayerB = winsB;
        resultDto.ties = ties;

        return resultDto;
    }

    private int getWinner(char playerA, char playerB) throws Exception {

        String playedCombination =  Character.toString(playerA).concat(Character.toString(playerB));

        switch (playedCombination){
            case "RP":
            case "SR":
            case "PS":
                return 2;
            case "PR":
            case "RS":
            case "SP":
                return 1;
            case "RR":
            case "PP":
            case "SS":
                return 0;
            default:
                throw new Exception("Invalid played values. Valid values are: P, R, S.");
        }
    }

    private char getRandomChoice() throws Exception {
        int randomNumber = (int) (Math.random()*(3-0))+0;
        switch (randomNumber){
            case 0:
                return 'R';
            case 1:
                return 'P';
            case 2:
                return 'S';
            default:
                throw new Exception("Invalid random number. Number Should be between 0 and 2.");
        }
    }
}
