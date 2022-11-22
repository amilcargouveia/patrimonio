package multiwork.process;

import java.sql.*;

import multiwork.entity.*;

/**
 * Representa o processo de movimenta��o de bens.
 */
public class ProcessoMovimentacao extends Process {

  /**
   * Realiza a movimenta��o do Bem indicada por 'movimentacaoInfo'.
   * @param movimentacaoInfo MovimentacaoInfo que representa a movimenta��o que
   *                         ser� efetuada.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco
   *                   ou na execu��o do processo.
   */
  public void moveBem(MovimentacaoInfo movimentacaoInfo) throws Exception {
    // trata a transa��o
    getConnection().setAutoCommit(false);
    try {
      // se os setores de origem e destino s�o os mesmos...
      if (movimentacaoInfo.getCodigoSetorOrigem() == movimentacaoInfo.getCodigoSetorDestino())
        throw new ProcessException("O setor de destino n�o pode ser o mesmo de origem.");
      // nossos objetos de neg�cio
      Bem bem = new Bem();
      bem.setConnection(getConnection());
      Movimentacao movimentacao = new Movimentacao();
      movimentacao.setConnection(getConnection());

      // Bem movimentado
      BemInfo bemInfo = bem.getByCodigo(movimentacaoInfo.getCodigoBem());
      // altera seu setor
      bemInfo.setCodigoSetor(movimentacaoInfo.getCodigoSetorDestino());
      // altera data de �ltima movimenta��o
      Timestamp now = new Timestamp((new java.util.Date()).getTime());
      bemInfo.setUltimaMovimentacao(now);
      // atualiza o bem
      bem.update(bemInfo);

      // insere a movimenta��o
      movimentacao.insert(movimentacaoInfo);
    }
    finally {
      // retorna o controle de transa��o
      getConnection().setAutoCommit(true);
    } // try-finally
  }

}
