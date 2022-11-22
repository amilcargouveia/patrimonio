
<%@include file="../include/beans.jsp"%>
<%@page import="java.sql.*"%>

<%!
  // constantes
  String ACTION_GRUPO_BEM = "grupobem";
  String ACTION_GRUPO_BEM_CADASTRO = "grupobemCadastro";
  String CLASS_GRUPO_BEM = "multiwork.entity.GrupoBem";
  String DELETE = "DELETE";
  String INSERT = "INSERT";
  String DESCRICAO = "descricao";

  // fun��es
  public String actionCadastro(int codigo) {
    return "controller?" + Controller.ACTION + "=" + ACTION_GRUPO_BEM_CADASTRO
         + "&" + GrupoBem.FIELD_CODIGO + "=" + codigo;
  }

  public String getRowClass(int rowIndex) {
    return (rowIndex % 2 > 0) ? "GridRowEven" : "GridRowOdd";
  }
%>

<%
  // comando para realizar
  String command = request.getParameter(Controller.COMMAND);
  if (command == null)
    command = "";
  // nossa inst�ncia de Grupo Bem
  GrupoBem grupobem = (GrupoBem)facade.getEntity(CLASS_GRUPO_BEM);
  // quer excluir?
  if (command.equals(DELETE)) {
    // chave do registro para excluir
    int codigo = Integer.parseInt(request.getParameter(GrupoBem.FIELD_CODIGO));
    // info do registro que iremos excluir
    GrupoBemInfo grupobemInfo = grupobem.getByCodigo(codigo);
    // exclui
    grupobem.delete(grupobemInfo);
  } // if

  // lista que iremos exibir
  GrupoBemInfo[] grupobemInfoList = new GrupoBemInfo[0];
  // chave de pesquisa
  String descricao = request.getParameter(DESCRICAO);
  if (descricao == null)
    descricao = "";
  // se temos uma chave de pesquisa...
  if ((!descricao.equals("")))
    grupobemInfoList = grupobem.getByDescricao(descricao);

%>

<html>
<head>
<title>Grupo Bem</title>
<link href="css/default.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="5" topmargin="5" rightmargin="5" bottommargin="5">

<script language="javascript">

  // controla a submiss�o do form para evitar
  // multiplicidade de requisi��es
  formSubmited = false;

  function deleteGrupoBem() {
    // se o form j� foi submetido...aguarde
    if (formSubmited) {
      alert("Aguarde enquanto a opera��o � conclu�da.");
      return;
    } // if
    // chave
    codigo = formGrupoBem.<%=GrupoBem.FIELD_CODIGO%>;
    // se n�o selecionou nada...dispara
    if ((codigo.value == "")) {
      alert("Selecione o(a) Grupo Bem para ser exclu�do(a).");
      return;
    } // if
    // se n�o quer excluir...dispara
    if (!confirm("Deseja realmente excluir o(a) Grupo Bem selecionado(a)?"))
      return;
    // informa que o comando ser� exclus�o
    formGrupoBem.<%=Controller.COMMAND%>.value = "<%=DELETE%>";
    // submete o form
    formSubmited = true;
    formGrupoBem.submit();
  }

  function insertGrupoBem() {
    // a��o para inclus�o
    window.location.href = "controller?<%=Controller.ACTION%>=<%=ACTION_GRUPO_BEM_CADASTRO%>"
                         + "&<%=Controller.COMMAND%>=<%=INSERT%>";
  }

  function searchGrupoBem() {
    // se o form j� foi submetido...aguarde
    if (formSubmited) {
      alert("Aguarde enquanto a opera��o � conclu�da.");
      return;
    } // if
    // chave
    descricao = formGrupoBem.<%=DESCRICAO%>;
    // se n�o selecionou nada...dispara
    if ((descricao.value == "")) {
      alert("Informe todos os valores para pesquisar.");
      return;
    } // if
    // submete o form
    formSubmited = true;
    formGrupoBem.submit();
  }

</script>

<!--conte�do-->
<table class="BtnFace" cellpadding="5" style="width:100%; height:100%; border:1px solid;">
  <tr>
    <td valign="top">
      <!-- t�tulo -->
      <h2>Grupo Bem</h2>
      <!-- form -->
      <form name="formGrupoBem" action="controller">
        <!-- controle de navega��o -->
        <input type="hidden"
               name="<%=Controller.ACTION%>"
               value="<%=ACTION_GRUPO_BEM%>">
        <input type="hidden"
               name="<%=Controller.COMMAND%>"
               value="">
        <!-- chave do registro selecionado -->
        <input type="hidden"
               name="<%=GrupoBem.FIELD_CODIGO%>"
               value="">
        <!-- pesquisa -->
        <table style="border:1px solid;">
          <tr>
            <td>
              <table class="BtnFace">
                <tr>
                  <td>Descri��o</td>
                  <td><input type="text"
                             name="<%=DESCRICAO%>"
                             value="<%=descricao%>"
                             style="width:150px;"></td>
                </tr>
                <tr>
                  <td></td>
                  <td><button onclick="searchGrupoBem();">Pesquisar</button></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
        <br/>
        <!-- lista -->
        <table class="Grid" style="width:550px;">
          <tr class="GridHeader">
            <td style="width:20px;" align="center">!</td>
            <td align="left">Descri��o</td>
          </tr>
          <%// loop nos itens
           for (int i=0; i<grupobemInfoList.length; i++) {
             // Grupo Bem da vez
             GrupoBemInfo grupobemInfo = grupobemInfoList[i];%>
          <tr class="<%=getRowClass(i)%>">
            <td align="center">
              <input type="radio"
                     name="radioDelete"
                     onclick="javascript:<%=GrupoBem.FIELD_CODIGO%>.value='<%=grupobemInfo.getCodigo()%>';">
            </td>
            <td align="left"><a href="<%=actionCadastro(grupobemInfo.getCodigo())%>"
                                title="Editar..."><%=grupobemInfo.getDescricao()%></a></td>
          </tr>
        <%}%>
        </table>
        <br>
        <!--bot�es de comando-->
        <button onclick="insertGrupoBem();">Incluir</button>&nbsp;&nbsp;&nbsp;
        <button onclick="deleteGrupoBem();">Excluir</button>
      </form>
    </td>
  </tr>
 </table>
</body>
</html>
