package multiwork.entity;

/**
 * Exce��o lan�ada quando um registro que � chave estrangeira de outro
 * est� para ser exclu�do.
 */
public class ForeignKeyConstraintException extends EntityException {

  public ForeignKeyConstraintException(String message) {
    super(message);
  }

}
