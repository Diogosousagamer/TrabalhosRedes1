/* ***************************************************************
* Autor............: Diogo Oliveira de Sousa
* Matricula........: 202411226
* Inicio...........: 16/08/2025
* Ultima alteracao.: 28/08/2025
* Nome.............: AplicacaoTransmissora
* Funcao...........: Esta classe representa a aplicacao da maquina transmissora, onde a mensagem
                     sera enviada para transmissao. 
                     
*************************************************************** */

package model;

import controller.TelaPrincipalController;

public class AplicacaoTransmissora {

  /* Metodos de instanciacao/operacao da camada */
  
  /*
   * ***************************************************************
   * Metodo: metodoAplicacaoTransmissora
   * Funcao: executa as operacoes da aplicacao da maquina transmissora
   * Parametros: nenhum parametro foi definido para esta funcao
   * Retorno: void
   ****************************************************************/

  public void metodoAplicacaoReceptora() {
    // A mensagem e impressa na caixa de texto do transmissor (Diogo)
    TelaPrincipalController.controller.enviarMensagem(TelaPrincipalController.controller.getMensagem());

    // Cria-se uma nova instancia da camada de aplicacao da maquina transmissora
    // para que ela possa convertir a mensagem em inteiro, e posteriormente em bits
    CamadaAplicacaoTransmissora camadaAplicacaoTransmissora = new CamadaAplicacaoTransmissora(TelaPrincipalController.controller.getMensagem());

    // A camada de aplicacao da maquina transmissora 
    // passa a executar as suas operacoes
    camadaAplicacaoTransmissora.CamadaDeAplicacaoTransmissora();
  }
}