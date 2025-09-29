/* ***************************************************************
* Autor............: Diogo Oliveira de Sousa
* Matricula........: 202411226
* Inicio...........: 13/09/2025
* Ultima alteracao.: 23/09/2025
* Nome.............: CamadaAplicacaoTransmissora
* Funcao...........: Esta classe tem como objetivo gerenciar os eventos e operacoes da CamadaAplicacaoReceptora.
                     
*************************************************************** */

package model;

public class CamadaAplicacaoTransmissora {
  /* Métodos de execucao */

  /*
   * ***************************************************************
   * Metodo: CamadaDeAplicacaoTransmissora
   * Funcao: executa as operacoes da camada de aplicacao transmissora
   * Parametros: String mensagem - mensagem a ser transmitida
   * Retorno: void
   ****************************************************************/

  protected void CamadaDeAplicacaoTransmissora(String mensagem) {
    // Gera o quadro apos converter a mensagem para sua forma inteira
    int[] quadro = conversaoBitPorBit(mensagem);

    // E executada a camada de enlace de dados da maquina transmissora para preparar os dados
    // para eventual transmissao
    CamadaEnlaceDadosTransmissora enlaceDadosTransmissora = new CamadaEnlaceDadosTransmissora();
    enlaceDadosTransmissora.CamadaEnlaceDeDadosTransmissora(quadro);
  }

  /* Metodos auxiliares */

  /*
   * ***************************************************************
   * Metodo: conversaoBitPorBit
   * Funcao: converte a mensagem em inteiro para ser codificada
   * Parametros: String mensagem - mensagem a ser codificada
   * Retorno: int[]
   ****************************************************************/

  private int[] conversaoBitPorBit(String mensagem) {
    // Vetor que armazenara a mensagem convertida para inteiro
    int[] quadro = new int[(mensagem.length() + 3) / 4];

    // Contador de posicao, usado para percorrer o vetor durante a insercao dos elementos
    int posicao = 0;

    // Inicio do bloco for
    // Percorremos a mensagem a cada quatro caracteres para que ela possa ser codificada em inteiros de 32 bits
    for (int i = 0; i < mensagem.length(); i += 4) {
      // Criamos uma variavel para armazenar o valor
      int valor = 0;

      // Inicio do bloco for
      // Inserimos a cada quatro caracteres enquanto a soma de indices (i + j) for menor que a mensagem
      for (int j = 0; j < 4 && (i + j) < mensagem.length(); j++) {
        // Obtemos um caractere no seu formato ASCII
        int ascii = mensagem.charAt(i + j);

        // Usamos uma manipulacao de bits para inserir cada caractere dentro do valor sem ocorrer sobreposicao
        valor |= (ascii << (8 * (3 - j)));
      } // Fim do bloco for

      // Insere o valor dentro do vetor
      quadro[posicao++] = valor;
    } // Fim do bloco for

    // O vetor e retornado
    return quadro;
  }
}
