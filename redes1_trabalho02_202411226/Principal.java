/* ***************************************************************
* Autor............: Diogo Oliveira de Sousa
* Matricula........: 202411226
* Inicio...........: 12/09/2025
* Ultima alteracao.: 29/09/2025
* Nome.............: Transmisssor de Enlace de Dados [com Diogo Oliveira e Gustavo Henrique] (Principal)
* Funcao...........: Como parte da simulacao do funcionamento de uma rede, este trabalho tem como objetivo demonstrar a operacao da camada de enlace de dados
                     durante a transmissao de sinais entre computadores conectados via rede.
                     
*************************************************************** */

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import controller.*;

public class Principal extends Application {
  
  /*
   * ***************************************************************
   * Metodo: start
   * Funcao: configura a aplicacao
   * Parametros: Stage stage - janela do programa
   * Retorno: void
   ****************************************************************/

  @Override
  public void start(Stage stage) {
    // Inicio do bloco try/catch
    try {
      // Sao importados os controllers do programa para que todas as classes sejam carregadas corretamente
      @SuppressWarnings("unused")
      TelaMenuController TelaMenuController = new TelaMenuController();
      
      @SuppressWarnings("unused")
      TelaPrincipalController TelaPrincipalController = new TelaPrincipalController();

      // Carrega o arquivo FXML da TelaMenu
      Parent root = FXMLLoader.load(getClass().getResource("/view/TelaMenu.fxml"));
      
      // Carrega a raiz dentro da cena 
      Scene scene = new Scene(root);

      // Carrega a fonte VCR OSD Mono para que ela possa ser exibida dentro do programa
      Font.loadFont(getClass().getResourceAsStream("/util/VCR_OSD_MONO_1.001.ttf"), 18);

      // Carrega a cena (tela) a ser exibida na janela
      stage.setScene(scene);

      // Exibe o titulo do programa na janela
      stage.setTitle("Transmissao de Enlace de Dados (com Diogo Oliveira e Gustavo Henrique)");

      // Carrega a imagem do icone
      Image icone = new Image(getClass().getResource("/img/icone.png").toExternalForm());

      // Adiciona o icone na tela
      stage.getIcons().add(icone);

      // Desativa o redimensionamento da janela
      stage.setResizable(false);

      // Exibe a janela na tela
      stage.show();
    }
    catch (IOException e) {
      // Em caso de excecao, a execucao e interrompida
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
