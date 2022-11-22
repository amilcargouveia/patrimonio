
<%@include file="../include/beans.jsp"%>
<%@page import="java.sql.*"%>

<%!
  // constantes
  String ACTION_FORNECEDOR = "fornecedor";
  String ACTION_FORNECEDOR_CADASTRO = "fornecedorCadastro";
  String CLASS_FORNECEDOR = "multiwork.entity.Fornecedor";
  String DELETE = "DELETE";
  String INSERT = "INSERT";
  String NOME = "nome";
  String CNPJ = "cnpj";

  // funções
  public String actionCadastro(int codigo) {
    return "controller?" + Controller.ACTION + "=" + ACTION_FORNECEDOR_CADASTRO
         + "&" + Fornecedor.FIELD_CODIGO + "=" + codigo;
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
  // nossa instância de Fornecedor
  Fornecedor fornecedor = (Fornecedor)facade.getEntity(CLASS_FORNECEDOR);
  // quer excluir?
  if (command.equals(DELETE)) {
    // chave do registro para excluir
    int codigo = Integer.parseInt(request.getParameter(Fornecedor.FIELD_CODIGO));
    // info do registro que iremos excluir
    FornecedorInfo fornecedorInfo = fornecedor.getByCodigo(codigo);
    // exclui
    fornecedor.delete(fornecedorInfo);
  } // if

  // lista que iremos exibir
  FornecedorInfo[] fornecedorInfoList = new FornecedorInfo[0];

  // chave de pesquisa
  String nome = request.getParameter(NOME);
  if (nome == null)
    nome = "";
  // se temos uma chave de pesquisa...
  if ((!nome.equals("")))
     fornecedorInfoList = fornecedor.getByNome(nome);

%>

<html>
<head>
<title>Fornecedor</title>
<link href="css/default.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="5" topmargin="5" rightmargin="5" bottommargin="5">

<script language="javascript">

  // controla a submissão do form para evitar
  // multiplicidade de requisições
  formSubmited = false;

  function deleteFornecedor() {
    // se o form já foi submetido...aguarde
    if (formSubmited) {
      alert("Aguarde enquanto a operação é concluída.");
      return;
    } // if
    // chave
    codigo = formFornecedor.<%=Fornecedor.FIELD_CODIGO%>;
    // se não selecionou nada...dispara
    if ((codigo.value == "")) {
      alert("Selecione o(a) Fornecedor para ser excluído(a).");
      return;
    } // if
    // se não quer excluir...dispara
    if (!confirm("Deseja realmente excluir o(a) Fornecedor selecionado(a)?"))
      return;
    // informa que o comando será exclusão
    formFornecedor.<%=Controller.COMMAND%>.value = "<%=DELETE%>";
    // submete o form
    formSubmited = true;
    formFornecedor.submit();
  }

  function insertFornecedor() {
    // ação para inclusão
    window.location.href = "controller?<%=Controller.ACTION%>=<%=ACTION_FORNECEDOR_CADASTRO%>"
                         + "&<%=Controller.COMMAND%>=<%=INSERT%>";
  }

  function searchFornecedor() {
    // se o form já foi submetido...aguarde
    if (formSubmited) {
      alert("Aguarde enquanto a operação é concluída.");
      return;
    } // if
    // chave
    nome = formFornecedor.<%=NOME%>;
    // se não selecionou nada...dispara
    if ((nome.value == "") ){
      alert("Informe todos os valores para pesquisar.");
      return;
    } // if
    // submete o form
    formSubmited = true;
    formFornecedor.submit();
  }

</script>

<!--conteúdo-->
<table class="BtnFace" cellpadding="5" style="width:100%; height:100%; border:1px solid;">
  <tr>
    <td valign="top">
      <!-- título -->
      <h2>Fornecedor</h2>
      <!-- form -->
      <form name="formFornecedor" action="controller">
        <!-- controle de navegação -->
        <input type="hidden"
               name="<%=Controller.ACTION%>"
               value="<%=ACTION_FORNECEDOR%>">
        <input type="hidden"
               name="<%=Controller.COMMAND%>"
               value="">
        <!-- chave do registro selecionado -->
        <input type="hidden"
               name="<%=Fornecedor.FIELD_CODIGO%>"
               value="">
        <!-- pesquisa -->
        <table style="border:1px solid;">
          <tr>
            <td>
              <table class="BtnFace">
                <tr>
                  <td>Nome</td>
                  <td><input type="text"
                             name="<%=NOME%>"
                             value="<%=nome%>"
                             style="width:150px;"></td>
                </tr>
                <tr>
                  <td></td>
                  <td><button onclick="searchFornecedor();">Pesquisar</button></td>
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
            <td align="left">Nome</td>
            <td align="left">Município</td>
            <td align="left">Estado</td>
            <td align="left">CNPJ</td>
          </tr>
          <%// loop nos itens
           for (int i=0; i<fornecedorInfoList.length; i++) {
             // Fornecedor da vez
             FornecedorInfo fornecedorInfo = fornecedorInfoList[i];%>
          <tr class="<%=getRowClass(i)%>">
            <td align="center">
              <input type="radio"
                     name="radioDelete"
                     onclick="javascript:<%=Fornecedor.FIELD_CODIGO%>.value='<%=fornecedorInfo.getCodigo()%>';">
            </td>
            <td align="left"><a href="<%=actionCadastro(fornecedorInfo.getCodigo())%>"
                                title="Editar..."><%=fornecedorInfo.getNome()%></a></td>


            <td align="left"><%=fornecedorInfo.getMunicipio()%></td>
            <td align="left"><%=fornecedorInfo.getEstado()%></td>
            <td align="left"><%=fornecedorInfo.getCnpj()%></td>
          </tr>
        <%}%>
        </table>
        <br>
        <!--botões de comando-->
        <button onclick="insertFornecedor();">Incluir</button>&nbsp;&nbsp;&nbsp;
        <button onclick="deleteFornecedor();">Excluir</button>
      </form>
    </td>
  </tr>
 </table>
</body>
</html>
