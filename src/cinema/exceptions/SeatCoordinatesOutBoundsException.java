package cinema.exceptions;

public class SeatCoordinatesOutBoundsException extends BusinessException {
    public SeatCoordinatesOutBoundsException() {
        super("The number of a row or a column is out of bounds!");
    }
}
