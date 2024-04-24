package cinema.exceptions;

public class AlreadyPurchaseException extends BusinessException{
    public AlreadyPurchaseException() {
        super("The ticket has been already purchased!");
    }
}
