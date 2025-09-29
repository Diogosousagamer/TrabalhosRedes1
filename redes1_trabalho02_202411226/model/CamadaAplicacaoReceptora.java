/* ***************************************************************
* Autor............: Diogo Oliveira de Sousa
* Matricula........: 202411226
* Inicio...........: 13/09/2025
* Ultima alteracao.: 27/09/2025
* Nome.............: CamadaAplicacaoReceptora
* Funcao...........: Esta classe tem como objetivo gerenciar os eventos e operacoes da camada de aplicacao da maquina
                     receptora, que decodificara a mensagem para sua forma String antes de exibi-la atraves da aplicacao
                     receptora.
                     
*************************************************************** */

package model;

public class CamadaAplicacaoReceptora {
  /* Metodos de instanciacao/execucao */

  /*
   * ***************************************************************
   * Metodo: CamadaDeAplicacaoReceptora
   * Funcao: executa as operacoes da camada de aplicacao receptora
   * Parametros: int[] quadro - vetor contendo a mensagem a ser recebida
   * Retorno: void
   ****************************************************************/

  protected void CamadaDeAplicacaoReceptora(int[] quadro) {
    // Obtem a mensagem transmitida convertendo sua versao compactada em inteiros de volta a sua forma String
    String mensagem = conversaoBitParaString(quadro);

    // Inicializa a aplicacao receptora, exibindo a mensagem e se houve (ou nao) ocorrencia de erros
    AplicacaoReceptora recepcao = new AplicacaoReceptora();
    recepcao.executarAplicacaoReceptora(mensagem);
  }

  /* Metodos auxiliares */
  
  /*
   * ***************************************************************
   * Metodo: conversaoBitParaString
   * Funcao: decodifica o vetor de inteiros para gerar a mensagem a ser recebida
   * Parametros: int[] quadro - vetor contendo os bits a serem decodificados
   * Retorno: String
   ****************************************************************/

  private String conversaoBitParaString(int[] quadro) {
    // String que armazenara a mensagem
    String mensagem = "";

    // Inicio do bloco for
    // Sao percorridos todos os valores inteiros dentro do quadro para recuperar
    // os caracteres que compoem a mensagem
    for (int valor : quadro) {
      // Inicio do bloco for
      for (int i = 3; i >= 0; i--) {
        // Recupera o caractere da mensagem a partir do deslocamento de bit a direita
        char caractere = (char) ((valor >> (i * 8)) & 255);

        // Concatena o caractere dentro da mensagem
        mensagem += caractere;
      } // Fim do bloco for
    } // Fim do bloco for

    // A mensagem e retornada
    return mensagem;
  }
}