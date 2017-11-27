package exception;

public class PedidoNoEncontradoException extends Exception {

    public PedidoNoEncontradoException() {
    }

    public PedidoNoEncontradoException(String msg) {
        super(msg);
    }
}
