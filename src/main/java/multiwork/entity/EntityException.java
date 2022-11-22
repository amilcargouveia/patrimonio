package multiwork.entity;

/**
 * Representa a classe base das exce��es lan�adas pela classes que representam
 * entidades na aplica��o.
 */
public class EntityException extends Exception {

  public EntityException(String message) {
    super(message);
  }

}
