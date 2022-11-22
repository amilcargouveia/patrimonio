
<%@include file="../include/beans.jsp"%>
<%@page import="java.sql.*"%>

<%!
  // constantes
  String ACTION_MOVIMENTACAO = "movimentacao";
  String ACTION_MOVIMENTACAO_CADASTRO = "movimentacaoCadastro";
  String CLASS_MOVIMENTACAO = "multiwork.entity.Movimentacao";
  String DELETE = "DELETE";
  String INSERT = "INSERT";
  String CODIGO_BEM = "codigoBem";
  String CLASS_SETOR = "multiwork.entity.Setor";

  public String getRowClass(int rowIndex) {
    return (rowIndex % 2 > 0) ? "GridRowEven" : "GridRowOdd";
  }

  // Recebe o código do Setor e retorna sua descrição
  public String getSetor(Setor setor,
  		          int   codigoSetor) throws Exception {
    try {
      return setor.getByCodigo(codigoSetor).getDescricao();
    }
    catch (Exception e) {
      return e.getMessage();
    }
  }
%>

<%
  // comando para realizar
  String command = request.getParameter(Controller.COMMAND);
  if (command == null)
    command = "";
  // nossa instância de Movimentação
  Movimentacao movimentacao = (Movimentacao)facade.getEntity(CLASS_MOVIMENTACAO);
  // nossa instância de Setor
  Setor setor = (Setor)facade.getEntity(CLASS_SETOR);
  // quer excluir?
  if (command.equals(DELETE)) {
    // chave do registro para excluir
    int codigo = Integer.parseInt(request.getParameter(Movimentacao.FIELD_CODIGO));
    // info do registro que iremos excluir
    MovimentacaoInfo movimentacaoInfo = movimentacao.getByCodigo(codigo);
    // exclui
    movimentacao.delete(movimentacaoInfo);
  } // if

  // chave de pesquisa
  String codigoBem = request.getParameter(CODIGO_BEM);
  // lista que iremos exibir
  MovimentacaoInfo[] movimentacaoInfoList = movimentacao.getByCodigoBem(Integer.parseInt(codigoBem));

%>

<html>
<head>
<title>Movimentação</title>
<link href="css/default.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="5" topmargin="5" rightmargin="5" bottommargin="5">

<script language="javascript">

  // controla a submissão do form para evitar
  // multiplicidade de requisições
  formSubmited = false;

  function editMovimentacao(codigo) {
    // inclusão
    window.open("controller?<%=Controller.ACTION%>=<%=ACTION_MOVIMENTACAO_CADASTRO%>"
              + "&<%=Movimentacao.FIELD_CODIGO%>=" + codigo,
                "movimentacao",
                "left=0,top=0,width=350,height=240");
  }

  function insertMovimentacao() {
    // inclusão
    window.open("controller?<%=Controller.ACTION%>=<%=ACTION_MOVIMENTACAO_CADASTRO%>"
              + "&<%=Controller.COMMAND%>=<%=INSERT%>"
              + "&<%=CODIGO_BEM%>=<%=codigoBem%>",
                "movimentacao",
                "left=0,top=0,width=350,height=240");
  }

  function searchMovimentacao() {
    // se o form já foi submetido...aguarde
    if (formSubmited) {
      alert("Aguarde enquanto a operação é concluída.");
      return;
    } // if
    // chave
    codigoBem = formMovimentacao.<%=CODIGO_BEM%>;
    // se não selecionou nada...dispara
    if (codigoBem.value == "") {
      alert("Informe todos os valores para pesquisar.");
      return;
    } // if
    // submete o form
    formSubmited = true;
    formMovimentacao.submit();
  }

</script>

<!-- form -->
<form name="formMovimentacao" action="controller">
  <!-- controle de navegação -->
  <input type="hidden" name="<%=Controller.ACTION%>" value="<%=ACTION_MOVIMENTACAO%>">
  <input type="hidden" name="<%=Controller.COMMAND%>" value="">
  <!-- chave do registro selecionado -->
  <input type="hidden" name="<%=Movimentacao.FIELD_CODIGO%>" value="">
  <input type="hidden" name="<%=CODIGO_BEM%>" value="<%=codigoBem%>">
  <!-- lista -->
  <table class="Grid" style="width:500px;">
    <tr class="GridHeader">
      <td align="left">Data</td>
      <td align="left">Setor Origem</td>
      <td align="left">Setor Destino</td>
    </tr>
    <%// loop nos itens
     for (int i=0; i<movimentacaoInfoList.length; i++) {
       // Movimentação da vez
       MovimentacaoInfo movimentacaoInfo = movimentacaoInfoList[i];%>
    <tr class="<%=getRowClass(i)%>">
      <td align="left"><a href="javascript:editMovimentacao(<%=movimentacaoInfo.getCodigo()%>);"
                          title="Editar..."><%=DateTools.dateToString(movimentacaoInfo.getData())%></a></td>
      <td align="left"><%=getSetor(setor, movimentacaoInfo.getCodigoSetorOrigem())%></td>
      <td align="left"><%=getSetor(setor, movimentacaoInfo.getCodigoSetorDestino())%></td>
    </tr>
  <%}%>
  </table>
  <br>
  <!--botões de comando-->
  <button onclick="insertMovimentacao();">Incluir</button>&nbsp;&nbsp;&nbsp;
</form>

</body>
</html>
