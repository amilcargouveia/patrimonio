package multiwork.entity;

/**
 * Exceção lançada quando um registro que é chave estrangeira de outro
 * está para ser excluído.
 */
public class ForeignKeyConstraintException extends EntityException {

  public ForeignKeyConstraintException(String message) {
    super(message);
  }

}
