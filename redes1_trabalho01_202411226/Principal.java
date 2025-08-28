/* ***************************************************************
* Autor............: Diogo Oliveira de Sousa
* Matricula........: 202411226
* Inicio...........: 14/08/2025
* Ultima alteracao.: 28/08/2025
* Nome.............: Transmisssor de Sinais [com Diogo Oliveira e Gustavo Henrique] (Principal)
* Funcao...........: Como parte da simulacao do funcionamento de uma rede, este trabalho tem como objetivo demonstrar a transmissao de sinais
                     entre os computadores a partir da camada fisica da rede.
                     
*************************************************************** */

import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import controller.TelaPrincipalController;

import java.io.IOException;

public class Principal extends Application {

  /*
   * ***************************************************************
   * Metodo: start
   * Funcao: configura a aplicacao
   * Parametros: Stage stage - janela do programa
   * Retorno: void
   ****************************************************************/

  public void start(Stage stage) {
    // Inicio do bloco try/catch
    try {
      // Instancia o controller da TelaPrincipal para garantir o seu carregamento
      @SuppressWarnings("unused")
      TelaPrincipalController TelaPrincipalController = new TelaPrincipalController();

      // O arquivo FXML e carregado para gerar uma nova cena
      Parent root = FXMLLoader.load(getClass().getResource("/view/TelaPrincipal.fxml"));
      Scene scene = new Scene(root);

      // Carrega o arquivo CSS da tela principal
      scene.getStylesheets().add(getClass().getResource("util/telaPrincipal.css").toExternalForm());

      // Carrega a fonte VCR OSD Mono para que ela possa ser exibida dentro do programa
      Font.loadFont(getClass().getResourceAsStream("/util/VCR_OSD_MONO_1.001.ttf"), 18); 

      /** A janela do programa e configurada antes de ser carregada na tela **/

      // Titulo do programa
      stage.setTitle("Transmissor de Bits (com Diogo Oliveira e Gustavo Henrique)");

      // Importacao da cena
      stage.setScene(scene);

      // Redimensionamento da tela e desativado
      stage.setResizable(false);

      // Carrega o icone a ser adicionado na janela
      Image icone = new Image(getClass().getResource("/img/icone.png").toExternalForm());

      // Adiciona o icone na janela
      stage.getIcons().add(icone);

      // A janela e exibida
      stage.show();
    }
    catch (IOException e) {
      // Em caso de excecao, ela e exibida no terminal
      e.printStackTrace();
    } // Fim do bloco try/catch
  }

  /*
   * ***************************************************************
   * Metodo: main
   * Funcao: executa a aplicacao
   * Parametros: String[] args - vetor contendo argumentos necessarios para a inicializacao do programa
   * Retorno: void
   ****************************************************************/

  public static void main(String[] args) {
    // O programa e executado
    launch(args);
  }
}