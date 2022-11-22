
<%@include file="../include/beans.jsp"%>
<%@page import="java.sql.*"%>

<%!
  // constantes
  String ACTION_BEM = "bem";
  String ACTION_BEM_CADASTRO = "bemCadastro";
  String CLASS_BEM = "multiwork.entity.Bem";
  String CLASS_FORNECEDOR = "multiwork.entity.Fornecedor";
  String CLASS_GRUPOBEM = "multiwork.entity.GrupoBem";
  String CLASS_SETOR = "multiwork.entity.Setor";
  String INSERT = "INSERT";
  String POST   = "POST";
  String CODIGO_GRUPOBEM = "codigogrupobem";
  String CODIGO_FORNE = "codigofornecedor";
  String CODIGO_BEM = "codigoBem";

  // Recebe o código do Fornecedor e retorna sua descrição
  public String getFornecedor(Fornecedor fornecedor,
  		              int        codigoFornecedor) throws Exception {
    try {
      if (codigoFornecedor > 0)
        return fornecedor.getByCodigo(codigoFornecedor).getNome();
      else
        return "";
    }
    catch (Exception e) {
      return e.getMessage();
    } // try-catch
  }

  // Recebe o código do Grupo e retorna sua descrição
  public String getGrupoBem(GrupoBem grupoBem,
  		            int      codigoGrupoBem) throws Exception {
    try {
      if (codigoGrupoBem > 0)
        return grupoBem.getByCodigo(codigoGrupoBem).getDescricao();
      else
        return "";
    }
    catch (Exception e) {
      return e.getMessage();
    } // try-ctch
  }

  // Recebe o código do Setor e retorna sua descrição
  public String getSetor(Setor setor,
  		          int   codigo) throws Exception {
    try {
      if (codigo > 0)
        return setor.getByCodigo(codigo).getDescricao();
      else
        return "";
    }
    catch (Exception e) {
      return e.getMessage();
    } // try-catch
  }

%>

<%
  // comando para realizar
  String command = request.getParameter(Controller.COMMAND);
  if (command == null)
    command = "";
  // nossa instância de Bem
  Bem bem = (Bem)facade.getEntity(CLASS_BEM);
  // nossa instância de Setor
  Setor setor = (Setor)facade.getEntity(CLASS_SETOR);
  // nossa instancia de Fornecedor
  Fornecedor fornecedor = (Fornecedor)facade.getEntity(CLASS_FORNECEDOR);
  // nossa instancia de GrupoBem
  GrupoBem grupoBem = (GrupoBem)facade.getEntity(CLASS_GRUPOBEM);
  // BemInfo para editar
  BemInfo bemInfo = null;
  // se quer incluir...
  if (command.equals(INSERT))
    // info em branco
    bemInfo =
      new BemInfo(
            0,
            "",
            "",
            "",
            0,
            "",
            0,
            0.0D,
            0,
            0,
            new Timestamp((new java.util.Date()).getTime()),
            new Timestamp((new java.util.Date()).getTime()),
            "",
            0,
            new Timestamp(0)
          );
  // se quer salvar...
  else if (command.equals(POST)) {
    // info preenchido na página
    bemInfo =
      new BemInfo(
            Integer.parseInt(request.getParameter(Bem.FIELD_CODIGO)),
            request.getParameter(Bem.FIELD_CODIGO_DE_BARRAS),
            request.getParameter(Bem.FIELD_TOMBO),
            request.getParameter(Bem.FIELD_NUMERO_SERIE),
            Integer.parseInt(request.getParameter(Bem.FIELD_CODIGO_GRUPO)),
            request.getParameter(Bem.FIELD_DESCRICAO),
            Integer.parseInt(request.getParameter(Bem.FIELD_CODIGO_SETOR)),
            NumberTools.parseDouble(request.getParameter(Bem.FIELD_VALOR_DA_COMPRA)),
            Integer.parseInt(request.getParameter(Bem.FIELD_NOTA_FISCAL)),
            Integer.parseInt(request.getParameter(Bem.FIELD_CODIGO_FORNECEDOR)),
            DateTools.stringDateToTimestamp(request.getParameter(Bem.FIELD_DATA_INCLUSAO)),
            DateTools.stringDateToTimestamp(request.getParameter(Bem.FIELD_DATA_GARANTIA)),
            request.getParameter(Bem.FIELD_PROPRIETARIO),
            Integer.parseInt(request.getParameter(Bem.FIELD_PARTICULAR)),
            DateTools.stringDateToTimestamp(request.getParameter(Bem.FIELD_ULTIMA_MOVIMENTACAO))
          );
    // se está salvando um novo...inclui
    if (request.getParameter(INSERT) != null)
    // request.getParameter(Bem.FIELD_CODIGO)
      bem.insert(bemInfo);
    // se não está salvando um novo...atualiza
    else
      bem.update(bemInfo);
    // volta para a página de consulta
    response.sendRedirect("controller?" + Controller.ACTION + "=" + ACTION_BEM);
    return;
  }
  // se quer editar...
  else {
    // info do registro para editar
    bemInfo = bem.getByCodigo(Integer.parseInt(request.getParameter(Bem.FIELD_CODIGO)));
  } // if

%>

<html>
<head>
<title>Bem</title>
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
    if (!confirm("Deseja mesmo salvar o(a) Bem?"))
      return;
    if (!validate(formBem.<%=Bem.FIELD_DESCRICAO%>, "Descrição"))
       return;
    if (!validate(formBem.<%=Bem.FIELD_CODIGO_GRUPO%>, "Grupo"))
       return;
    if (!validate(formBem.<%=Bem.FIELD_CODIGO_FORNECEDOR%>, "Fornecedor"))
       return;
    if (!validate(formBem.<%=Bem.FIELD_CODIGO_SETOR%>, "Setor"))
       return;
    // altera o comando
    formBem.<%=Controller.COMMAND%>.value = "<%=POST%>";
    // envia o formulário
    formSubmited = true;
    formBem.submit();
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

   function showHideProprietario() {
    // se o bem é particular exibe os controles para proprietário
    if (formBem.<%=Bem.FIELD_PARTICULAR%>[0].checked) {
      label<%=Bem.FIELD_PROPRIETARIO%>.style.visibility = "visible";
      formBem.<%=Bem.FIELD_PROPRIETARIO%>.style.visibility = "visible";
    }
    else {
      label<%=Bem.FIELD_PROPRIETARIO%>.style.visibility = "hidden";
      formBem.<%=Bem.FIELD_PROPRIETARIO%>.style.visibility = "hidden";
    } // if
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
        <input type="hidden"
               name="<%=Controller.ACTION%>"
               value="<%=ACTION_BEM_CADASTRO%>">
        <input type="hidden"
               name="<%=Controller.COMMAND%>"
               value="">
        <input type="hidden"
               name="<%=Bem.FIELD_CODIGO%>"
               value="<%=bemInfo.getCodigo()%>">
        <%if (command.equals(INSERT)) {%>
          <input type="hidden"
                 name="<%=INSERT%>"
                 value="true">
        <%} // if%>

      <%
        // nosso PageControl
        PageControl pageControl = new PageControl();
        pageControl.setId("pageControlContrato"); // define a identificação (nome) do PageControl
        pageControl.setWidth(550); // define sua largura
        pageControl.setHeight(360); // define sua altura
        pageControl.setTabSheetCount(2); // define a qde. de guias
        pageControl.setRowCount(1); // define a qde. de linhas de guias
        pageControl.setTabsPerRow(2); // define a qde. de guias por linha
        pageControl.setTabWidth(110); // largura da guia
      %>

      <!-- inicia o PageControl -->
      <%=pageControl.begin()%>
        <!-- inicia o TabSheet -->
        <%=pageControl.beginTabSheet("Dados do Bem")%>
          <!-- dados de Bem -->
          <table class="BtnFace">
            <!-- campo Código de Barras -->
            <tr>
              <td colspan="2">
                <table class="BtnFace">
                  <tr>
                    <td style="width:175px;">Código de Barras</td>
                    <td style="width:175px;">Número de Série</td>
                    <td style="width:175px;">Tombo</td>
                  </tr>
                  <tr>
                    <td>
                      <input type="text"
                             name="<%=Bem.FIELD_CODIGO_DE_BARRAS%>"
                             value="<%=bemInfo.getCodigoDeBarras()%>">
                    </td>
                    <td>
                      <input type="text"
                             name="<%=Bem.FIELD_NUMERO_SERIE%>"
                             value="<%=bemInfo.getNumeroSerie()%>">
                    </td>
                    <td>
                      <input type="text"
                             name="<%=Bem.FIELD_TOMBO%>"
                             value="<%=bemInfo.getTombo()%>">
                    </td>
                  </tr>
                </table>
            <tr>
              <td>Descrição</td>
              <td>
                <input type="text"
                       name="<%=Bem.FIELD_DESCRICAO%>"
                       value="<%=bemInfo.getDescricao()%>"
                       style="width:250px;">
              </td>
            </tr>
            <!-- campo Código Grupo -->
            <tr>
              <td>Grupo</td>
              <td>
                      <%
                        String[] grupoBemSearchFieldNames = {GrupoBem.FIELD_DESCRICAO};
                        String[] grupoBemSearchFieldTitles = {"Descrição"};
                        String[] grupoBemDisplayFieldNames = {GrupoBem.FIELD_CODIGO, GrupoBem.FIELD_DESCRICAO};
                        String[] grupoBemDisplayFieldTitles = {"Código", "Descrição"};
                        short[]  grupoBemDisplayFieldWidths = {50, 300};
                        String[] grupoBemReturnFieldNames = {GrupoBem.FIELD_CODIGO};
                        String[] grupoBemReturnFieldValues = {bemInfo.getCodigoGrupo() + ""};
                        String[] grupoBemReturnUserFieldNames = {GrupoBem.FIELD_DESCRICAO};
                        String[] grupoBemReturnUserFieldValues = {getGrupoBem(grupoBem, bemInfo.getCodigoGrupo())};
                        String[] grupoBemOrderFieldNames = {GrupoBem.FIELD_DESCRICAO};
                      %>
                      <%=ExternalLookup.script(GrupoBem.TABLE_GRUPO_BEM,
                                               grupoBemSearchFieldNames,
                                               grupoBemSearchFieldTitles,
                                               grupoBemDisplayFieldNames,
                                               grupoBemDisplayFieldTitles,
                                               grupoBemDisplayFieldWidths,
                                               grupoBemReturnFieldNames,
                                               grupoBemReturnFieldValues,
                                               grupoBemReturnUserFieldNames,
                                               grupoBemReturnUserFieldValues,
                                               grupoBemOrderFieldNames,
                                               "",
                                               ExternalLookup.SELECT_TYPE_SINGLE,
                                               "Pesquisar Grupo Bem",
                                               Bem.FIELD_CODIGO_GRUPO,
                                               "width:200px;",
                                               "")%>
              </td>
            </tr>
            <!-- campo Código Fornecedor -->
            <tr>
              <td>Fornecedor</td>
              <td>
                      <%
                        String[] fornecedorSearchFieldNames = {Fornecedor.FIELD_NOME};
                        String[] fornecedorSearchFieldTitles = {"Nome"};
                        String[] fornecedorDisplayFieldNames = {Fornecedor.FIELD_CODIGO, Fornecedor.FIELD_NOME};
                        String[] fornecedorDisplayFieldTitles = {"Código", "Nome"};
                        short[]  fornecedorDisplayFieldWidths = {50, 300};
                        String[] fornecedorReturnFieldNames = {Fornecedor.FIELD_CODIGO};
                        String[] fornecedorReturnFieldValues = {bemInfo.getCodigoFornecedor() + ""};
                        String[] fornecedorReturnUserFieldNames = {Fornecedor.FIELD_NOME};
                        String[] fornecedorReturnUserFieldValues = {getFornecedor(fornecedor, bemInfo.getCodigoFornecedor())};
                        String[] fornecedorOrderFieldNames = {Fornecedor.FIELD_NOME};
                      %>
                      <%=ExternalLookup.script(Fornecedor.TABLE_FORNECEDOR,
                                               fornecedorSearchFieldNames,
                                               fornecedorSearchFieldTitles,
                                               fornecedorDisplayFieldNames,
                                               fornecedorDisplayFieldTitles,
                                               fornecedorDisplayFieldWidths,
                                               fornecedorReturnFieldNames,
                                               fornecedorReturnFieldValues,
                                               fornecedorReturnUserFieldNames,
                                               fornecedorReturnUserFieldValues,
                                               fornecedorOrderFieldNames,
                                               "",
                                               ExternalLookup.SELECT_TYPE_SINGLE,
                                               "Pesquisar Fornecedor",
                                               Bem.FIELD_CODIGO_FORNECEDOR,
                                               "width:200px;",
                                               "")%>
              </td>
            </tr>
            <!-- campo Código Setor -->
            <tr>
              <td>Setor</td>
              <td>
              <%if (command.equals(INSERT)) {%>
                <%
                  String[] setorDisplayFieldNames = {Setor.FIELD_DESCRICAO};
                  String[] setorReturnFieldNames  = {Setor.FIELD_CODIGO};
                  String[] setorReturnFieldValues = {bemInfo.getCodigoSetor() + ""};
                  String[] setorOrderFieldNames   = {Setor.FIELD_DESCRICAO};
                  %>
                  <%=LookupList.script(facade.connection(),
                                       Setor.TABLE_SETOR,
                                       setorDisplayFieldNames,
                                       setorReturnFieldNames,
                                       setorReturnFieldValues,
                                       setorOrderFieldNames,
                                       "",
                                       LookupList.SELECT_TYPE_SINGLE,
                                       Bem.FIELD_CODIGO_SETOR,
                                       "width:250px;",
                                       "")%>
              <%}
                else {%>
                <input type="hidden" name="<%=Bem.FIELD_CODIGO_SETOR%>" value="<%=bemInfo.getCodigoSetor()%>"/>
                <input type="text"
                       class="BtnFace"
                       style="width:250px;"
                       value="<%=getSetor(setor, bemInfo.getCodigoSetor())%>"
                       readonly/>
              <%} // if%>
              </td>
            </tr>
            <!-- campo Valor da Compra -->
            <tr>
              <td>Valor Compra</td>
              <td>
                <input type="text"
                       style="width:70px;"
                       name="<%=Bem.FIELD_VALOR_DA_COMPRA%>"
                       value="<%=NumberTools.format(bemInfo.getValorDaCompra())%>">
              </td>
            </tr>
            <!-- campo Nota Fiscal -->
            <tr>
              <td>Nota Fiscal</td>
              <td>
                <input type="text"
                       style="width:70px;"
                       name="<%=Bem.FIELD_NOTA_FISCAL%>"
                       value="<%=bemInfo.getNotaFiscal()%>">
              </td>
            </tr>
            <!-- campos Data Inclusão, Data Garantia e Última Movimentação -->
            <tr>
              <td colspan="2">
                <table class="BtnFace">
                  <tr>
                    <td style="width:100px;">Data Inclusão</td>
                    <td style="width:100px;">Data Garantia</td>
                    <td style="width:150px;">Última Movimentação</td>
                  </tr>
                  <tr>
                    <td>
                      <input type="text"
                             name="<%=Bem.FIELD_DATA_INCLUSAO%>"
                             value="<%=DateTools.dateToString(bemInfo.getDataInclusao())%>"
                             style="width:70px;"
                             maxlength="10">
                    </td>
                    <td>
                      <input type="text"
                             name="<%=Bem.FIELD_DATA_GARANTIA%>"
                             value="<%=DateTools.dateToString(bemInfo.getDataGarantia())%>"
                             style="width:70px;"
                             maxlength="10">
                    </td>
                    <td>
                      <input type="text"
                             class="BtnFace"
                             name="<%=Bem.FIELD_ULTIMA_MOVIMENTACAO%>"
                             value="<%=DateTools.dateToString(bemInfo.getUltimaMovimentacao())%>"
                             style="width:70px;"
                             readonly>
                    </td>
                  </tr>
                </table>
            <!-- campo Particular -->
            <tr>
              <td>Particular</td>
              <td><input type="radio"
                         name="<%=Bem.FIELD_PARTICULAR%>"
                         value="<%=Bem.PARTICULAR_SIM%>"
                         onclick="showHideProprietario();"
                         <%=bemInfo.getParticular() == Bem.PARTICULAR_SIM ? "checked" : ""%>>
                         Sim
                  <input type="radio"
                         name="<%=Bem.FIELD_PARTICULAR%>"
                         value="<%=Bem.PARTICULAR_NAO%>"
                         onclick="showHideProprietario();"
                         <%=bemInfo.getParticular() == Bem.PARTICULAR_NAO ? "checked" : ""%>>
                         Não
              </td>
            </tr>
            <!-- campo Proprietário -->
            <tr>
              <td><span id="label<%=Bem.FIELD_PROPRIETARIO%>">Proprietário</span></td>
              <td>
                <input type="text"
                       name="<%=Bem.FIELD_PROPRIETARIO%>"
                       value="<%=bemInfo.getProprietario()%>"
                       style="width:250px;">
              </td>
            </tr>
          </table>
        <%=pageControl.endTabSheet()%>
        <!-- inicia o TabSheet -->
        <%=pageControl.beginTabSheet("Movimentação")%>
          <%// se não estamos incluindo...
             if (!command.equals(INSERT)) {%>
          <iframe src="controller?<%=Controller.ACTION%>=movimentacao&<%=CODIGO_BEM%>=<%=bemInfo.getCodigo()%>"
                  width="526"
                  height="320"
                  frameborder="false">
          </iframe>
          <%}
            // se estamos incluindo...avisa
            else {%>
            <br />
            <span class="BtnFace">
              O Bem deverá ser salvo antes da realização de movimentação.
            </span>
            <br />
          <%} // if%>
        <%=pageControl.endTabSheet()%>
      <%=pageControl.end()%>

        <br>
        <!-- script de carga de página -->
        <script language="javascript">showHideProprietario();</script>
        <!--botões de comando-->
        <button onclick="post();">Salvar</button>
      </form>
      <button id="buttonVoltar"
              onclick="window.location.href='controller?<%=Controller.ACTION%>=bem';">Voltar</button>
    </td>
  </tr>
</table>

</body>
</html>
