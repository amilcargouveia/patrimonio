package multiwork.entity;

/**
 * Representa a exce��o lan�ada quando um registro esperado n�o � encontrado.
 */
public class RecordNotFoundException extends EntityException {

  public RecordNotFoundException(String message) {
    super(message);
  }

}
