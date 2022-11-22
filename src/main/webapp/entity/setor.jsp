
<%@include file="../include/beans.jsp"%>
<%@page import="java.sql.*"%>

<%!
  // constantes
  String ACTION_SETOR = "setor";
  String ACTION_SETOR_CADASTRO = "setorCadastro";
  String CLASS_SETOR = "multiwork.entity.Setor";
  String DELETE = "DELETE";
  String INSERT = "INSERT";
  String DESCRICAO = "descricao";

  // funções
  public String actionCadastro(int codigo) {
    return "controller?" + Controller.ACTION + "=" + ACTION_SETOR_CADASTRO
         + "&" + Setor.FIELD_CODIGO + "=" + codigo;
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
  // nossa instância de Setor
  Setor setor = (Setor)facade.getEntity(CLASS_SETOR);
  // quer excluir?
  if (command.equals(DELETE)) {
    // chave do registro para excluir
    int codigo = Integer.parseInt(request.getParameter(Setor.FIELD_CODIGO));
    // info do registro que iremos excluir
    SetorInfo setorInfo = setor.getByCodigo(codigo);
    // exclui
    setor.delete(setorInfo);
  } // if

  // lista que iremos exibir
  SetorInfo[] setorInfoList = new SetorInfo[0];
  // chave de pesquisa
  String descricao = request.getParameter(DESCRICAO);
  if (descricao == null)
    descricao = "";
  // se temos uma chave de pesquisa...
  if ((!descricao.equals("")))
    setorInfoList = setor.getByDescricao(descricao);

%>

<html>
<head>
<title>Setor</title>
<link href="css/default.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="5" topmargin="5" rightmargin="5" bottommargin="5">

<script language="javascript">

  // controla a submissão do form para evitar
  // multiplicidade de requisições
  formSubmited = false;

  function deleteSetor() {
    // se o form já foi submetido...aguarde
    if (formSubmited) {
      alert("Aguarde enquanto a operação é concluída.");
      return;
    } // if
    // chave
    codigo = formSetor.<%=Setor.FIELD_CODIGO%>;
    // se não selecionou nada...dispara
    if ((codigo.value == "")) {
      alert("Selecione o(a) Setor para ser excluído(a).");
      return;
    } // if
    // se não quer excluir...dispara
    if (!confirm("Deseja realmente excluir o(a) Setor selecionado(a)?"))
      return;
    // informa que o comando será exclusão
    formSetor.<%=Controller.COMMAND%>.value = "<%=DELETE%>";
    // submete o form
    formSubmited = true;
    formSetor.submit();
  }

  function insertSetor() {
    // ação para inclusão
    window.location.href = "controller?<%=Controller.ACTION%>=<%=ACTION_SETOR_CADASTRO%>"
                         + "&<%=Controller.COMMAND%>=<%=INSERT%>";
  }

  function searchSetor() {
    // se o form já foi submetido...aguarde
    if (formSubmited) {
      alert("Aguarde enquanto a operação é concluída.");
      return;
    } // if
    // chave
    descricao = formSetor.<%=DESCRICAO%>;
    // se não selecionou nada...dispara
    if ((descricao.value == "")) {
      alert("Informe todos os valores para pesquisar.");
      return;
    } // if
    // submete o form
    formSubmited = true;
    formSetor.submit();
  }

</script>

<!--conteúdo-->
<table class="BtnFace" cellpadding="5" style="width:100%; height:100%; border:1px solid;">
  <tr>
    <td valign="top">
      <!-- título -->
      <h2>Setor</h2>
      <!-- form -->
      <form name="formSetor" action="controller">
        <!-- controle de navegação -->
        <input type="hidden"
               name="<%=Controller.ACTION%>"
               value="<%=ACTION_SETOR%>">
        <input type="hidden"
               name="<%=Controller.COMMAND%>"
               value="">
        <!-- chave do registro selecionado -->
        <input type="hidden"
               name="<%=Setor.FIELD_CODIGO%>"
               value="">
        <!-- pesquisa -->
        <table style="border:1px solid;">
          <tr>
            <td>
              <table class="BtnFace">
                <tr>
                  <td>Descrição</td>
                  <td><input type="text"
                             name="<%=DESCRICAO%>"
                             value="<%=descricao%>"
                             style="width:150px;"></td>
                </tr>
                <tr>
                  <td></td>
                  <td><button onclick="searchSetor();">Pesquisar</button></td>
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
            <td align="left">Descrição</td>
          </tr>
          <%// loop nos itens
           for (int i=0; i<setorInfoList.length; i++) {
             // Setor da vez
             SetorInfo setorInfo = setorInfoList[i];%>
          <tr class="<%=getRowClass(i)%>">
            <td align="center">
              <input type="radio"
                     name="radioDelete"
                     onclick="javascript:<%=Setor.FIELD_CODIGO%>.value='<%=setorInfo.getCodigo()%>';">
            </td>
            <td align="left"><a href="<%=actionCadastro(setorInfo.getCodigo())%>"
                                title="Editar..."><%=setorInfo.getDescricao()%></a></td>
          </tr>
        <%}%>
        </table>
        <br>
        <!--botões de comando-->
        <button onclick="insertSetor();">Incluir</button>&nbsp;&nbsp;&nbsp;
        <button onclick="deleteSetor();">Excluir</button>
      </form>
    </td>
  </tr>
 </table>
</body>
</html>
