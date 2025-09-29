/* ***************************************************************
* Autor............: Diogo Oliveira de Sousa
* Matricula........: 202411226
* Inicio...........: 15/09/2025
* Ultima alteracao.: 29/09/2025
* Nome.............: TelaPrincipalController
* Funcao...........: Esta classe tem como objetivo gerenciar os eventos e operacoes da TelaPrincipal.
                     
*************************************************************** */

package controller;

import model.AplicacaoTransmissora;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TelaPrincipalController implements Initializable {
  /* Componentes da interface */

  // Botoes
  @FXML
  private Button botaoVoltar;

  @FXML
  private ImageView botaoTransmitir;

  @FXML
  private ImageView botaoLimpar;

  // Componentes do painel de transmissao
  @FXML
  private AnchorPane painelTransmissao;

  @FXML
  private ScrollPane painelRolagem;

  // Caixas de texto
  @FXML
  private TextArea txtDiogo;

  @FXML
  private TextArea txtGustavo;

  @FXML
  private TextArea txtDepuracao;

  /* Atributos */

  // Imagens dos botoes Transmitir e Limpar
  private Image transmitir;

  private Image limpar;

  private Image transmitirHover;

  private Image limparHover;
  
  // Atributos relacionados a mensagem
  private String mensagem;

  private int tamanhoMensagem;

  // Valores inteiros contendo os tipos de algoritmos selecionados
  private int tipoEnquadramento;

  private int tipoCodificacao;

  private int probabilidadeErro;

  // Atributos volateis
  public static volatile boolean temErro;

  public static volatile TelaPrincipalController controller;

  /*
   * ***************************************************************
   * Metodo: initialize
   * Funcao: executa um conjunto de instrucoes durante a inicializacao da aplicacao
   * Parametros: URL url: endereco do programa
   * ResourceBundle rb: recursos para inicializacao
   * Retorno: void
   ****************************************************************/

  @Override 
  public void initialize(URL url, ResourceBundle rb) {
    // Carrega as imagens dos botoes Transmitir e Limpar para evitar sobrecarga na memoria heap
    transmitir = new Image(getClass().getResource("/img/Transmitir.png").toExternalForm());
    limpar = new Image(getClass().getResource("/img/Limpar.png").toExternalForm());
    transmitirHover = new Image(getClass().getResource("/img/TransmitirHover.png").toExternalForm());
    limparHover = new Image(getClass().getResource("/img/LimparHover.png").toExternalForm());

    // Carrega a instancia volatil do controller
    controller = this;
  }

  /* Metodos do botaoVoltar */

  /*
   * ***************************************************************
   * Metodo: botaoVoltarHover
   * Funcao: altera a cor do botaoVoltar quando o mouse esta em cima dele
   * Parametros: MouseEvent event: evento gerado ao colocar o mouse em cima do botao
   * Retorno: void
   ****************************************************************/

  @FXML
  private void botaoVoltarHover(MouseEvent event) {
    // Altera a cor para um tom mais escuro de verde, mantendo o arredondamento e o padding
    botaoVoltar.setStyle("-fx-background-color:  #296e3cff; -fx-background-radius: 20px; -fx-padding: 10px");
  }

  /*
   * ***************************************************************
   * Metodo: botaoVoltarExit
   * Funcao: reseta a cor do botaoVoltar quando o mouse sai de cima dele
   * Parametros: MouseEvent event: evento gerado ao retirar o mouse de cima do botao
   * Retorno: void
   ****************************************************************/

  @FXML
  private void botaoVoltarExit(MouseEvent event) {
    // Reseta a cor para o tom normal de verde, mantendo o arredondamento e o padding
    botaoVoltar.setStyle("-fx-background-color:  #32a852; -fx-background-radius: 20px; -fx-padding: 10px");
  }
  
  /*
   * ***************************************************************
   * Metodo: voltarTelaInicial
   * Funcao: retorna o usuario a TelaMenu
   * Parametros: ActionEvent event: evento gerado ao clicar no botao
   * Retorno: void
   ****************************************************************/
  
  @FXML
  private void voltarTelaInicial(ActionEvent event) {
    // Inicio do bloco try/catch
    try {
      // Carrega o arquivo FXML da TelaMenu para gerar uma nova cena 
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaMenu.fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root);

      // Carrega a nova cena dentro da janela ja aberta
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
    }
    catch (IOException e) {
      // Em caso de excecao, ela sera exibida no terminal
      Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, e);
    } // Fim do bloco try/catch
  }

  /* Metodos do botaoTransmitir */

  /*
   * ***************************************************************
   * Metodo: botaoTransmitirHover
   * Funcao: altera a imagem do botaoTransmitir quando o mouse esta em cima dele
   * Parametros: MouseEvent event: evento gerado ao colocar o mouse em cima do botao
   * Retorno: void
   ****************************************************************/

  @FXML
  private void botaoTransmitirHover(MouseEvent event) {
    // Altera a imagem do botaoTransmitir para uma versao com cor mais escura
    botaoTransmitir.setImage(transmitirHover);
  }

  /*
   * ***************************************************************
   * Metodo: botaoTransmitirExit
   * Funcao: reseta a imagem do botaoTransmitir quando o mouse sai de cima dele
   * Parametros: MouseEvent event: evento gerado ao retirar o mouse de cima do botao
   * Retorno: void
   ****************************************************************/

  @FXML
  private void botaoTransmitirExit(MouseEvent event) {
    // Reseta a imagem original do botaoTransmitir
    botaoTransmitir.setImage(transmitir);
  }

  /*
   * ***************************************************************
   * Metodo: iniciarTransmissao
   * Funcao: inicializa a transmissao da mensagem
   * Parametros: MouseEvent event: evento gerado ao clicar na imagem do botao
   * Retorno: void
   ****************************************************************/
  
  @FXML
  private void iniciarTransmissao(MouseEvent event) {
    // Inicio do bloco if/else
    if (!txtDiogo.getText().isEmpty()) { // Se uma mensagem tiver sido escrita
      // Esvazia o painel de transmissao
      painelTransmissao.getChildren().clear();
      
      // Inicio do bloco if
      // Se a caixa de mensagem do receptor (Gustavo) nao estiver vazia
      if (!txtGustavo.getText().isEmpty()) {
        // A caixa de texto de Gustavo e esvaziada antes de iniciar a transmissao
        // para dar espaco a nova mensagem a ser transmitida
        txtGustavo.clear();
      } // Fim do bloco if

      // Carrega a mensagem digitada, o comprimento da mensagem e o tipo de codificacao selecionado
      mensagem = txtDiogo.getText();
      tamanhoMensagem = mensagem.length();

      // Desativa os componentes, permanecendo assim ate o fim da transmissao
      txtDiogo.setEditable(false);
      botaoTransmitir.setDisable(true);
      botaoLimpar.setDisable(true);

      // Inicializa a transmissao instanciando a aplicacao transmissora da rede
      AplicacaoTransmissora aplicacao = new AplicacaoTransmissora();
      aplicacao.executarAplicacaoTransmissora();
    }
    else {
      // Em caso contrario, e emitido um alerta informando ao usuario que a transmissao
      // so sera realizada caso uma mensagem tiver sido digitada
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("Atencao");
      alert.setHeaderText("Mensagem vazia");
      alert.setContentText("Insira uma mensagem para progredir com o processo de codificacao");
      alert.showAndWait();
    } // Fim do bloco if/else
  }

  /* Metodos do botaoLimpar */

  /*
   * ***************************************************************
   * Metodo: botaoLimparHover
   * Funcao: altera a imagem do botaoLimpar quando o mouse esta em cima dele
   * Parametros: MouseEvent event: evento gerado ao colocar o mouse em cima do botao
   * Retorno: void
   ****************************************************************/

  @FXML
  private void botaoLimparHover(MouseEvent event) {
    // Altera a imagem do botaoLimpar para uma versao mais escura
    botaoLimpar.setImage(limparHover);
  }

  /*
   * ***************************************************************
   * Metodo: botaoLimparExit
   * Funcao: reseta a imagem do botaoLimpar quando o mouse sai de cima dele
   * Parametros: MouseEvent event: evento gerado ao retirar o mouse de cima do botao
   * Retorno: void
   ****************************************************************/

  @FXML
  private void botaoLimparExit(MouseEvent event) {
    // Reseta a cor para o tom normal de verde, mantendo o arredondamento e o padding
    botaoLimpar.setImage(limpar);
  }

  @FXML
  private void limparTransmissao(MouseEvent event) {
    // Limpa o conteudo das caixas de texto dos usuarios (Diogo e Gustavo) e do painel de transmissao
    txtDiogo.clear();
    txtGustavo.clear();
    painelTransmissao.getChildren().clear();
  }

  /*
   * ***************************************************************
   * Metodo: receberMensagem
   * Funcao: inicializa a transmissao da mensagem
   * Parametros: String mensagem: mensagem a ser exibida
   * Retorno: void
   ****************************************************************/
  
  public void receberMensagem(String mensagem) {
    // Exibe a mensagem no computador do receptor (Gustavo)
    txtGustavo.setText(mensagem);

    // Os demais componentes sao reativados
    txtDiogo.setEditable(true);
    botaoTransmitir.setDisable(false);
    botaoLimpar.setDisable(false);
  }

  /*
   * ***************************************************************
   * Metodo: retornarDepuracao
   * Funcao: informa ao usuario a ocorrencia (ou nao) de erros durante a transmissao
   * Parametros: nenhum parametro foi definido para esta funcao
   * Retorno: void
   ****************************************************************/

  public void retornarDepuracao() {
    // Inicio do bloco if/else
    if (temErro) {
      // Esta mensagem e exibida em caso de ocorrencia de erros durante a transmissao
      txtDepuracao.appendText("ERRO de transmissao." + "\n" + "----------" + "\n");
    }
    else {
      // Esta mensagem e exibida em caso contrario
      txtDepuracao.appendText("Mensagem transmitida com sucesso" + "\n" + "----------" + "\n");
    } // Fim do bloco if/else
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
    double largura = painelTransmissao.getWidth() / quadro.length;

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
    double altura0 = painelTransmissao.getHeight() * 0.8;

    // Altura da linha quando receber um bit 1
    double altura1 = painelTransmissao.getHeight() * 0.3;

    // Cria-se uma polilinha para representar os sinais de transferencia
    // e assim, alternar a sua largura e altura em diferentes pontos 
    // de acordo com o bit lido
    Polyline linha = new Polyline();

    // Define a cor da linha em verde marinho
    linha.setStroke(Color.SEAGREEN);

    // Define a largura do contorno da linha
    linha.setStrokeWidth(2);

    // A linha e adiciona dentro do painel
    painelTransmissao.getChildren().add(linha);

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
    for (int i = 0; i < quadro.length && bitsSignificativos < (mensagem.length() * 64); i++) {
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
        double larguraVisivel = painelTransmissao.getWidth();

        // Inicio do bloco if
        // Se a largura da linha for maior que a largura do painel
        if (larguraTotal > larguraVisivel) {
          // Aumentamos o tamanho da barra de rolagem
          painelRolagem.setHvalue((larguraTotal - larguraVisivel) / larguraTotal);
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
    double largura = painelTransmissao.getWidth() / quadro.length;

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
    double altura0 = painelTransmissao.getHeight() * 0.8;

    // Altura da linha quando receber um bit 1
    double altura1 = painelTransmissao.getHeight() * 0.3;

    // Cria-se uma polilinha para representar os sinais de transferencia
    // e assim, alternar a sua largura e altura em diferentes pontos 
    // de acordo com o bit lido
    Polyline linha = new Polyline();

    // Define a cor da linha em verde marinho
    linha.setStroke(Color.SEAGREEN);

    // Define a largura do contorno da linha
    linha.setStrokeWidth(2);

    // A linha e adiciona dentro do painel
    painelTransmissao.getChildren().add(linha);

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
    for (int i = 0; i < quadro.length && bitsSignificativos < (mensagem.length() * 64); i++) {
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
        double larguraVisivel = painelTransmissao.getWidth();

        // Inicio do bloco if
        // Se a largura da linha for maior que a largura do painel
        if (larguraTotal > larguraVisivel) {
          // Aumentamos o tamanho da barra de rolagem
          painelRolagem.setHvalue((larguraTotal - larguraVisivel) / larguraTotal);
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
    double largura = painelTransmissao.getWidth() / quadro.length;

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
    double altura0 = painelTransmissao.getHeight() * 0.8;

    // Altura da linha quando receber um bit 1
    double altura1 = painelTransmissao.getHeight() * 0.3;

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
    painelTransmissao.getChildren().add(linha);

    // Cria-se uma timeline para executar a movimentacao da linha
    Timeline timeline = new Timeline();
    Duration intervalo = Duration.millis(300);

    // Inicio do bloco for
    // O vetor contendo o fluxo de bits e percorrido para que o sinal de cada bit seja transmitido
    // e tambem para transmitir apenas os bits mais significativos
    for (int i = 0; i < quadro.length && bitsSignificativos < (mensagem.length() * 64); i++) {
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
        double larguraVisivel = painelTransmissao.getWidth();

        // Inicio do bloco if
        // Se a largura da linha for maior que a largura do painel
        if (larguraTotal > larguraVisivel) {
          // Aumentamos o tamanho da barra de rolagem
          painelRolagem.setHvalue((larguraTotal - larguraVisivel) / larguraTotal);
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

  /* Metodos de configuracao/acesso de parametros */

  /*
   * ***************************************************************
   * Metodo: setEnquadramento
   * Funcao: configura o tipo de enquadramento selecionado para o enlace de dados
   * Parametros: int tipoEnquadramento: o tipo de enquadramento a ser configurado
   * Retorno: void
   ****************************************************************/
  
  public void setEnquadramento(int tipoEnquadramento) {
    this.tipoEnquadramento = tipoEnquadramento;
  }

  /*
   * ***************************************************************
   * Metodo: setCodificacao
   * Funcao: configura o algoritmo de codificacao selecionado para a transmissao
   * Parametros: int tipoCodificacao: o algoritmo de codificacao a ser configurado
   * Retorno: void
   ****************************************************************/
  
  public void setCodificacao(int tipoCodificacao) {
    this.tipoCodificacao = tipoCodificacao;
  }

  /*
   * ***************************************************************
   * Metodo: setProbabilidadeErro
   * Funcao: configura a probabilidade de erro para a transmissao de dados
   * Parametros: double probabilidadeErro: a probabilidade de erro a ser configurada
   * Retorno: void
   ****************************************************************/
  
  public void setProbabilidadeErro(int probabilidadeErro) {
    this.probabilidadeErro = probabilidadeErro;
  }

  /*
   * ***************************************************************
   * Metodo: getTipoEnquadramento
   * Funcao: retorna o tipo de enquadramento selecionado
   * Parametros: nenhum parametro foi definido para esta funcao
   * Retorno: int
   ****************************************************************/

  public int getTipoEnquadramento() {
    return tipoEnquadramento;
  }

  /*
   * ***************************************************************
   * Metodo: getProbabilidadeErro
   * Funcao: retorna a probabilidade de erro para certa transmissao
   * Parametros: nenhum parametro foi definido para esta funcao
   * Retorno: double
   ****************************************************************/

  public int getProbabilidadeErro() {
    return probabilidadeErro;
  }

  /*
   * ***************************************************************
   * Metodo: getTipoCodificacao
   * Funcao: retorna o tipo de codificacao selecionado
   * Parametros: nenhum parametro foi definido para esta funcao
   * Retorno: int
   ****************************************************************/
  
  public int getTipoCodificacao() {
    return tipoCodificacao;
  }

  /*
   * ***************************************************************
   * Metodo: getMensagem
   * Funcao: retorna a mensagem digitada
   * Parametros: nenhum parametro foi definido para esta funcao
   * Retorno: String
   ****************************************************************/
  
  public String getMensagem() {
    return mensagem;
  }

  /*
   * ***************************************************************
   * Metodo: getTamanhoMensagem
   * Funcao: retorna o comprimento da mensagem
   * Parametros: nenhum parametro foi definido para esta funcaos
   * Retorno: int
   ****************************************************************/
  
  public int getTamanhoMensagem() {
    return tamanhoMensagem;
  }
}