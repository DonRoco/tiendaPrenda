package exception;

public class CategoriaNoEncontradaException extends Exception {

    public CategoriaNoEncontradaException() {
    }
   
    public CategoriaNoEncontradaException(String msg) {
        super(msg);
    }
}
