/* ***************************************************************
* Autor............: Diogo Oliveira de Sousa
* Matricula........: 202411226
* Inicio...........: 16/08/2025
* Ultima alteracao.: 24/08/2025
* Nome.............: AplicacaoReceptora
* Funcao...........: Esta classe representa a aplicacao da maquina receptora, onde a mensagem recebida
                     sera impressa. 
                     
*************************************************************** */

package model;

import controller.TelaPrincipalController;

public class AplicacaoReceptora {
  /* Atributos */

  // String contendo a mensagem a ser recebida pelo receptor depois de decodificada
  private String mensagem;

  /* Metodos de instanciacao/operacao da camada */
  
  /*
   * ***************************************************************
   * Metodo: AplicacaoReceptora
   * Funcao: cria uma nova instancia da classe
   * Parametros: String mensagem - mensagem a ser recebida
   * Retorno: void
   ****************************************************************/

  public AplicacaoReceptora(String mensagem) {
    // Define a mensagem a ser recebida
    this.mensagem = mensagem;
  }
  
  /*
   * ***************************************************************
   * Metodo: metodoAplicacaoReceptora
   * Funcao: executa as operacoes da aplicacao da maquina receptora
   * Parametros: nenhum parametro foi definido para esta funcao
   * Retorno: void
   ****************************************************************/

  protected void metodoAplicacaoReceptora() {
    // O receptor (Gustavo) recebe a mensagem decodificada, imprimindo ela na sua caixa de texto
    TelaPrincipalController.controller.receberMensagem(mensagem);

    // Os componentes da interface sao reativados, permitindo que outra mensagem seja enviada
    TelaPrincipalController.controller.reativarComponentes();
  }
}
