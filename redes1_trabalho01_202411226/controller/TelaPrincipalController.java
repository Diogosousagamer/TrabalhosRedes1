/* ***************************************************************
* Autor............: Diogo Oliveira de Sousa
* Matricula........: 202411226
* Inicio...........: 14/08/2025
* Ultima alteracao.: 28/08/2025
* Nome.............: TelaPrincipalController
* Funcao...........: Esta classe contem todos os metodos e eventos que seram executados durante a interacao
                     do usuario com a TelaPrincipal. 
                     
*************************************************************** */

package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import model.AplicacaoTransmissora;

public class TelaPrincipalController implements Initializable {
  /* Atributos */
  // Componentes do painel onde o sinal sera transmitido
  @FXML
  private ScrollPane barraRolagem;

  @FXML
  private AnchorPane painelSinais;

  // Componentes do menu de envio/codificacao da mensagem
  @FXML
  private TextField txtMensagem;

  @FXML
  private Button btnCodificar;

  @FXML
  private ComboBox<String> comboBoxCodificacao;

  // Caixas de texto dos usuarios (Diogo e Gustavo)
  @FXML
  private TextArea txtDiogo;

  @FXML
  private TextArea txtGustavo;
  
  // Atributos que serao acessados por outras classes
  private String mensagem;

  private int tipoDeCodificacao;

  private int tipoDeDecodificacao;

  public static volatile TelaPrincipalController controller;

  /* Metodos de inicializacao do programa */
  
  /*
   * ***************************************************************
   * Metodo: initialize
   * Funcao: executa um conjunto de instrucoes durante a inicializacao do programa
   * Parametros: URL url - endereco
   * Retorno: void
   ****************************************************************/

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    /* Carregamento da fonte */

    // Carrega a fonte VCR OSD Mono para que ela possa ser exibida dentro do programa
    Font.loadFont(getClass().getResourceAsStream("/util/VCR_OSD_MONO_1.001.ttf"), 18);

    /* Configuracao da comboBoxCodificacao */

    // Define e carrega as opcoes de codificacao dentro da comboBox
    ObservableList<String> codificacao = FXCollections.observableArrayList("Binaria", "Manchester", "Manchester Diferencial");
    comboBoxCodificacao.setItems(codificacao);

    // A comboBox inicializa com a primeira opcao selecionada
    comboBoxCodificacao.getSelectionModel().selectFirst();

    // Metodo que altera a cor do texto da comboBox
    comboBoxCodificacao.setButtonCell(new ListCell<String>() {
      @Override
      protected void updateItem(String item, boolean empty) {
        // O item e atualizado ao ser selecionado na comboBox
        super.updateItem(item, empty);

        // Inicio do bloco if/else
        if (empty || item == null) {
          // Define o texto como nulo
          // caso o item estiver vazio
          setText(null);
        } 
        else {
          // Caso contrario, o item e selecionado
          setText(item);

          // E a cor do texto e trocada para branco
          setTextFill(javafx.scene.paint.Color.WHITE);
        } // Fim do bloco if/else
      }
    });

    // A instancia do controller e inicializada
    controller = this;
  }

  /* Metodos do btnCodificar */

  /*
   * ***************************************************************
   * Metodo: hoverBtnCodificacao
   * Funcao: altera a cor do btnCodificar quando a seta do mouse se encontra em cima dele
   * Parametros: MouseEvent event - evento gerado ao colocar a seta do mouse em cima do botao
   * Retorno: void
   ****************************************************************/

  @FXML
  private void hoverBtnCodificacao(MouseEvent event) {
    // Altera a cor do botao para um tom mais escuro de vermelho
    // e mantem o arredondamento do botao
    btnCodificar.setStyle("-fx-background-color: #ad130e; -fx-background-radius: 20px");
  }

  /*
   * ***************************************************************
   * Metodo: hoverBtnCodificacao
   * Funcao: reseta a cor do btnCodificar quando a seta do mouse sai de cima dele
   * Parametros: MouseEvent event - evento gerado ao retirar a seta do mouse de cima do botao
   * Retorno: void
   ****************************************************************/

  @FXML
  private void exitBtnCodificacao(MouseEvent event) {
    // Reseta a cor para o tom padrao de vermelho
    // e mantem o arredondamento do botao
    btnCodificar.setStyle("-fx-background-color: #cf231d; -fx-background-radius: 20px");
  }

  /*
   * ***************************************************************
   * Metodo: codificarMensagem
   * Funcao: inicializa a codificacao e transmissao da mensagem escrita
   * Parametros: ActionEvent event - evento gerado ao clicar no botao
   * Retorno: void
   ****************************************************************/

  @FXML
  private void codificarMensagem(ActionEvent event) {
    // Inicio do bloco if/else
    // Se houver alguma mensagem escrita
    if (!txtMensagem.getText().isEmpty()) {
      // Remove os sinais da mensagem transmitida anteriormente
      painelSinais.getChildren().clear();

      // Inicio do bloco if
      if (!txtDiogo.getText().isEmpty() || !txtGustavo.getText().isEmpty()) {
        // Esvazia as caixas de texto do transmissor (Diogo) e receptor (Gustavo)
        // caso alguma mensagem tiver sido transmitida anteriormente
        txtDiogo.clear();
        txtGustavo.clear();
      } // Fim do bloco if

      // A mensagem a ser codificada e o indice do tipo de codificacao/decodificacao selecionado 
      // sao lidos e armazenados em variaveis
      mensagem = txtMensagem.getText();
      tipoDeCodificacao = comboBoxCodificacao.getSelectionModel().getSelectedIndex();
      tipoDeDecodificacao = comboBoxCodificacao.getSelectionModel().getSelectedIndex();

      // A caixa de texto e esvaziada
      txtMensagem.clear();
      
      // Desativa os componentes para que nao haja interrumpcao da transmissao da mensagem
      txtMensagem.setDisable(true);
      comboBoxCodificacao.setDisable(true);
      btnCodificar.setDisable(true);

      // Inicializa a transmissao da mensagem ao instanciar a aplicacao da maquina transmissora
      AplicacaoTransmissora aplicacaoTransmissora = new AplicacaoTransmissora();
      aplicacaoTransmissora.metodoAplicacaoReceptora();
    }
    else { // Caso o usuario nao tiver escrito nada
      // E lancado um aviso para que o usuario digite alguma mensagem 
      // para realizar o processo de codificacao
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("Atencao");
      alert.setHeaderText("Mensagem vazia");
      alert.setContentText("Insira uma mensagem para progredir com o processo de codificacao");
      alert.showAndWait();
    } // Fim do bloco if/else
  }

  /* Metodos de gerenciamento de mensagem/componentes */

  /*
   * ***************************************************************
   * Metodo: enviarMensagem
   * Funcao: exige a mensagem de Diogo a ser transmitida para Gustavo
   * Parametros: String mensagem - mensagem a ser enviada
   * Retorno: void
   ****************************************************************/

  public void enviarMensagem(String mensagem) {
    // A mensagem e impressa dentro da caixa de texto de Diogo
    txtDiogo.setText(mensagem);
  }

  /*
   * ***************************************************************
   * Metodo: receberMensagem
   * Funcao: Gustavo recebe a mensagem enviada por Diogo
   * Parametros: String mensagem - mensagem a ser recebida
   * Retorno: void
   ****************************************************************/

  public void receberMensagem(String mensagem) {
    // A mensagem e impressa dentro da caixa de texto de Gustavo
    // sinalizando que ela foi recebida com sucesso
    txtGustavo.setText(mensagem);
  }

  /*
   * ***************************************************************
   * Metodo: reativarComponentes
   * Funcao: reativa os componentes da interface depois de a mensagem
             ter sido enviada com sucesso
   * Parametros: String mensagem - mensagem a ser enviada
   * Retorno: void
   ****************************************************************/

  public void reativarComponentes() {
    // A caixa de texto de mensagem e reativado
    txtMensagem.setDisable(false);

    // A comboBoxCodificacao e reativada
    comboBoxCodificacao.setDisable(false);

    // O botao de codificacao e reativado
    btnCodificar.setDisable(false);
  }

  /* Metodos de animacao */

  /*
   * ***************************************************************
   * Metodo: animacaoBinaria
   * Funcao: realiza a animacao da transferencia de sinais com base na
             codificacao binaria
   * Parametros: int[] quadro - vetor contendo os bits da mensagem codificada
   * Retorno: Timeline
   ****************************************************************/

  public Timeline animacaoBinaria(int[] quadro) {
    // Limites para a largura da linha (minimo e maximo)
    double larguraMin = 10;
    double larguraMax = 40;

    // Largura da linha, calculada pela divisao entre a largura do painel e o tamanho do vetor de bits
    double largura = painelSinais.getWidth() / quadro.length;

    // Constante que armazenara a largura final
    final double larguraFinal;

    // Flag responsavel por contar os bits mais significativos que compoem, de fato, a mensagem
    int bitsSignificativos = 0;

    // Inicio do bloco if/else if/else
    if (largura < larguraMin) { // Se a largura for menor que a minima
      // A largura final passa a ter o valor minimo
      larguraFinal = larguraMin;
    }
    else if (largura > larguraMax) { // Porem se a largura for maior que o maximo
      // A largura final possuira o valor maximo  
      larguraFinal = larguraMax;
    }
    else { // Caso nenhuma das condicoes anteriores forem atendidas
      // A largura final passa a ser a largura
      larguraFinal = largura;
    } // Fim do bloco if/else if/else 

    // Altura da linha quando receber um bit 0
    double altura0 = painelSinais.getHeight() * 0.8;

    // Altura da linha quando receber um bit 1
    double altura1 = painelSinais.getHeight() * 0.3;

    // Cria-se uma polilinha para representar os sinais de transferencia
    // e assim, alternar a sua largura e altura em diferentes pontos 
    // de acordo com o bit lido
    Polyline linha = new Polyline();

    // Define a cor da linha em verde marinho
    linha.setStroke(Color.SEAGREEN);

    // Define a largura do contorno da linha
    linha.setStrokeWidth(2);

    // A linha e adiciona dentro do painel
    painelSinais.getChildren().add(linha);

    // Vetor contendo a largura inicial no eixo x
    final double[] x = {0};

    // Vetor contendo a altura inicial no eixo y de acordo com o bit inicial
    final double[] yAnterior = {quadro[0] == 1 ? altura1 : altura0};

    // Modificamos os pontos da linha
    linha.getPoints().addAll(x[0], yAnterior[0]);

    // Cria-se uma timeline para executar a movimentacao da linha
    Timeline timeline = new Timeline();
    Duration intervalo = Duration.millis(300);

    // Inicio do bloco for
    // O vetor contendo o fluxo de bits e percorrido para que o sinal de cada bit seja transmitido
    // e tambem para transmitir apenas os bits mais significativos 
    for (int i = 0; i < quadro.length && bitsSignificativos < (mensagem.length() * 8); i++) {
      // Uma constante e criada para armazenar o vetor na posicao dada pelo contador
      final int bit = quadro[i];

      // Inicio do bloco KeyFrame
      // Cria-se uma KeyFrame para controlar a animacao da linha
      KeyFrame kf = new KeyFrame(intervalo.multiply(i), e -> {
        double yAtual = (bit == 1) ? altura1 : altura0;

        // Inicio do bloco if
        // Se a altura atual for diferente da estipulada anteriormente
        if (yAtual != yAnterior[0]) {
          // Altera-se o formato da linha
          linha.getPoints().addAll(x[0], yAtual);
        } // Fim do bloco if

        // Define um novo ponto dentro da linha
        linha.getPoints().addAll(x[0], yAtual);

        // Aumenta a largura do pico obtido na linha
        x[0] += larguraFinal;

        // Encerra o pico com um novo ponto
        linha.getPoints().addAll(x[0], yAtual);

        // Atualiza a altura assumida anteriormente
        yAnterior[0] = yAtual;

        // Obtem-se a largura total da linha e a largura visivel do painel para incrementar a barra de rolagem
        double larguraTotal = linha.getBoundsInLocal().getWidth();
        double larguraVisivel = painelSinais.getWidth();

        // Inicio do bloco if
        // Se a largura da linha for maior que a largura do painel
        if (larguraTotal > larguraVisivel) {
          // Aumentamos o tamanho da barra de rolagem
          barraRolagem.setHvalue((larguraTotal - larguraVisivel) / larguraTotal);
        } // Fim do bloco if
      }); // Fim do bloco KeyFrame

      // Aumenta a quantidade de bits significativos transmitidos
      bitsSignificativos++;

      // O quadro-chave e adicionado dentro da Timeline
      timeline.getKeyFrames().add(kf);
    } // Fim do bloco for

    // Retorna a timeline pronta para ser executada dentro do meio de comunicacao
    return timeline;
  }

  /*
   * ***************************************************************
   * Metodo: animacaoManchester
   * Funcao: realiza a animacao da transferencia de sinais com base na
             codificacao Manchester
   * Parametros: int[] quadro - vetor contendo os bits da mensagem codificada
   * Retorno: Timeline
   ****************************************************************/

  public Timeline animacaoManchester(int[] quadro) {
    // Limites para a largura da linha (minimo e maximo)
    double larguraMin = 10;
    double larguraMax = 40;

    // Largura da linha, calculada pela divisao entre a largura do painel e o tamanho do vetor de bits
    double largura = painelSinais.getWidth() / quadro.length;

    // Constante que armazenara a largura final
    final double larguraFinal;

    // Flag responsavel por contar os bits mais significativos que compoem, de fato, a mensagem
    int bitsSignificativos = 0;

    // Inicio do bloco if/else if/else
    if (largura < larguraMin) { // Se a largura for menor que a minima
      // A largura final passa a ter o valor minimo
      larguraFinal = larguraMin;
    }
    else if (largura > larguraMax) { // Porem se a largura for maior que o maximo
      // A largura final possuira o valor maximo  
      larguraFinal = larguraMax;
    }
    else { // Caso nenhuma das condicoes anteriores forem atendidas
      // A largura final passa a ser a largura
      larguraFinal = largura;
    } // Fim do bloco if/else if/else 

    // Altura da linha quando receber um bit 0
    double altura0 = painelSinais.getHeight() * 0.8;

    // Altura da linha quando receber um bit 1
    double altura1 = painelSinais.getHeight() * 0.3;

    // Cria-se uma polilinha para representar os sinais de transferencia
    // e assim, alternar a sua largura e altura em diferentes pontos 
    // de acordo com o bit lido
    Polyline linha = new Polyline();

    // Define a cor da linha em verde marinho
    linha.setStroke(Color.SEAGREEN);

    // Define a largura do contorno da linha
    linha.setStrokeWidth(2);

    // A linha e adiciona dentro do painel
    painelSinais.getChildren().add(linha);

    // Vetor contendo a largura inicial no eixo x
    final double[] x = {0};

    // Vetor contendo a altura inicial no eixo y de acordo com o bit inicial
    final double[] yAnterior = {quadro[0] == 1 ? altura1 : altura0};

    // Modificamos os pontos da linha
    linha.getPoints().addAll(x[0], yAnterior[0]);

    // Cria-se uma timeline para executar a movimentacao da linha
    Timeline timeline = new Timeline();
    Duration intervalo = Duration.millis(300);

    // Inicio do bloco for
    // O vetor contendo o fluxo de bits e percorrido para que o sinal de cada bit seja transmitido
    // e tambem para transmitir apenas os bits mais significativos
    for (int i = 0; i < quadro.length && bitsSignificativos < (mensagem.length() * 16); i++) {
      // Uma constante e criada para armazenar o vetor na posicao dada pelo contador
      final int bit = quadro[i];

      // Inicio do bloco KeyFrame
      // Cria-se uma KeyFrame para controlar a animacao da linha
      KeyFrame kf = new KeyFrame(intervalo.multiply(i), e -> {
        double yAtual = (bit == 1) ? altura1 : altura0;

        // Inicio do bloco if
        // Se a altura atual for diferente da estipulada anteriormente
        if (yAtual != yAnterior[0]) {
          // Altera-se o formato da linha
          linha.getPoints().addAll(x[0], yAtual);
        } // Fim do bloco if

        // Define um novo ponto dentro da linha
        linha.getPoints().addAll(x[0], yAtual);

        // Aumenta a largura do pico obtido na linha
        x[0] += larguraFinal;

        // Encerra o pico com um novo ponto
        linha.getPoints().addAll(x[0], yAtual);

        // Atualiza a altura assumida anteriormente
        yAnterior[0] = yAtual;

        // Obtem-se a largura total da linha e a largura visivel do painel para incrementar a barra de rolagem
        double larguraTotal = linha.getBoundsInLocal().getWidth();
        double larguraVisivel = painelSinais.getWidth();

        // Inicio do bloco if
        // Se a largura da linha for maior que a largura do painel
        if (larguraTotal > larguraVisivel) {
          // Aumentamos o tamanho da barra de rolagem
          barraRolagem.setHvalue((larguraTotal - larguraVisivel) / larguraTotal);
        } // Fim do bloco if
      }); // Fim do bloco KeyFrame

      // Aumenta a quantidade de bits significativos transmitidos
      bitsSignificativos++;

      // O quadro-chave e adicionado dentro da Timeline
      timeline.getKeyFrames().add(kf);
    } // Fim do bloco for

    // Retorna a timeline pronta para ser executada dentro do meio de comunicacao
    return timeline;
  }

  /*
   * ***************************************************************
   * Metodo: animacaoManchesterDiferencial
   * Funcao: realiza a animacao da transferencia de sinais com base na
             codificacao Manchester diferencial
   * Parametros: int[] quadro - vetor contendo os bits da mensagem codificada
   * Retorno: Timeline
   ****************************************************************/

  public Timeline animacaoManchesterDiferencial(int[] quadro) {
    // Limites para a largura da linha (minimo e maximo)
    double larguraMin = 10;
    double larguraMax = 40;

    // Largura da linha, calculada pela divisao entre a largura do painel e o tamanho do vetor de bits
    double largura = painelSinais.getWidth() / quadro.length;

    // Constante que armazenara a largura final
    final double larguraFinal;

    // Flag responsavel por contar os bits mais significativos que compoem, de fato, a mensagem
    int bitsSignificativos = 0;

    // Inicio do bloco if/else if/else
    if (largura < larguraMin) { // Se a largura for menor que a minima
      // A largura final passa a ter o valor minimo
      larguraFinal = larguraMin;
    }
    else if (largura > larguraMax) { // Porem se a largura for maior que o maximo
      // A largura final possuira o valor maximo  
      larguraFinal = larguraMax;
    }
    else { // Caso nenhuma das condicoes anteriores forem atendidas
      // A largura final passa a ser a largura
      larguraFinal = largura;
    } // Fim do bloco if/else if/else 

    // Altura da linha quando receber um bit 0
    double altura0 = painelSinais.getHeight() * 0.8;

    // Altura da linha quando receber um bit 1
    double altura1 = painelSinais.getHeight() * 0.3;

    // Cria-se uma polilinha para representar os sinais de transferencia
    // e assim, alternar a sua largura e altura em diferentes pontos 
    // de acordo com o bit lido
    Polyline linha = new Polyline();

    // Define a cor da linha em verde marinho
    linha.setStroke(Color.SEAGREEN);

    // Define a largura do contorno da linha
    linha.setStrokeWidth(2);

    // Vetor contendo a largura inicial no eixo x
    final double[] x = {0};

    // Vetor contendo a maior altura no eixo y
    final double[] y = {altura1};

    // Modificamos os pontos da linha
    linha.getPoints().addAll(x[0], y[0]);

    // A linha e adiciona dentro do painel
    painelSinais.getChildren().add(linha);

    // Cria-se uma timeline para executar a movimentacao da linha
    Timeline timeline = new Timeline();
    Duration intervalo = Duration.millis(300);

    // Inicio do bloco for
    // O vetor contendo o fluxo de bits e percorrido para que o sinal de cada bit seja transmitido
    // e tambem para transmitir apenas os bits mais significativos
    for (int i = 0; i < quadro.length && bitsSignificativos < (mensagem.length() * 16); i++) {
      // Uma constante e criada para armazenar o vetor na posicao dada pelo contador
      final int bit = quadro[i];

      // Inicio do bloco KeyFrame
      // Cria-se uma KeyFrame para controlar a animacao da linha
      KeyFrame kf = new KeyFrame(intervalo.multiply(i + 1), e -> {
        // Calcula-se o ponto do meio do sinal, onde sempre ocorrera transicao
        double xMeio = x[0] + larguraFinal / 2;

        // Obtem-se a altura do meio do sinal
        double yMeio = (y[0] == altura1) ? altura0: altura1;

        // Marcamos os pontos dentro da linha
        linha.getPoints().addAll(xMeio, y[0], xMeio, yMeio);

        // Calculamos o ponto no eixo x onde sera iniciado o proximo bit
        double xFim = x[0] + larguraFinal;

        // Inicializamos o ponto final no eixo y com nenhum valor
        double yFim = 0;

        // Inicio do bloco if/else
        // Se o bit obtido for 0
        if (bit == 0) {
          // Obtem-se qual a altura do ponto de transicao (0 ou 1)
          yFim = (yMeio == altura1) ? altura0 : altura1;

          // Os pontos sao eventualmente adicionados na linha
          linha.getPoints().addAll(xFim, yMeio, xFim, yFim);
        }
        else { // Caso contrario
          // Nao havera um ponto de transicao no inicio do proximo bit
          yFim = yMeio;

          // Os pontos sao eventualmente adicionados na linha
          linha.getPoints().addAll(xFim, yFim);
        } // Fim do bloco if/else

        // Atualizamos os indices anteriores
        x[0] = xFim;
        y[0] = yFim;

        // Obtem-se a largura total da linha e a largura visivel do painel para incrementar a barra de rolagem
        double larguraTotal = linha.getBoundsInLocal().getWidth();
        double larguraVisivel = painelSinais.getWidth();

        // Inicio do bloco if
        // Se a largura da linha for maior que a largura do painel
        if (larguraTotal > larguraVisivel) {
          // Aumentamos o tamanho da barra de rolagem
          barraRolagem.setHvalue((larguraTotal - larguraVisivel) / larguraTotal);
        } // Fim do bloco if
      }); // Fim do bloco KeyFrame

      // Aumenta a quantidade de bits significativos transmitidos
      bitsSignificativos++;

      // O quadro-chave e adicionado dentro da Timeline
      timeline.getKeyFrames().add(kf);
    }

    // Retorna a timeline para ser executada dentro do meio de comunicacao
    return timeline;
  }

  /*
   * ***************************************************************
   * Metodo: getMensagem
   * Funcao: retorna o valor da mensagem
   * Parametros: nenhum parametro foi definido para esta funcao
   * Retorno: String
   ****************************************************************/

  public String getMensagem() {
    return mensagem;
  }

  /*
   * ***************************************************************
   * Metodo: getTipoDeCodificacao
   * Funcao: retorna o valor do tipo de codificacao
   * Parametros: nenhum parametro foi definido para esta funcao
   * Retorno: int
   ****************************************************************/

  public int getTipoDeCodificacao() {
    return tipoDeCodificacao;
  }

  /*
   * ***************************************************************
   * Metodo: getTipoDeDecodificacao
   * Funcao: retorna o valor do tipo de decodificacao
   * Parametros: nenhum parametro foi definido para esta funcao
   * Retorno: int
   ****************************************************************/

  public int getTipoDeDecodificacao() {
    return tipoDeDecodificacao;
  }

  /*
   * ***************************************************************
   * Metodo: getTipoDeDecodificacao
   * Funcao: retorna o comprimento da mensagem enviada
   * Parametros: nenhum parametro foi definido para esta funcao
   * Retorno: int
   ****************************************************************/

  public int getTamanhoMensagem() {
    return mensagem.length();
  }
}