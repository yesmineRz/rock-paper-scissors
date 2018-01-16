package src.dto.request;

import src.tools.ChoiceEnum;

import javax.validation.constraints.NotNull;


public class DtoPostSingleRoundResultRequest {

    @NotNull
    public ChoiceEnum choiceA;

    @NotNull
    public ChoiceEnum choiceB;
}
