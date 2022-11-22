package multiwork.entity;

/**
 * Representa a exceção lançada quando mais de um registro foi encontrado
 * enquanto apenas um registro era esperado.
 */
public class ManyRecordsFoundException extends EntityException {

  public ManyRecordsFoundException(String message) {
    super(message);
  }

}
