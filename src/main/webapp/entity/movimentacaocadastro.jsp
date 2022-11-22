
<%@include file="../include/beans.jsp"%>
<%@page import="java.sql.*"%>

<%!
  // constantes
  String ACTION_MOVIMENTACAO = "movimentacao";
  String ACTION_MOVIMENTACAO_CADASTRO = "movimentacaoCadastro";
  String CLASS_MOVIMENTACAO = "multiwork.entity.Movimentacao";
  String INSERT = "INSERT";
  String POST   = "POST";
  String CODIGO_BEM = "codigoBem";
  String CLASS_SETOR = "multiwork.entity.Setor";
  String CLASS_BEM = "multiwork.entity.Bem";
  String CLASS_PROCESSO_MOVIMENTACAO = "multiwork.process.ProcessoMovimentacao";

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
  // nossa instância de Bem
  Bem bem = (Bem)facade.getEntity(CLASS_BEM);
  // MovimentacaoInfo para editar
  MovimentacaoInfo movimentacaoInfo = null;
  // nossa instância de Processo Movimentação
  ProcessoMovimentacao processoMovimentacao = (ProcessoMovimentacao)facade.getProcess(CLASS_PROCESSO_MOVIMENTACAO);
  // se quer incluir...
  if (command.equals(INSERT)) {
    // código do bem
    int codigoBem = Integer.parseInt(request.getParameter(CODIGO_BEM));
    // pega o Bem referente
    BemInfo bemInfo = bem.getByCodigo(codigoBem);
    // info em branco
    movimentacaoInfo =
      new MovimentacaoInfo(
            0,
            codigoBem,
            new Timestamp((new java.util.Date()).getTime()),
            bemInfo.getCodigoSetor(),
            0,
            ""
          );
  }
  // se quer salvar...
  else if (command.equals(POST)) {
    // info preenchido na página
    movimentacaoInfo =
      new MovimentacaoInfo(
            Integer.parseInt(request.getParameter(Movimentacao.FIELD_CODIGO)),
            Integer.parseInt(request.getParameter(Movimentacao.FIELD_CODIGO_BEM)),
            DateTools.stringDateToTimestamp(request.getParameter(Movimentacao.FIELD_DATA)),
            Integer.parseInt(request.getParameter(Movimentacao.FIELD_CODIGO_SETOR_ORIGEM)),
            Integer.parseInt(request.getParameter(Movimentacao.FIELD_CODIGO_SETOR_DESTINO)),
            request.getParameter(Movimentacao.FIELD_DESCRICAO)
          );
    // se está salvando um novo...chama o processo
    if (request.getParameter(INSERT) != null)
      processoMovimentacao.moveBem(movimentacaoInfo);
    // se não está salvando um novo...atualiza
    else
      movimentacao.update(movimentacaoInfo);
    %>
      <script language="javascript">
        window.opener.parent.location.reload();
        window.close();
      </script>
    <%
    return;
  }
  // se quer editar...
  else {
    // info do registro para editar
    movimentacaoInfo = movimentacao.getByCodigo(Integer.parseInt(request.getParameter(Movimentacao.FIELD_CODIGO)));
  } // if

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

  function post() {
    // se o form já foi submetido...aguarde
    if (formSubmited) {
      alert("Aguarde enquanto a operação é concluída.");
      return;
    } // if
    // se não confirmou...dispara
    if (!confirm("Deseja mesmo salvar o(a) Movimentação?"))
      return;
    // valida os dados
    if (!validate(formMovimentacao.<%=Movimentacao.FIELD_CODIGO%>, "Código"))
       return;
    // altera o comando
    formMovimentacao.<%=Controller.COMMAND%>.value = "<%=POST%>";
    // envia o formulário
    formSubmited = true;
    formMovimentacao.submit();
  }

  function validate(input, title) {
    // se não informou o valor
    if (input.value == "") {
      // põe o foco
      input.focus();
      // avisa
      alert("Informe o valor para " + title);
      // retorna falso
      return false;
    }
    // se informou...retorna ok
    else
      return true;
  }

</script>

<!-- título -->
<h2>Movimentação</h2>
<!-- form -->
<form name="formMovimentacao" action="controller">
  <input type="hidden" name="<%=Controller.ACTION%>" value="<%=ACTION_MOVIMENTACAO_CADASTRO%>">
  <input type="hidden" name="<%=Controller.COMMAND%>" value="">
  <input type="hidden" name="<%=Movimentacao.FIELD_CODIGO%>" value="<%=movimentacaoInfo.getCodigo()%>">
  <input type="hidden" name="<%=Movimentacao.FIELD_CODIGO_BEM%>" value="<%=movimentacaoInfo.getCodigoBem()%>">
  <%if (command.equals(INSERT)) {%>
    <input type="hidden" name="<%=INSERT%>" value="true">
  <%} // if%>
  <!-- dados de Movimentação -->
  <table class="BtnFace">
    <!-- campo Data -->
    <tr>
      <td>Data</td>
      <td>
      <%if (command.equals(INSERT)) {%>
        <input type="text"
               name="<%=Movimentacao.FIELD_DATA%>"
               value="<%=DateTools.dateToString(movimentacaoInfo.getData())%>"
               style="width:70px;"
               maxlength="10"> dd/mm/aaaa
      <%}
        else {%>
        <input type="hidden" name="<%=Movimentacao.FIELD_DATA%>" value="<%=DateTools.dateToString(movimentacaoInfo.getData())%>"/>
        <span><b><%=DateTools.dateToString(movimentacaoInfo.getData())%></b></span>
      <%} // if%>
      </td>
    </tr>
    <!-- campo Código Setor Origem -->
    <tr>
      <td>Setor Origem</td>
      <td>
        <input type="hidden" name="<%=Movimentacao.FIELD_CODIGO_SETOR_ORIGEM%>" value="<%=movimentacaoInfo.getCodigoSetorOrigem()%>"/>
        <span><b><%=getSetor(setor, movimentacaoInfo.getCodigoSetorOrigem())%></b></span>
      </td>
    </tr>
    <!-- campo Código Setor Destino -->
    <tr>
      <td>Setor Destino</td>
      <td>
      <%if (command.equals(INSERT)) {%>
         <%
          String[] setorDestinoDisplayFieldNames = {Setor.FIELD_DESCRICAO};
          String[] setorDestinoReturnFieldNames  = {Setor.FIELD_CODIGO};
          String[] setorDestinoReturnFieldValues = {movimentacaoInfo.getCodigoSetorDestino() + ""};
          String[] setorDestinoOrderFieldNames   = {Setor.FIELD_DESCRICAO};
          %>
          <%=LookupList.script(facade.connection(),
                               Setor.TABLE_SETOR,
                               setorDestinoDisplayFieldNames,
                               setorDestinoReturnFieldNames,
                               setorDestinoReturnFieldValues,
                               setorDestinoOrderFieldNames,
                               "",
                               LookupList.SELECT_TYPE_SINGLE,
                               Movimentacao.FIELD_CODIGO_SETOR_DESTINO,
                               "width:250px;",
                               "")%>
      <%}
        else {%>
        <input type="hidden" name="<%=Movimentacao.FIELD_CODIGO_SETOR_DESTINO%>" value="<%=movimentacaoInfo.getCodigoSetorDestino()%>" />
        <span><b><%=getSetor(setor, movimentacaoInfo.getCodigoSetorDestino())%></b></span>
      <%} // if%>
      </td>
    </tr>
    <!-- campo Descrição -->
    <tr>
      <td>Descrição</td>
      <td>
        <input type="text"
               name="<%=Movimentacao.FIELD_DESCRICAO%>"
               value="<%=movimentacaoInfo.getDescricao()%>"
               style="width:250px;">
      </td>
    </tr>
  </table>
  <br>
  <!--botões de comando-->
  <button onclick="post();">Salvar</button>
</form>
<button id="buttonFechar" onclick="window.close();">Fechar</button>

</body>
</html>
