package multiwork.process;

import java.sql.*;

import multiwork.entity.*;

/**
 * Representa o processo de movimentação de bens.
 */
public class ProcessoMovimentacao extends Process {

  /**
   * Realiza a movimentação do Bem indicada por 'movimentacaoInfo'.
   * @param movimentacaoInfo MovimentacaoInfo que representa a movimentação que
   *                         será efetuada.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco
   *                   ou na execução do processo.
   */
  public void moveBem(MovimentacaoInfo movimentacaoInfo) throws Exception {
    // trata a transação
    getConnection().setAutoCommit(false);
    try {
      // se os setores de origem e destino são os mesmos...
      if (movimentacaoInfo.getCodigoSetorOrigem() == movimentacaoInfo.getCodigoSetorDestino())
        throw new ProcessException("O setor de destino não pode ser o mesmo de origem.");
      // nossos objetos de negócio
      Bem bem = new Bem();
      bem.setConnection(getConnection());
      Movimentacao movimentacao = new Movimentacao();
      movimentacao.setConnection(getConnection());

      // Bem movimentado
      BemInfo bemInfo = bem.getByCodigo(movimentacaoInfo.getCodigoBem());
      // altera seu setor
      bemInfo.setCodigoSetor(movimentacaoInfo.getCodigoSetorDestino());
      // altera data de última movimentação
      Timestamp now = new Timestamp((new java.util.Date()).getTime());
      bemInfo.setUltimaMovimentacao(now);
      // atualiza o bem
      bem.update(bemInfo);

      // insere a movimentação
      movimentacao.insert(movimentacaoInfo);
    }
    finally {
      // retorna o controle de transação
      getConnection().setAutoCommit(true);
    } // try-finally
  }

}
