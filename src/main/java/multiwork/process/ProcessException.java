package multiwork.process;

/**
 * Representa a classe base das exceções lançadas pelas classes que representam
 * processos na aplicação.
 */
public class ProcessException extends Exception {

  public ProcessException(String message) {
    super(message);
  }

}
