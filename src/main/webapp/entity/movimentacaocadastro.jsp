
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

  // Recebe o c�digo do Setor e retorna sua descri��o
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
  // nossa inst�ncia de Movimenta��o
  Movimentacao movimentacao = (Movimentacao)facade.getEntity(CLASS_MOVIMENTACAO);
  // nossa inst�ncia de Setor
  Setor setor = (Setor)facade.getEntity(CLASS_SETOR);
  // nossa inst�ncia de Bem
  Bem bem = (Bem)facade.getEntity(CLASS_BEM);
  // MovimentacaoInfo para editar
  MovimentacaoInfo movimentacaoInfo = null;
  // nossa inst�ncia de Processo Movimenta��o
  ProcessoMovimentacao processoMovimentacao = (ProcessoMovimentacao)facade.getProcess(CLASS_PROCESSO_MOVIMENTACAO);
  // se quer incluir...
  if (command.equals(INSERT)) {
    // c�digo do bem
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
    // info preenchido na p�gina
    movimentacaoInfo =
      new MovimentacaoInfo(
            Integer.parseInt(request.getParameter(Movimentacao.FIELD_CODIGO)),
            Integer.parseInt(request.getParameter(Movimentacao.FIELD_CODIGO_BEM)),
            DateTools.stringDateToTimestamp(request.getParameter(Movimentacao.FIELD_DATA)),
            Integer.parseInt(request.getParameter(Movimentacao.FIELD_CODIGO_SETOR_ORIGEM)),
            Integer.parseInt(request.getParameter(Movimentacao.FIELD_CODIGO_SETOR_DESTINO)),
            request.getParameter(Movimentacao.FIELD_DESCRICAO)
          );
    // se est� salvando um novo...chama o processo
    if (request.getParameter(INSERT) != null)
      processoMovimentacao.moveBem(movimentacaoInfo);
    // se n�o est� salvando um novo...atualiza
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
<title>Movimenta��o</title>
<link href="css/default.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="5" topmargin="5" rightmargin="5" bottommargin="5">

<script language="javascript">

  // controla a submiss�o do form para evitar
  // multiplicidade de requisi��es
  formSubmited = false;

  function post() {
    // se o form j� foi submetido...aguarde
    if (formSubmited) {
      alert("Aguarde enquanto a opera��o � conclu�da.");
      return;
    } // if
    // se n�o confirmou...dispara
    if (!confirm("Deseja mesmo salvar o(a) Movimenta��o?"))
      return;
    // valida os dados
    if (!validate(formMovimentacao.<%=Movimentacao.FIELD_CODIGO%>, "C�digo"))
       return;
    // altera o comando
    formMovimentacao.<%=Controller.COMMAND%>.value = "<%=POST%>";
    // envia o formul�rio
    formSubmited = true;
    formMovimentacao.submit();
  }

  function validate(input, title) {
    // se n�o informou o valor
    if (input.value == "") {
      // p�e o foco
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

<!-- t�tulo -->
<h2>Movimenta��o</h2>
<!-- form -->
<form name="formMovimentacao" action="controller">
  <input type="hidden" name="<%=Controller.ACTION%>" value="<%=ACTION_MOVIMENTACAO_CADASTRO%>">
  <input type="hidden" name="<%=Controller.COMMAND%>" value="">
  <input type="hidden" name="<%=Movimentacao.FIELD_CODIGO%>" value="<%=movimentacaoInfo.getCodigo()%>">
  <input type="hidden" name="<%=Movimentacao.FIELD_CODIGO_BEM%>" value="<%=movimentacaoInfo.getCodigoBem()%>">
  <%if (command.equals(INSERT)) {%>
    <input type="hidden" name="<%=INSERT%>" value="true">
  <%} // if%>
  <!-- dados de Movimenta��o -->
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
    <!-- campo C�digo Setor Origem -->
    <tr>
      <td>Setor Origem</td>
      <td>
        <input type="hidden" name="<%=Movimentacao.FIELD_CODIGO_SETOR_ORIGEM%>" value="<%=movimentacaoInfo.getCodigoSetorOrigem()%>"/>
        <span><b><%=getSetor(setor, movimentacaoInfo.getCodigoSetorOrigem())%></b></span>
      </td>
    </tr>
    <!-- campo C�digo Setor Destino -->
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
    <!-- campo Descri��o -->
    <tr>
      <td>Descri��o</td>
      <td>
        <input type="text"
               name="<%=Movimentacao.FIELD_DESCRICAO%>"
               value="<%=movimentacaoInfo.getDescricao()%>"
               style="width:250px;">
      </td>
    </tr>
  </table>
  <br>
  <!--bot�es de comando-->
  <button onclick="post();">Salvar</button>
</form>
<button id="buttonFechar" onclick="window.close();">Fechar</button>

</body>
</html>
