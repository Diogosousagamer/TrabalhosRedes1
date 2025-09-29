/* ***************************************************************
* Autor............: Diogo Oliveira de Sousa
* Matricula........: 202411226
* Inicio...........: 13/09/2025
* Ultima alteracao.: 27/09/2025
* Nome.............: AplicacaoReceptora
* Funcao...........: Esta classe tem como objetivo gerenciar os eventos e operacoes da AplicacaoReceptora.
                     
*************************************************************** */

package model;

import controller.TelaPrincipalController;

public class AplicacaoReceptora {
  /* Metodos de instanciacao/execucao */

  /*
   * ***************************************************************
   * Metodo: executarAplicacaoReceptora
   * Funcao: executa as operacoes da aplicacao receptora
   * Parametros: String mensagem - mensagem a ser recebida pelo receptor
   * Retorno: void
   ****************************************************************/

  protected void executarAplicacaoReceptora(String mensagem) {
    // Exibe a mensagem recebida dentro do monitor receptor (Gustavo)
    TelaPrincipalController.controller.receberMensagem(mensagem);

    // Mostra ao usuario se houve ocorrencia de erros durante a transmissao
    TelaPrincipalController.controller.retornarDepuracao();
  }
}
