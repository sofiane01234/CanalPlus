package canalplus.testtechnique.sofiane.exception;

public class NotFoundException extends MetierException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException() {
        this("Entity not found");
    }

}
