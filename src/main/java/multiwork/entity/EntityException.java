package multiwork.entity;

/**
 * Representa a classe base das exceções lançadas pela classes que representam
 * entidades na aplicação.
 */
public class EntityException extends Exception {

  public EntityException(String message) {
    super(message);
  }

}
