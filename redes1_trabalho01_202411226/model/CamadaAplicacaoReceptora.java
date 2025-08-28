/* ***************************************************************
* Autor............: Diogo Oliveira de Sousa
* Matricula........: 202411226
* Inicio...........: 16/08/2025
* Ultima alteracao.: 28/08/2025
* Nome.............: CamadaAplicacaoReceptora
* Funcao...........: Esta classe representa a camada de aplicacao da maquina receptora, onde a mensagem
                     e decodificada para String antes de ser recebida. 
                     
*************************************************************** */

package model;

import controller.TelaPrincipalController;

public class CamadaAplicacaoReceptora {
  /* Atributos */

  // Vetor contendo a mensagem decodificada
  private int[] quadro;

  public CamadaAplicacaoReceptora(int[] quadro) {
    // Define o vetor contendo a mensagem
    this.quadro = quadro;
  }

  /*
   * ***************************************************************
   * Metodo: rodar
   * Funcao: executa as operacoes da camada de aplicacao da maquina receptora
   * Parametros: nenhum parametro foi definido para esta funcao
   * Retorno: void
   ****************************************************************/

  protected void CamadaDeAplicacaoReceptora() {
    // Geramos a mensagem enviada decodificando o vetor passado como parametro
    String mensagem = conversaoBitParaString(quadro);

    // Colocamos a aplicacao da maquina receptora para rodar
    AplicacaoReceptora aplicacaoReceptora = new AplicacaoReceptora(mensagem);
    aplicacaoReceptora.metodoAplicacaoReceptora();
  }

  /*
   * ***************************************************************
   * Metodo: conversaoBitParaString
   * Funcao: decodifica o vetor de inteiros para gerar a mensagem a ser recebida
   * Parametros: int[] quadro - vetor contendo os bits a serem decodificados
   * Retorno: String
   ****************************************************************/

  private String conversaoBitParaString(int[] quadro) {
    // String contendo a mensagem a ser obtida
    String mensagem = "";

    // Vetor responsavel por armazenar os caracteres que conterao a mensagem
    char[] caracteres = new char[TelaPrincipalController.controller.getMensagem().length()];

    // Contador responsavel por indicar a quantidade de caracteres ja recuperados
    int caracteresRecuperados = 0;

    // Inicio do bloco for
    // Percorremos cada valor existente no vetor original
    for (int valor : quadro) {
      // Inicio do bloco for
      // Realizamos a decodificacao a cada quatro iteracoes
      for (int j = 3; j >= 0; j--) {
        // Inicio do bloco if/else
        // Se a quantidade de caracteres recuperados for maior ou igual ao comprimento da mensagem original
        if (caracteresRecuperados >= TelaPrincipalController.controller.getTamanhoMensagem()) {
          // O laco e interrompido
          break;
        }
        else { // Caso contrario
          // Recuperamos o caractere a partir da manipulacao de bits
          caracteres[caracteresRecuperados++] = (char) ((valor >> (j * 8)) & 0xFF);
        }
      } // Fim do bloco for
    } // Fim do bloco for

    // A mensagem e formada a partir do vetor contendo os caracteres decodificados
    mensagem = new String(caracteres);

    // A mensagem e retornada
    return mensagem;
  }
}
