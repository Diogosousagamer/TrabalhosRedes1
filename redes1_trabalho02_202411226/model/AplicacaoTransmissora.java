/* ***************************************************************
* Autor............: Diogo Oliveira de Sousa
* Matricula........: 202411226
* Inicio...........: 13/09/2025
* Ultima alteracao.: 23/09/2025
* Nome.............: AplicacaoTransmissora
* Funcao...........: Esta classe tem como objetivo gerenciar os eventos e operacoes da AplicacaoTransmissora,
                     onde o usuario envia a mensagem a ser transmitida para a maquina receptora.
                     
*************************************************************** */

package model;

import controller.TelaPrincipalController;

public class AplicacaoTransmissora {
  /*
   * ***************************************************************
   * Metodo: executarAplicacaoTransmissora
   * Funcao: executa as operacoes da aplicacao transmissora
   * Parametros: nenhum parametro foi definido para esta funcao
   * Retorno: void
   ****************************************************************/

  public void executarAplicacaoTransmissora() {
    // Recebe a mensagem a ser transmitida
    String mensagem = TelaPrincipalController.controller.getMensagem();

    // Inicializa a camada de aplicacao transmissora para preparar a mensagem para a transmissao
    CamadaAplicacaoTransmissora camadaAplicacaoTransmissora = new CamadaAplicacaoTransmissora();
    camadaAplicacaoTransmissora.CamadaDeAplicacaoTransmissora(mensagem);
  }
}
