package src.tools;

public enum ChoiceEnum {
    R("ROCK"),
    P("PAPER"),
    S("SCISSORS");

    private String choice;

    ChoiceEnum(String choice) {
        this.choice = choice;
    }

    public String choice() {
        return choice;
    }

    public Character choiceCharacter() {
        return choice.charAt(0);
    }
}
