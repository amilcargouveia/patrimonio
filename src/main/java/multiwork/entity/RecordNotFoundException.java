package multiwork.entity;

/**
 * Representa a exceção lançada quando um registro esperado não é encontrado.
 */
public class RecordNotFoundException extends EntityException {

  public RecordNotFoundException(String message) {
    super(message);
  }

}
