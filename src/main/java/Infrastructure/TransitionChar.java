package Infrastructure;

public class TransitionChar {
    public boolean isLambda;
    public char character;

    public TransitionChar(){
        isLambda = true;
        character = 'λ';
    }

    public TransitionChar(char character){
        this.character = character;
        isLambda = character == 'λ';
    }

    @Override
    public int hashCode() {
        return character;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass())
            return false;
        var objAsTransitionChar = (TransitionChar) obj;
        return objAsTransitionChar.isLambda == isLambda && objAsTransitionChar.character == character;
    }
}
