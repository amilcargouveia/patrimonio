
<%@include file="../include/beans.jsp"%>
<%@page import="java.sql.*"%>

<%!
  // constantes
  String ACTION_BEM = "bem";
  String ACTION_BEM_CADASTRO = "bemCadastro";
  String CLASS_BEM = "multiwork.entity.Bem";
  String DELETE = "DELETE";
  String INSERT = "INSERT";
  String CODIGO_BARRAS = "codigoBarras";
  String TOMBO = "tombo";
  String DESCRICAO = "descricao";
  String VALOR_COMPRA = "valorCompra";
  String DATA_INCLUSAO = "dataInclusao";
  String DATA_ULTIMA_MOVIMENTACAO = "dataUltimaMovimentacao";
  String CLASS_GRUPO_BEM = "multiwork.entity.GrupoBem";
  String CLASS_SETOR = "multiwork.entity.Setor";
  String SEARCH_OPTION = "searchOption";
  String BEM = "bem";
  String SEARCH_OPTION_CODIGO_BARRAS = "searchOptionCodigoBarras";
  String SEARCH_OPTION_TOMBO = "searchOptionTombo";
  String SEARCH_OPTION_DESCRICAO = "searchOptionDescricao";
  String SEARCH_OPTION_VALOR_COMPRA = "searchOptionValorCompra";
  String SEARCH_OPTION_DATA_INCLUSAO = "searchOptionDataInclusao";
  String SEARCH_OPTION_DATA_ULTIMA_MOVIMENTACAO = "searchOptionDataUltimaMovimentacao";


  // funções
  public String actionCadastro(int codigo) {
    return "controller?" + Controller.ACTION + "=" + ACTION_BEM_CADASTRO
         + "&" + Bem.FIELD_CODIGO + "=" + codigo;
  }

  public String getRowClass(int rowIndex) {
    return (rowIndex % 2 > 0) ? "GridRowEven" : "GridRowOdd";
  }

  // Recebe o código do Grupo e retorna sua descrição
  public String getGrupoBem(GrupoBem grupoBem,
  		             int      codigoGrupoBem) throws Exception {
    try {
      return grupoBem.getByCodigo(codigoGrupoBem).getDescricao();
    }
    catch (Exception e) {
      return e.getMessage();
    }
  }

  // Recebe o código do Setor e retorna sua descrição
  public String getSetor(Setor setor,
  		          int  codigoSetor) throws Exception {
    try {
      return setor.getByCodigo(codigoSetor).getDescricao();
    }
    catch (Exception e) {
      return e.getMessage();
    }
  }

  // Recebe o código do campo particular e retorna sim (1) ou não (0)
  public String getParticular(int codigoParticular) throws Exception {
    if (codigoParticular == 0)
    	return "Não";
    else
    	return "Sim";
  }

%>

<%
  // comando para realizar
  String command = request.getParameter(Controller.COMMAND);
  if (command == null)
    command = "";
  // nossa instância de Bem
  Bem bem = (Bem)facade.getEntity(CLASS_BEM);
  // nossa instância de GrupoBem
  GrupoBem grupoBem = (GrupoBem)facade.getEntity(CLASS_GRUPO_BEM);
  // nossa instância de Setor
  Setor setor = (Setor)facade.getEntity(CLASS_SETOR);
  // quer excluir?
  if (command.equals(DELETE)) {
    // chave do registro para excluir
    int codigo = Integer.parseInt(request.getParameter(Bem.FIELD_CODIGO));
    // info do registro que iremos excluir
    BemInfo bemInfo = bem.getByCodigo(codigo);
    // exclui
    bem.delete(bemInfo);
  } // if

  // lista que iremos exibir
  BemInfo[] bemInfoList = new BemInfo[0];

  // chave de pesquisa
  String searchOption = request.getParameter(SEARCH_OPTION);
  if (searchOption == null)
    searchOption = HttpTools.getCookieValue(request, SEARCH_OPTION + BEM);
  if (searchOption == null)
    searchOption = SEARCH_OPTION_CODIGO_BARRAS;
  //*
  String codigoBarras = request.getParameter(CODIGO_BARRAS);
  if (codigoBarras == null)
    codigoBarras = HttpTools.getCookieValue(request, CODIGO_BARRAS + BEM);
  if (codigoBarras == null)
    codigoBarras = "";
  //*
  String tombo = request.getParameter(TOMBO);
  if (tombo == null)
    tombo = HttpTools.getCookieValue(request, TOMBO + BEM);
  if (tombo == null)
    tombo = "";
  //*
  String descricao = request.getParameter(DESCRICAO);
  if (descricao == null)
    descricao = HttpTools.getCookieValue(request, DESCRICAO + BEM);
  if (descricao == null)
    descricao = "";
  //*
  String valorCompra = request.getParameter(VALOR_COMPRA);
  if (valorCompra == null)
    valorCompra = HttpTools.getCookieValue(request, VALOR_COMPRA + BEM);
  if (valorCompra == null)
    valorCompra = "";
  //*
  String dataInclusao = request.getParameter(DATA_INCLUSAO);
  if (dataInclusao == null)
    dataInclusao = HttpTools.getCookieValue(request, DATA_INCLUSAO + BEM);
  if (dataInclusao == null)
    dataInclusao = "";
  //*
  String dataUltimaMovimentacao = request.getParameter(DATA_ULTIMA_MOVIMENTACAO);
  if (dataUltimaMovimentacao == null)
    dataUltimaMovimentacao = HttpTools.getCookieValue(request, DATA_ULTIMA_MOVIMENTACAO + BEM);
  if (dataUltimaMovimentacao == null)
    dataUltimaMovimentacao = "";

  // se temos uma chave de pesquisa...
  if (searchOption.equals(SEARCH_OPTION_CODIGO_BARRAS)) {
    if (!codigoBarras.equals("")){
      bemInfoList = bem.getByCodigoBarras(codigoBarras);
    }
  }  // if
  else if (searchOption.equals(SEARCH_OPTION_TOMBO)) {
    bemInfoList = new BemInfo[1];
    bemInfoList[0] = bem.getByTombo(tombo);
  }  // if
  else if (searchOption.equals(SEARCH_OPTION_DESCRICAO)) {
    bemInfoList = bem.getByDescricao(descricao);
  }  // if
  else if (searchOption.equals(SEARCH_OPTION_VALOR_COMPRA)) {
      bemInfoList =
          bem.getByValorCompra(Double.parseDouble(valorCompra));
  }
  else if (searchOption.equals(SEARCH_OPTION_DATA_INCLUSAO)) {
      bemInfoList =
          bem.getByDataInclusao(
            DateTools.stringDateToTimestamp(dataInclusao));
  }
  else if (searchOption.equals(SEARCH_OPTION_DATA_ULTIMA_MOVIMENTACAO)) {
      bemInfoList =
          bem.getByDataInclusao(
            DateTools.stringDateToTimestamp(dataUltimaMovimentacao));
  }

%>

<html>
<head>
<title>Bem</title>
<link href="css/default.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="5" topmargin="5" rightmargin="5" bottommargin="5">

<script language="javascript" src="scripts/tools.js"></script>

<script language="javascript">

  // controla a submissão do form para evitar
  // multiplicidade de requisições
  formSubmited = false;

  function deleteBem() {
    // se o form já foi submetido...aguarde
    if (formSubmited) {
      alert("Aguarde enquanto a operação é concluída.");
      return;
    } // if
    // chave
    codigo = formBem.<%=Bem.FIELD_CODIGO%>;
    // se não selecionou nada...dispara
    if ((codigo.value == "")) {
      alert("Selecione o(a) Bem para ser excluído(a).");
      return;
    } // if
    // se não quer excluir...dispara
    if (!confirm("Deseja realmente excluir o(a) Bem selecionado(a)?"))
      return;
    // informa que o comando será exclusão
    formBem.<%=Controller.COMMAND%>.value = "<%=DELETE%>";
    // submete o form
    formSubmited = true;
    formBem.submit();
  }

  function insertBem() {
    // ação para inclusão
    window.location.href = "controller?<%=Controller.ACTION%>=<%=ACTION_BEM_CADASTRO%>"
                         + "&<%=Controller.COMMAND%>=<%=INSERT%>";
  }

                                                                  function searchBem() {
    // se o form já foi submetido...aguarde
    if (formSubmited) {
      alert("Aguarde enquanto a operação é concluída.");
      return;
    } // if
    // chave
    codigoBarras = formBem.<%=CODIGO_BARRAS%>;
    tombo = formBem.<%=TOMBO%>;
    descricao = formBem.<%=DESCRICAO%>;
    valorCompra = formBem.<%=VALOR_COMPRA%>;
    dataInclusao = formBem.<%=DATA_INCLUSAO%>;
    dataUltimaMovimentacao = formBem.<%=DATA_ULTIMA_MOVIMENTACAO%>;
    searchOption = formBem.<%=SEARCH_OPTION%>;
    // se não selecionou nada...dispara
    if ((codigoBarras.value == "") &&
       (tombo.value == "") &&
       (descricao.value == "") &&
       (valorCompra.value == "") &&
       (dataInclusao.value == "") &&
       (dataUltimaMovimentacao.value == "")) {
      alert("Informe um dos valores para pesquisar.");
      return;
    } // if
    // salva os valores da pesquisa em Cookies
    writeCookieValue("<%=CODIGO_BARRAS + BEM%>", codigoBarras.value);
    writeCookieValue("<%=TOMBO + BEM%>", tombo.value);
    writeCookieValue("<%=DESCRICAO + BEM%>", descricao.value);
    writeCookieValue("<%=VALOR_COMPRA + BEM%>", valorCompra.value);
    writeCookieValue("<%=DATA_INCLUSAO + BEM%>", dataInclusao.value);
    writeCookieValue("<%=DATA_ULTIMA_MOVIMENTACAO + BEM%>", dataUltimaMovimentacao.value);
    if (searchOption[0].checked)
      writeCookieValue("<%=SEARCH_OPTION + BEM%>", "<%=SEARCH_OPTION_CODIGO_BARRAS%>");
    if (searchOption[1].checked)
      writeCookieValue("<%=SEARCH_OPTION + BEM%>", "<%=SEARCH_OPTION_TOMBO%>");
    if (searchOption[2].checked)
      writeCookieValue("<%=SEARCH_OPTION + BEM%>", "<%=SEARCH_OPTION_DESCRICAO%>");
    if (searchOption[3].checked)
      writeCookieValue("<%=SEARCH_OPTION + BEM%>", "<%=SEARCH_OPTION_VALOR_COMPRA%>");
    if (searchOption[4].checked)
      writeCookieValue("<%=SEARCH_OPTION + BEM%>", "<%=SEARCH_OPTION_DATA_INCLUSAO%>");
    if (searchOption[5].checked)
      writeCookieValue("<%=SEARCH_OPTION + BEM%>", "<%=SEARCH_OPTION_DATA_ULTIMA_MOVIMENTACAO%>");
    // submete o form
    formSubmited = true;
    formBem.submit();
  }

</script>

<!--conteúdo-->
<table class="BtnFace" cellpadding="5" style="width:100%; height:100%; border:1px solid;">
  <tr>
    <td valign="top">
      <!-- título -->
      <h2>Bem</h2>
      <!-- form -->
      <form name="formBem" action="controller">
        <!-- controle de navegação -->
        <input type="hidden"
               name="<%=Controller.ACTION%>"
               value="<%=ACTION_BEM%>">
        <input type="hidden"
               name="<%=Controller.COMMAND%>"
               value="">
        <!-- chave do registro selecionado -->
        <input type="hidden"
               name="<%=Bem.FIELD_CODIGO%>"
               value="">
        <!-- pesquisa -->
        <table style="border:1px solid;">
          <tr>
            <td>
              <table class="BtnFace">
                <tr>
                  <td><input type="radio"
                             name="<%=SEARCH_OPTION%>"
                             value="<%=SEARCH_OPTION_CODIGO_BARRAS%>"
                             <%=searchOption.equals(SEARCH_OPTION_CODIGO_BARRAS) ? "checked" : ""%>>
                             Código de Barras</td>
                  <td><input type="text"
                             name="<%=CODIGO_BARRAS%>"
                             value="<%=codigoBarras%>"
                             style="width:70px;"
                             onchange="formBem.<%=SEARCH_OPTION%>[0].checked=true;"></td>
                </tr>
                <tr>
                  <td><input type="radio"
                             name="<%=SEARCH_OPTION%>"
                             value="<%=SEARCH_OPTION_TOMBO%>"
                             <%=searchOption.equals(SEARCH_OPTION_TOMBO) ? "checked" : ""%>>
                             Tombo</td>
                  <td><input type="text"
                             name="<%=TOMBO%>"
                             value="<%=tombo%>"
                             style="width:70px;"
                             onchange="formBem.<%=SEARCH_OPTION%>[1].checked=true;"></td>
                </tr>
                <tr>
                  <td><input type="radio"
                             name="<%=SEARCH_OPTION%>"
                             value="<%=SEARCH_OPTION_DESCRICAO%>"
                             <%=searchOption.equals(SEARCH_OPTION_DESCRICAO) ? "checked" : ""%>>
                             Descrição</td>
                  <td><input type="text"
                             name="<%=DESCRICAO%>"
                             value="<%=descricao%>"
                             style="width:150px;"
                             onchange="formBem.<%=SEARCH_OPTION%>[2].checked=true;"></td>
                </tr>
                <tr>
                  <td><input type="radio"
                             name="<%=SEARCH_OPTION%>"
                             value="<%=SEARCH_OPTION_VALOR_COMPRA%>"
                             <%=searchOption.equals(SEARCH_OPTION_VALOR_COMPRA) ? "checked" : ""%>>
                             Valor Compra</td>
                  <td><input type="text"
                             name="<%=VALOR_COMPRA%>"
                             value="<%=valorCompra%>"
                             style="width:70px;"
                             onchange="formBem.<%=SEARCH_OPTION%>[3].checked=true;"></td>
                  </td>
                </tr>
                <tr>
                  <td><input type="radio"
                             name="<%=SEARCH_OPTION%>"
                             value="<%=SEARCH_OPTION_DATA_INCLUSAO%>"
                             <%=searchOption.equals(SEARCH_OPTION_DATA_INCLUSAO) ? "checked" : ""%>>
                             Data Inclusão</td>
                  <td><input type="text"
                             name="<%=DATA_INCLUSAO%>"
                             value="<%=dataInclusao%>"
                             style="width:70px;"
                             onchange="formBem.<%=SEARCH_OPTION%>[4].checked=true;"> dd/mm/aaaa</td>
                </tr>
                <tr>
                  <tr>
                  <td><input type="radio"
                             name="<%=SEARCH_OPTION%>"
                             value="<%=SEARCH_OPTION_DATA_ULTIMA_MOVIMENTACAO%>"
                             <%=searchOption.equals(SEARCH_OPTION_DATA_ULTIMA_MOVIMENTACAO) ? "checked" : ""%>>
                             Data Última Movimentação</td>
                  <td><input type="text"
                  	      name="<%=DATA_ULTIMA_MOVIMENTACAO%>"
                             value="<%=dataUltimaMovimentacao%>"
                             style="width:70px;"
                             onchange="formBem.<%=SEARCH_OPTION%>[5].checked=true;"> dd/mm/aaaa</td>
                  </td>
                </tr>
                <tr>
                  <td></td>
                  <td><button onclick="searchBem();">Pesquisar</button></td>
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
            <td align="left">Tombo</td>
            <td align="left">Descrição</td>
            <td align="left">Grupo</td>
            <td align="left">Setor</td>
            <td align="left">Particular</td>
          </tr>
          <%// loop nos itens
           for (int i=0; i<bemInfoList.length; i++) {
             // Bem da vez
             BemInfo bemInfo = bemInfoList[i];%>
          <tr class="<%=getRowClass(i)%>">
            <td align="center">
              <input type="radio"
                     name="radioDelete"
                     onclick="javascript:<%=Bem.FIELD_CODIGO%>.value='<%=bemInfo.getCodigo()%>';">
            </td>
            <td align="left"><a href="<%=actionCadastro(bemInfo.getCodigo())%>"
                                title="Editar..."><%=bemInfo.getTombo().equals("") ? "[sem tombo]" : bemInfo.getTombo()%></a></td>
            <td align="left"><%=bemInfo.getDescricao()%></td>
            <td align="left"><%=getGrupoBem(grupoBem, bemInfo.getCodigoGrupo())%></td>
            <td align="left"><%=getSetor(setor, bemInfo.getCodigoSetor())%></td>
            <td align="left"><%=getParticular(bemInfo.getParticular())%></td>
          </tr>
        <%}%>
        </table>
        <br>
        <!--botões de comando-->
        <button onclick="insertBem();">Incluir</button>&nbsp;&nbsp;&nbsp;
        <button onclick="deleteBem();">Excluir</button>
      </form>
    </td>
  </tr>
 </table>
</body>
</html>
