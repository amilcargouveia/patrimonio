package multiwork.ui;

/**
 * Representa um controle baseado em páginas. Com o PageControl voce poderá
 * distribuir os controles de sua interface em páginas cujo acesso é feito através
 * de guias nomeadas. <p>
 * Exemplo: <p>
 * <pre>
 * &lt;%
 *   PageControl pageControl = new PageControl(); // cria o PageControl
 *   pageControl.setId("myPageControl"); // define a identificação (nome) do PageControl
 *   pageControl.setWidth(550); // define sua largura
 *   pageControl.setHeight(380); // define sua altura
 *   pageControl.setTabSheetCount(3); // define a qde. de guias
 *   pageControl.setRowCount(1); // define a qde. de linhas de guias
 *   pageControl.setTabsPerRow(3); // define a qde. de guias por linha
 * %&gt;
 * &lt;!--aqui se inicia nosso PageControl--&gt;
 * &lt;%=pageControl.begin()%&gt;
 *     &lt;!--inicia a primeira guia--&gt;
 *     &lt;%=pageControl.beginTabSheet("Primeira")%&gt;
 *       &lt;h1&gt;Conteúdo da primeira guia&lt;/h1&gt;
 *     &lt;!--termina primeira guia--&gt;
 *     &lt;%=pageControl.endTabSheet()%&gt;
 *     &lt;%=pageControl.beginTabSheet("Segunda")%&gt;
 *       &lt;h1&gt;Conteúdo da segunda guia&lt;/h1&gt;
 *     &lt;%=pageControl.endTabSheet()%&gt;
 *     &lt;%=pageControl.beginTabSheet("Terceira")%&gt;
 *       &lt;h1&gt;Conteúdo da terceira guia&lt;/h1&gt;
 *     &lt;%=pageControl.endTabSheet()%&gt;
 * &lt;!--aqui termina nosso PageControl--&gt;
 * &lt;%=pageControl.end()%&gt;
 * </pre>
 */
public class PageControl {

  int    height           = 400;
  int    horizontalOffSet = 10;
  String id               = "";
  int    rowCount         = 1;
  int    tabsAdded        = 0;
  int    tabSheetCount    = 0;
  int    tabHeight        = 24;
  String tabSheetCaption  = "";
  int    tabsPerRow       = 4;
  int    tabWidth         = 90;
  int    verticalOffSet   = 2;
  int    width            = 400;

  /**
   * Construtor padrão.
   */
  public PageControl() {
  }

  /**
   * Retorna o script que contendo a representação HTML que inicia o PageControl.
   * @return Retorna o script que contendo a representação HTML que inicia o
   *         PageControl.
   */
  public String begin() {
    return "<ilayer id=\"panelLocator\""
         + " width=\"" + width + "px\""
         + " height=\"" + height + "px\"></ilayer> \r"
         + "<nolayer> \r"
         + "  <div id=\"" + id + "\""
         + "       style=\"background-color:transparent;"
         + "               position:relative; width:" + width + "px;"
         + "                                  height:" + height + "px\"> \r";
  }


  /**
   * Retorna o script para o início de um novo tab sheet.
   * @param caption Título do tab sheet.
   * @return Retorna o script para o início de um novo tab sheet.
   * @see PageControl#endTabSheet
   */
  public String beginTabSheet(String caption) {
    // guarda o caption do tabSheet
		tabSheetCaption = caption;
    // incrementa a qde. de guias adicionadas
    tabsAdded++;
    // índice do painel
    int index = tabsAdded - 1;
    // z-Index deste painel
    int zIndex = tabSheetCount - index;
    // id do painel
    String panelId = id + "panel" + index;
    // topo do painel
    int panelTop = rowCount * tabHeight - (rowCount * verticalOffSet);
    // altura do painel
    int panelHeight = height - panelTop;
    // inicia a declaração do painel
    String result = "<div id=\"" + panelId + "\""
                  + " class=\"TabSheetPanel\""
                  + " style=\"z-index:" + zIndex + "; top:" + panelTop + "px; width:" + width + "px; height:" + panelHeight + "px;\"> \r";
	  // retorna o início do painel
    return result;
  }

  /**
   * Retorna o script contendo a representação HTML que termina o PageControl.
   * @return Retorna o script contendo a representação HTML que termina o
   *         PageControl.
   */
  public String end() {
    return "  </div> \r "
         + "</nolayer> \r"
         + "<script> \r"
         + "   \r"
         + "  function getCookie(name) \r"
         + "  { \r"
         + "    var arg = name + \"=\"; \r"
         + "    var alen = arg.length; \r"
         + "    var clen = document.cookie.length; \r"
         + "    var i = 0; \r"
         + "    while (i < clen) { \r"
         + "      var j = i + alen; \r"
         + "      if (document.cookie.substring(i, j) == arg) \r"
         + "        return getCookieVal(j); \r"
         + "      i = document.cookie.indexOf(\" \", i) + 1; \r"
         + "      if (i == 0) break; \r"
         + "    } \r"
         + "    return null; \r"
         + "  } \r"
         + " \r"
         + "  function getCookieVal(offset) { \r"
         + "    var endstr = document.cookie.indexOf (\";\", offset); \r"
         + "    if (endstr == -1) \r"
         + "      endstr = document.cookie.length; \r"
         + "    return unescape(document.cookie.substring(offset, endstr)); \r"
         + "  } \r"
         + "  \r"
         + "  function getDiv(s,i) { \r"
         + "   var div \r"
         + "   if (document.layers) { \r"
         + "       div = document.layers[panelID].layers[panelID+s+i] \r"
         + "   } else if (document.all && !document.getElementById) { \r"
         + "       div = document.all[panelID+s+i] \r"
         + "   } else { \r"
         + "       div = document.getElementById(panelID+s+i) \r"
         + "   } \r"
         + "   return div \r"
         + "  } \r"
         + "  \r"
         + "  function setCookie(name, value) \r"
         + "  { \r"
         + "    var argv = setCookie.arguments; \r"
         + "    var argc = setCookie.arguments.length; \r"
         + "    var expires = (argc > 2) ? argv[2] : null; \r"
         + "    var path = (argc > 3) ? argv[3] : null; \r"
         + "    var domain = (argc > 4) ? argv[4] : null; \r"
         + "    var secure = (argc > 5) ? argv[5] : false; \r"
         + "    var path = \"/\"; //allows the tree to remain open across pages with diff names & paths \r"
         + "    document.cookie = name + \"=\" + escape (value) + \r"
         + "    ((expires == null) ? \"\" : (\"; expires=\" + expires.toGMTString())) + \r"
         + "    ((path == null) ? \"\" : (\"; path=\" + path)) + \r"
         + "    ((domain == null) ? \"\" : (\"; domain=\" + domain)) + \r"
         + "    ((secure == true) ? \"; secure\" : \"\"); \r"
         + "  } \r"
         + "   \r"
         + "  function setZIndex(div, zIndex) { \r"
         + "   if (document.layers) div.style = div; \r"
         + "   div.style.zIndex = zIndex \r"
         + "  } \r"
         + "   \r"
         + "  function updatePosition(div, newPos) { \r"
         + "   newClip=tabHeight*(Math.floor(newPos/tabsPerRow)+1) \r"
         + "   if (document.layers) { \r"
         + "     div.style=div; \r"
         + "     div.clip.bottom=newClip; // clip off bottom \r"
         + "     } else { \r"
         + "     div.style.clip=\"rect(0 auto \"+newClip+\" 0)\" \r"
         + "   } \r"
         + "   div.style.top = (numRows-(Math.floor(newPos/tabsPerRow) + 1)) * (tabHeight-vOffset) \r"
         + "   div.style.left = (newPos % tabsPerRow) * tabWidth +	(hOffset * (Math.floor(newPos / tabsPerRow))) \r"
         + "  } \r"
         + "   \r"
         + "  function selectTab(n) { \r"
         + "   var firstTab = Math.floor(divLocation[n] / tabsPerRow) * tabsPerRow \r"
         + "   for(var i=0; i<numDiv; ++i) { \r"
         + "     var loc = divLocation[i] \r"
         + "     if(loc >= firstTab && loc < (firstTab + tabsPerRow)) newLocation[i] = (loc - firstTab) \r"
         + "     else if(loc < tabsPerRow) newLocation[i] = firstTab+(loc % tabsPerRow) \r"
         + "     else newLocation[i] = loc \r"
         + "   } \r"
         + "   for(var i=0; i<numDiv; ++i) { \r"
         + "     var loc = newLocation[i] \r"
         + "     var div = getDiv(\"panel\",i) \r"
         + "     if (i == n) { \r "
         + "       showControls(div, 'visible') \r"
         + "       setZIndex(div, numLocations +1) \r"
         + "     } \r"
         + "     else { \r"
         + "       showControls(div, 'hidden') \r"
         + "       setZIndex(div, numLocations - loc) \r"
         + "     } \r"
         + "     divLocation[i] = loc \r"
         + "     div = getDiv(\"tab\",i) \r"
         + "     updatePosition(div, loc) \r"
         + "     if(i == n) { \r"
         + "       setZIndex(div, numLocations +1) \r"
         + "       div.className = \"TabSheet\" \r"
         + "       setCookie(panelID, n)\r"
         + "     } \r"
         + "     else { \r"
         + "       setZIndex(div,numLocations - loc) \r"
         + "       div.className = \"TabSheetInactive\" \r"
         + "     } \r"
         + "   } \r"
         + "  } \r"
         + "   \r"
         + "  function positionPanel() { \r"
         + "   document.p1.top=document.panelLocator.pageY; \r"
         + "   document.p1.left=document.panelLocator.pageX; \r"
         + "  } \r"
         + "  function showControls(div, show) { \r"
         + "    for (i=0; i<div.all.length; i++) { \r"
         + "      div.all[i].style.visibility = show; \r"
         + "    } \r"
         + "  } \r"
         + "   \r"
         + "  var panelID      = \"" + id + "\"; \r"
         + "  var numDiv       = " + tabSheetCount + "; \r"
         + "  var numRows      = " + rowCount + "; \r"
         + "  var tabsPerRow   = " + tabsPerRow + "; \r"
         + "  var numLocations = numRows * tabsPerRow; \r"
         + "  var tabWidth     = " + tabWidth + "; \r"
         + "  var tabHeight    = " + tabHeight + "; \r"
         + "  var vOffset      = " + verticalOffSet + "; \r"
         + "  var hOffset      = " + horizontalOffSet + "; \r"
         + "   \r"
         + "  var divLocation = new Array(numLocations) \r"
         + "  var newLocation = new Array(numLocations) \r"
         + "   \r"
         + "  for(var i=0; i<numLocations; ++i) { \r"
         + "   divLocation[i] = i \r"
         + "   newLocation[i] = i \r"
         + "  } \r"
         + "  \r"
         + "  lastTab = getCookie(panelID) \r"
         + "  if (lastTab == null) lastTab = 0; \r"
         + "  selectTab(lastTab); \r"
         + "</script> \r";
  }

  /**
   * Retorna o script para o término de um tab sheet.
   * @return Retorna o script para o término de um tab sheet.
   * @see PageControl#beginTabSheet
   */
	public String endTabSheet() {
		// índice da guia
    int tabIndex = tabsAdded-1;
		// termina o painel adicionado com beginTabSheet()
    String result = "</div> \r";
    // id da guia
    String tabSheetId = id + "tab" + tabIndex;
    // posição da guia
    int tabY    = (rowCount - (tabIndex / tabsPerRow)) - 1;
    int tabX    = tabIndex - ((tabIndex / tabsPerRow) * tabsPerRow);
    int tabTop  = tabY * (tabHeight - verticalOffSet);
    int tabLeft = (tabX * tabWidth) + ((rowCount - tabY - 1) * horizontalOffSet);
    // clipRight
    int clipRight = (rowCount - (tabIndex / tabsPerRow)) * tabHeight;
    // z-Index desta guia
    int zIndex = tabSheetCount-tabIndex;
    // inicia a guia
    result += "<div onclick=\"selectTab(" + tabIndex + ")\""
            + " id=\"" + tabSheetId + "\""
            + " class=\"TabSheet\""
            + " style=\"left:" + tabLeft + "px; top:" + tabTop + "px; width:" + tabWidth + "px; z-index:" + zIndex + "; clip:rect(0 auto " + clipRight + " 0)\"> \r"
            + tabSheetCaption + "\r"
            + " </div> \r";
	  // retorna o resultado
		return result;
  }

  /**
   * Retorna a altura do PageControl.
   * @return Retorna a altura do PageControl.
   */
	public int getHeight() {
    return height;
  }

  /**
   * Retorna a identificação do PageControl na página.
   * @return Retorna a identificação do PageControl na página.
   */
  public String getId() {
    return id;
  }

  /**
   * Retorna a quantidade de linhas de guias para serem exibidas.
   * @return Retorna a quantidade de linhas de guias para serem exibidas.
   */
	public int getRowCount() {
    return rowCount;
  }

  /**
   * Retorna a quantidade de guias mantidas pelo PageControl.
   * @return Retorna a quantidade de guias mantidas pelo PageControl.
   */
	public int getTabSheetCount() {
    return tabSheetCount;
  }

  /**
   * Retorna a quantidade de guias que podem ser exibidas por linha.
   * @return Retorna a quantidade de guias que podem ser exibidas por linha.
   */
	public int getTabsPerRow() {
    return tabsPerRow;
  }

  /**
   * Retorna a largura de uma guia.
   * @return Retorna a largura de uma guia.
   */
  public int getTabWidth() {
    return  tabWidth;
  }

  /**
   * Retorna a largura do PageControl.
   * @return Retorna a largura do PageControl.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Define a altura do PageControl.
   * @param height Retorna a altura do PageControl.
   */
  public void setHeight(int height) {
    this.height = height;
  }

  /**
   * Define a identificação do PageControl na página. Este identificação é
   * obrigatória pois permitirá que o tab sheet selecionado permaneça entre
   * requisições de página pelo uso de cookies.
   * @param id Identificação do PageControl na página.
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Define a quantidade de linhas de guias para serem exibidas.
   * @param rowCount Quantidade de linhas de guias para serem exibidas.
   */
  public void setRowCount(int rowCount) {
    this.rowCount = rowCount;
  }

  /**
   * Define a quantidade de guias mantidas pelo PageControl.
   * @param tabSheetCount Quantidade de guias mantidas pelo PageControl.
   */
  public void setTabSheetCount(int tabSheetCount) {
    this.tabSheetCount = tabSheetCount;
  }

  /**
   * Define a quantidade de guias que podem ser exibidas por linha.
   * @param tabsPerRow Quantidade de guias que podem ser exibidas por linha.
   */
  public void setTabsPerRow(int tabsPerRow) {
    this.tabsPerRow = tabsPerRow;
  }

  /**
   * Define a largura de uma guia.
   * @param tabWidth Largura de uma guia.
   */
  public void setTabWidth(int tabWidth) {
    this.tabWidth = tabWidth;
  }

  /**
   * Define a largura do PageControl.
   * @param width Largura do PageControl.
   */
  public void setWidth(int width) {
    this.width = width;
  }

}
