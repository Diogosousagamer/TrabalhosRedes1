/* ***************************************************************
* Autor............: Diogo Oliveira de Sousa
* Matricula........: 202411226
* Inicio...........: 16/08/2025
* Ultima alteracao.: 28/08/2025
* Nome.............: CamadaAplicacaoTransmissora
* Funcao...........: Esta classe representa a camada de aplicacao da maquina transmissora, onde a mensagem
                     sera convertida de String para bits antes de ser passada para a camada fisica.
                     
*************************************************************** */

package model;

public class CamadaAplicacaoTransmissora {
  /* Atributos */

  // String contendo a mensagem a ser enviada
  private String mensagem;

  /* Metodos de instanciacao/operacao da camada */
  
  /*
   * ***************************************************************
   * Metodo: CamadaAplicacaoTransmissora
   * Funcao: cria uma nova instancia da classe
   * Parametros: String mensagem - a mensagem a ser codificada
   * Retorno: nenhum
   ****************************************************************/

  public CamadaAplicacaoTransmissora(String mensagem) {
    // Define a mensagem
    this.mensagem = mensagem;
  }
    
  /*
   * ***************************************************************
   * Metodo: CamadaDeAplicacaoTransmissora
   * Funcao: executa as operacoes da camada de aplicacao da maquina transmissora
   * Parametros: nenhum parametro foi definido para esta funcao
   * Retorno: void
   ****************************************************************/

  protected void CamadaDeAplicacaoTransmissora() {
    // Gera o quadro apos converter a mensagem para sua forma inteira
    int[] quadro = conversaoBitPorBit(mensagem);

    // Titulo indicando o debug do quadro
    System.out.println("Forma inteira da palavra: ");

    // Inicio do bloco for
    for (int bit : quadro) {
      // A palavra e impressa em sua forma inteira
      System.out.println(bit);
    } // Fim do bloco for
   
    // Espacamento para garantir a organizacao das saidas dentro do terminal
    System.out.println();
     
    // Instanciamos a camada fisica da maquina transmissora
    // para codificar os bits para serem transferidos
    CamadaFisicaTransmissora camadaFisicaTransmissora = new CamadaFisicaTransmissora(quadro);

    // A camada fisica da maquina transmissora executara suas operacoes
    camadaFisicaTransmissora.metodoCamadaFisicaTransmissora();
  }

  /*
   * ***************************************************************
   * Metodo: conversaoBitPorBit
   * Funcao: converte a mensagem em inteiro para ser codificada
   * Parametros: String mensagem - 
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