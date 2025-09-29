/* ***************************************************************
* Autor............: Diogo Oliveira de Sousa
* Matricula........: 202411226
* Inicio...........: 12/09/2025
* Ultima alteracao.: 18/09/2025
* Nome.............: TelaMenuController
* Funcao...........: Esta classe tem como objetivo gerenciar os eventos e operacoes da TelaMenu.
                     
*************************************************************** */

package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class TelaMenuController implements Initializable {
  /* Componentes da interface */

  // ComboBoxes dos algoritmos de enlace de dados
  @FXML
  private ComboBox<String> comboBoxEnquadramento;

  @FXML
  private ComboBox<String> comboBoxCodificacao;

  @FXML
  private ComboBox<String> comboBoxProbabilidadeErro;

  // Botao de confirmacao
  @FXML
  private Button botaoConfirmar;

  /* Atributos */

  // Inteiros contendo os tipos de algoritmo selecionados
  // para cada operacao da camada de enlace de dados
  private int tipoEnquadramento;

  private int tipoCodificacao;

  private int probabilidadeErro;

  /*
   * ***************************************************************
   * Metodo: initialize
   * Funcao: executa um conjunto de instrucoes durante a inicializacao da aplicacao
   * Parametros: URL location: endereco do programa
   * ResourceBundle resources: recursos para inicializacao
   * Retorno: void
   ****************************************************************/

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // Configura as opcoes da comboBoxEnquadramento e inicializa com o primeiro item selecionado
    ObservableList<String> enquadramento = FXCollections.observableArrayList("Contagem de caracteres", "Insercao de bytes", "Insercao de bits", "Violacoes de codificacao da camada fisica");
    comboBoxEnquadramento.setItems(enquadramento);
    comboBoxEnquadramento.getSelectionModel().selectFirst();

    // Configura as opcoes da comboBoxControleErro e inicializa com o primeiro item selecionado
    ObservableList<String> codificacao = FXCollections.observableArrayList("Codificacao Binaria", "Codificacao Manchester", "Codificacao Manchester Diferencial");
    comboBoxCodificacao.setItems(codificacao);
    comboBoxCodificacao.getSelectionModel().selectFirst();

    // Configura as opcoes da comboBoxControleFluxo e inicializa com o primeiro item selecionado
    ObservableList<String> pErro = FXCollections.observableArrayList("0%", "10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%");
    comboBoxProbabilidadeErro.setItems(pErro);
    comboBoxProbabilidadeErro.getSelectionModel().selectFirst();

    // Carrega os tipos de algoritmos selecionados
    tipoEnquadramento = comboBoxEnquadramento.getSelectionModel().getSelectedIndex();
    tipoCodificacao = comboBoxCodificacao.getSelectionModel().getSelectedIndex();

    String valorSelecionado = (String) comboBoxProbabilidadeErro.getValue();
    String porcentagemErro = valorSelecionado.replace("%", "");
    probabilidadeErro = Integer.parseInt(porcentagemErro);

    /** Metodos que alteram a cor do texto das comboBoxes **/

    // comboBoxEnquadramento
    comboBoxEnquadramento.setButtonCell(new ListCell<String>() {
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

    // comboBoxCodificacao
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

    // comboBoxControleFluxo
    comboBoxProbabilidadeErro.setButtonCell(new ListCell<String>() {
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
  }

  /* Metodos de configuracao dos algoritmos */

  /*
   * ***************************************************************
   * Metodo: definirAlgoritmoEnquadramento
   * Funcao: seleciona o algoritmo de enquadramento a ser executado
   * Parametros: ActionEvent event - evento gerado ao selecionar algum item na comboBox 
   * Retorno: void
   ****************************************************************/

  @FXML
  private void definirAlgoritmoEnquadramento(ActionEvent event) {
    // Armazena a opcao selecionada dentro da variavel tipoEnquadramento
    tipoEnquadramento = comboBoxEnquadramento.getSelectionModel().getSelectedIndex();
  }

  /*
   * ***************************************************************
   * Metodo: definirAlgoritmoCodificacao
   * Funcao: seleciona o algoritmo de codificacao a ser executado
   * Parametros: ActionEvent event - evento gerado ao selecionar algum item na comboBox 
   * Retorno: void
   ****************************************************************/

  @FXML
  private void definirAlgoritmoCodificacao(ActionEvent event) {
    // Armazena a opcao selecionada dentro da variavel tipoControleErro
    tipoCodificacao = comboBoxCodificacao.getSelectionModel().getSelectedIndex();
  }

  /*
   * ***************************************************************
   * Metodo: definirProbabilidadeErro
   * Funcao: seleciona a probabilidade de ocorrencia de erro na transmissao
   * Parametros: ActionEvent event - evento gerado ao selecionar algum item na comboBox 
   * Retorno: void
   ****************************************************************/

  @FXML
  private void definirProbabilidadeErro(ActionEvent event) {
    // Armazena a opcao selecionada dentro da variavel tipoControleFluxo
    String valorSelecionado = (String) comboBoxProbabilidadeErro.getValue();
    String porcentagemErro = valorSelecionado.replace("%", "");
    probabilidadeErro = Integer.parseInt(porcentagemErro);
  }

  /*
   * ***************************************************************
   * Metodo: botaoConfirmarExit
   * Funcao: reseta o botaoConfirmar quando o mouse sai de cima dele
   * Parametros: MouseEvent event - evento gerado ao tirar o mouse de cima do botao
   * Retorno: void
   ****************************************************************/

  @FXML
  private void botaoConfirmarExit(MouseEvent event) {
    // Reseta a cor padrao e mantem o botao arredondado
    botaoConfirmar.setStyle("-fx-background-color:  #32a852; -fx-background-radius: 15px");
  }

  /*
   * ***************************************************************
   * Metodo: botaoConfirmarExit
   * Funcao: reseta o botaoConfirmar quando o mouse sai de cima dele
   * Parametros: MouseEvent event - evento gerado ao colocar o mouse em cima do botao
   * Retorno: void
   ****************************************************************/

  @FXML
  private void botaoConfirmarHover(MouseEvent event) {
    // Altera a cor para um tom mais escuro de verde e mantem o botao arredondado
    botaoConfirmar.setStyle("-fx-background-color:  #296e3cff; -fx-background-radius: 15px");
  }

  /*
   * ***************************************************************
   * Metodo: confirmarConfiguracao
   * Funcao: inicia a simulacao transitando para a TelaPrincipal
   * Parametros: ActionEvent event - evento gerado ao clicar no botao
   * Retorno: void
   ****************************************************************/

  @FXML
  private void confirmarConfiguracao(ActionEvent event) {
    // Inicio do bloco try/catch
    try {
      // Carrega o arquivo FXML da TelaPrincipal para gerar uma nova cena
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaPrincipal.fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root);

      // Carrega uma nova instancia do controller da TelaPrincipal
      TelaPrincipalController controller = loader.getController();

      // Configura os algoritmos de enquadramento, controle de erro e de fluxo
      controller.setEnquadramento(tipoEnquadramento);
      controller.setCodificacao(tipoCodificacao);
      controller.setProbabilidadeErro(probabilidadeErro);

      // Carrega a nova cena dentro da janela ja aberta
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
    }
    catch (IOException e) {
      // Em caso de excecao, ela sera exibida no terminal
      Logger.getLogger(TelaMenuController.class.getName()).log(Level.SEVERE, null, e);
    } // Fim do bloco try/catch
  }
}