/* ***************************************************************
* Autor............: Diogo Oliveira de Sousa
* Matricula........: 202411226
* Inicio...........: 16/08/2025
* Ultima alteracao.: 28/08/2025
* Nome.............: CamadaFisicaTransmissora
* Funcao...........: Esta classe contem as propriedades da camada fisica da maquina transmissora, onde
                     ocorrera a codificacao da mensagem (convertida em bits) para eventual transmissao.
                     
*************************************************************** */

package model;

import controller.TelaPrincipalController;

public class CamadaFisicaTransmissora {
  /* Atributos */

  // Vetor de bits a serem codificados para transmissao
  private int[] quadro;

  // Vetor contendo o fluxo bruto dos bits codificados
  private int[] fluxoBrutoDeBits;
    
  /* Metodos de instanciacao/operacao da camada */

  /*
   * ***************************************************************
   * Metodo: CamadaFisicaTransmissora
   * Funcao: cria uma nova instancia da camada
   * Parametros: int[] quadro - vetor contendo a mensagem codificada em bits
   * Retorno: nenhum
   ****************************************************************/

  public CamadaFisicaTransmissora(int[] quadro) {
    // Define o vetor de bits a serem codificados para transmissao
    this.quadro = quadro;
  }
    
  /*
   * ***************************************************************
   * Metodo: metodoCamadaFisicaTransmissora
   * Funcao: executa as operacoes da camada fisica da maquina transmissora
   * Parametros: nenhum parametro foi definido para esta funcao
   * Retorno: void
   ****************************************************************/

  protected void metodoCamadaFisicaTransmissora() {
    // Inicio do bloco switch/case
    // Os bits serao codificados de acordo com tipo de codificacao selecionado
    switch (TelaPrincipalController.controller.getTipoDeCodificacao()) {
      case 0: // Executa a codificacao binaria
        fluxoBrutoDeBits = camadaFisicaTransmissoraCodificacaoBinaria(quadro);
        break;

      case 1: // Executa a codificacao manchester
        fluxoBrutoDeBits = camadaFisicaTransmissoraCodificacaoManchester(quadro);
        break;

      case 2: // Executa a codificacao manchester diferencial
        fluxoBrutoDeBits = camadaFisicaTransmissoraCodificacaoManchesterDiferencial(quadro);
        break;
    } // Fim do bloco switch/case

    // Instancia o meio de comunicacao para que a mensagem seja transmitida ao receptor
    MeioDeComunicacao meioDeComunicacao = new MeioDeComunicacao(fluxoBrutoDeBits);

    // O meio de comunicacao executa suas operacoes
    meioDeComunicacao.MeioComunicacao();
  }

  /* Metodos de codificacao */
    
  /*
   * ***************************************************************
   * Metodo: camadaFisicaTransmissoraCodificacaoBinaria
   * Funcao: codifica os bits atraves da codificacao binaria
   * Parametros: int[] quadro - vetor de bits a serem codificados
   * Retorno: int[]
   ****************************************************************/

  private int[] camadaFisicaTransmissoraCodificacaoBinaria(int[] quadro) {
    // Como a modificacao do fluxo de bits ocorre apenas dentro do meio de comunicacao
    // neste caso apenas retornamos o quadro
    return quadro;
  }
    
  /*
   * ***************************************************************
   * Metodo: camadaFisicaTransmissoraCodificacaoManchester
   * Funcao: codifica os bits atraves da codificacao manchester
   * Parametros: int[] quadro - vetor de bits a serem codificados
   * Retorno: int[]
   ****************************************************************/

  private int[] camadaFisicaTransmissoraCodificacaoManchester(int[] quadro) {
    // Vetor encarregado de armazenar os resultados
    // O tamanho e o dobro do vetor passado como parametro, pois na codificacao Manchester e na sua versao
    // diferencial, ela divide o sinal de um bit na metade, formando dois bits e assim, gastando o dobro de
    // largura da banda.
    int[] resultado = new int[quadro.length * 64];

    // Ponteiro de posicao, usado para percorrer o vetor resultado
    int posicao = 0;

    // Mascara usada para a leitura de bits
    int mascara = 1;

    // Inicio do bloco for
    // Percorremos todos os elementos do quadro para realizar a codificacao
    for (int i = 0; i < quadro.length; i++) {
      // Inicio do bloco for
      // Percorremos do bit mais significativo ate o menos significativo
      for (int j = 31; j >= 0; j--) {
        // Realizamos a leitura de um bit de um certo elemento do vetor
        int bit = mascara & (quadro[i] >> j);

        // Inicio do bloco if/else
        // Se o bit obtido for 0
        if (bit == 0) {
          // O primeiro e segundo elementos serao 0 e 1, respectivamente (baixo-alto)
          resultado[posicao++] |= 0;
          resultado[posicao++] |= 1;
        }
        else { // Caso for 1
          // O primeiro e segundo elementos serao 1 e 0, respectivamente (alto-baixo)
          resultado[posicao++] |= 1;
          resultado[posicao++] |= 0;
        } // Fim do bloco if/else
      } // Fim do bloco for
    } // Fim do bloco for

    // O resultado final e retornado
    return resultado;
  }
    
  /*
   * ***************************************************************
   * Metodo: camadaFisicaTransmissoraCodificacaoManchesterDiferencial
   * Funcao: codifica os bits atraves da codificacao manchester diferenciaç
   * Parametros: int[] quadro - vetor de bits a serem codificados
   * Retorno: int[]
   ****************************************************************/

  private int[] camadaFisicaTransmissoraCodificacaoManchesterDiferencial(int[] quadro) {
    // Vetor encarregado de armazenar os resultados
    // O tamanho e o tamanho do quadro vezes 64 (32 * 2), pois na codificacao Manchester e na sua versao
    // diferencial, ela divide o sinal de um bit na metade, formando dois bits e assim, gastando o dobro de
    // largura da banda.
    int[] resultado = new int[quadro.length * 64];

    // Ponteiro de posicao, usado para percorrer o vetor
    int posicao = 0;

    // Nivel inicial arbitrario
    int nivelAtual = 1;

    // Mascara usada para a leitura de bits
    int mascara = 1;

    // Inicio do bloco for
    // Percorremos todos os elementos do quadro para realizar a codificacao
    for (int i = 0; i < quadro.length; i++) {
      // Inicio do bloco for
      // Percorremos do bit mais significativo ate o menos significativo
      for (int j = 31; j >= 0; j--) {
        // Realizamos a leitura de um bit de um certo elemento do vetor
        int bit = mascara & (quadro[i] >> j);

        // Inicio do bloco if
        // Se o bit for igual a 0
        if (bit == 0) {
          // O nivel e trocado para sinalizar transicao
          nivelAtual = trocar(nivelAtual);
        } // Fim do bloco if

        // Inserimos o nivel atual obtido
        resultado[posicao++] |= nivelAtual;

        // Realizamos a troca para obter a segunda parte do sinal
        nivelAtual = trocar(nivelAtual);

        // O novo nivel obtido e inserido dentro do vetor
        resultado[posicao++] |= nivelAtual;
      } // Fim do bloco for
    } // Fim do bloco for

    // O resultado e retornado
    return resultado;
  }

  /* Metodos auxiliares */
      
  /*
   * ***************************************************************
   * Metodo: trocar
   * Funcao: o bit e trocado de acordo com o seu valor
   * Parametros: int x - bit a ser trocado
   * Retorno: int[]
   ****************************************************************/

  public static int trocar(int x) {
    // Se o valor for 0, retorna 1
    // Caso contrario, retorna 0
    return x == 0 ? 1 : 0;
  }
}