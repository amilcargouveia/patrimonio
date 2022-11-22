package multiwork.process;

/**
 * Representa a classe base das exce��es lan�adas pelas classes que representam
 * processos na aplica��o.
 */
public class ProcessException extends Exception {

  public ProcessException(String message) {
    super(message);
  }

}
