package exception;

public class PrendaNoEncontradaException extends Exception {

    public PrendaNoEncontradaException() {
    }

    /**
     * Constructs an instance of <code>PrendaNoEncontradaException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public PrendaNoEncontradaException(String msg) {
        super(msg);
    }
}
