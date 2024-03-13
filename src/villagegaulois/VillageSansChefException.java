package villagegaulois;

public class VillageSansChefException extends Exception {
    public VillageSansChefException() {
        super("Le village doit avoir un chef !");
    }

    public VillageSansChefException(String message) {
        super(message);
    }
}

