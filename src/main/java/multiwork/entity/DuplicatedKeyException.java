package multiwork.entity;

/**
 * Representa a exce��o lan�ada quando � encontrada uma chave �nica duplicada.
 */
public class DuplicatedKeyException extends EntityException {

  public DuplicatedKeyException(String message) {
    super(message);
  }

}
