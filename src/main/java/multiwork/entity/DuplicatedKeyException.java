package multiwork.entity;

/**
 * Representa a exceção lançada quando é encontrada uma chave única duplicada.
 */
public class DuplicatedKeyException extends EntityException {

  public DuplicatedKeyException(String message) {
    super(message);
  }

}
