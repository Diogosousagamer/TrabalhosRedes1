/* ***************************************************************
* Autor............: Diogo Oliveira de Sousa
* Matricula........: 202411226
* Inicio...........: 16/08/2025
* Ultima alteracao.: 28/08/2025
* Nome.............: CamadaFisicaReceptora
* Funcao...........: Esta classe representa a camada fisica da maquina receptora, onde a mensagem sera
                     decodificada antes de ser enviada para a camada de aplicacao. 
                     
*************************************************************** */

package model;

import controller.TelaPrincipalController;

public class CamadaFisicaReceptora {
  /* Atributos */

  // Vetor contendo o fluxo de bits codificado
  private int[] quadro;

  // Vetor contendo o fluxo de bits decodificado
  private int[] fluxoBrutoDeBits;

  /* Metodos de instanciacao/operacao da camada */

  /*
   * ***************************************************************
   * Metodo: CamadaFisicaReceptora
   * Funcao: cria uma nova instancia da camada
   * Parametros: int[] quadro - vetor contendo a mensagem codificada em bits
   * Retorno: nenhum
   ****************************************************************/

  public CamadaFisicaReceptora(int[] quadro) {
    // Define o quadro de bits
    this.quadro = quadro;
  }

  /*
   * ***************************************************************
   * Metodo: metodoCamadaFisicaReceptora
   * Funcao: executa as operacoes da camada fisica da maquina receptora
   * Parametros: nenhum parametro foi definido para esta funcao
   * Retorno: void
   ****************************************************************/

  protected void metodoCamadaFisicaReceptora() {
    // Inicio do bloco switch/case
    // A decodificacao sera realizada de acordo com o tipo de decodificacao anteriormente
    // selecionado pelo usuario
    switch (TelaPrincipalController.controller.getTipoDeDecodificacao()) {
      case 0: // Executa a decodificacao binaria
        fluxoBrutoDeBits = camadaFisicaReceptoraCodificacaoBinaria(quadro);
        break;

      case 1: // Executa a decodificacao Manchester
        fluxoBrutoDeBits = camadaFisicaReceptoraCodificacaoManchester(quadro);
        break;

      case 2: // Executa a decodificacao Manchester diferencial
        fluxoBrutoDeBits = camadaFisicaReceptoraCodificacaoManchesterDiferencial(quadro);
        break;
    } // Fim do bloco switch/case

    // Executa a camada de aplicacao da maquina receptora apos decodificar o fluxo de bits
    CamadaAplicacaoReceptora camadaAplicacaoReceptora = new CamadaAplicacaoReceptora(fluxoBrutoDeBits);
    camadaAplicacaoReceptora.CamadaDeAplicacaoReceptora();
  }

  /* Metodos de decodificacao */

  /*
   * ***************************************************************
   * Metodo: camadaFisicaReceptoraCodificacaoBinaria
   * Funcao:  realiza a decodificacao com base na codificacao binaria
   * Parametros: int[] quadro - vetor contendo a mensagem em bits a ser decodificada
   * Retorno: int[]
   ****************************************************************/

  private int[] camadaFisicaReceptoraCodificacaoBinaria(int[] quadro) {
    // O vetor tera como tamanho o comprimento do vetor original dividido por 32
    int[] resultado = new int[quadro.length / 32];

    // Contador de posicao, usado para percorrer o vetor
    int posicao = 0;

    // Inicio do bloco for
    // Percorremos o vetor resultado para decodificar o fluxo de bits passado como parametro
    for (int i = 0; i < resultado.length; i++) {
      // Variavel inteira que armazenara o valor ja convertido
      int valor = 0;

      // Inicio do bloco for
      // Percorremos de 0 a 32 para garantir que todos os bits sejam lidos
      for (int j = 0; j < 32; j++) {
        // Deslocamos o valor para a esquerda
        valor <<= 1;

        // Inserimos um bit dentro do valor
        valor |= quadro[posicao];

        // Incrementamos a posicao
        posicao++;
      } // Fim do bloco for

      // O valor e inserido dentro do vetor resultado
      resultado[i] = valor;
    } // Fim do bloco for
    
    // O resultado e retornado
    return resultado;
  }

  /*
   * ***************************************************************
   * Metodo: camadaFisicaReceptoraCodificacaoManchester
   * Funcao:  realiza a decodificacao com base na codificacao manchester
   * Parametros: int[] quadro - vetor contendo a mensagem em bits a ser decodificada
   * Retorno: int[]
   ****************************************************************/

  private int[] camadaFisicaReceptoraCodificacaoManchester(int[] quadro) {
    // O resultado possuira o tamanho igual ao comprimento do quadro dividido por 64
    int[] resultado = new int[quadro.length / 64];

    // Ponteiro de posicao, usado para percorrer o vetor
    int posicao = 0;

    // Inicio do bloco for
    // Percorremos o vetor resultado para decodificar o fluxo de bits passado como parametro
    for (int i = 0; i < resultado.length; i++) {
      // Variavel inteira que armazenara o valor ja convertido
      int valor = 0;

      // Inicio do bloco for
      // Percorremos de 0 a 32 para garantir que todos os bits sejam decodificados
      for (int j = 0; j < 32; j++) {
        // Obtemos o primeiro e segundo elementos dentro do vetor
        // conforme indicados pelo contador de posicao
        int primeiro = quadro[posicao++];
        int segundo = quadro[posicao++];

        // Variavel que armazenara o bit a ser inserido
        int bit = 0;

        // Inicio do bloco if/else if/else
        // Se o sinal for baixo-alto (0 e 1)
        if (primeiro == 0 && segundo == 1) {
          // O bit a ser inserido sera 0
          bit = 0;
        }
        else if (primeiro == 1 && segundo == 0) { // Caso for alto-baixo (1 e 0)
          // O bit a ser inserido sera 1
          bit = 1;
        }
        else { // Caso nenhuma dessas condicoes forem atendidas
          // Lancamos uma excecao indicando uma sequencia invalida de bits 
          throw new IllegalArgumentException("Sequencia invalida de bits.");
        } // Fim do bloco if/else if/else

        // O nivel antecessor sera o sinal indicado pelo segundo elemento
        valor = (valor << 1) | bit;
      } // Fim do bloco for

      // O valor e inserido dentro do vetor resultado
      resultado[i] = valor;
    } // Fim do bloco for

    // O resultado e retornado
    return resultado;
  }

  /*
   * ***************************************************************
   * Metodo: camadaFisicaReceptoraCodificacaoManchesterDiferencial
   * Funcao:  realiza a decodificacao com base na codificacao manchester diferencial
   * Parametros: int[] quadro - vetor contendo a mensagem em bits a ser decodificada
   * Retorno: int[]
   ****************************************************************/

  private int[] camadaFisicaReceptoraCodificacaoManchesterDiferencial(int[] quadro) {
    // O resultado possuira o tamanho igual ao comprimento do quadro dividido por 64
    int[] resultado = new int[quadro.length / 64];

    // Ponteiro de posicao, usado para percorrer o vetor
    int posicao = 0;

    // Atribui-se um nivel anterior arbitrario
    int nivelAnterior = 1;

    // Inicio do bloco for
    // Percorremos o vetor resultado para decodificar o fluxo de bits passado como parametro
    for (int i = 0; i < resultado.length; i++) {
      // Variavel inteira que armazenara o valor ja convertido
      int valor = 0;

      // Inicio do bloco for
      // Percorremos de 0 a 32 para garantir que todos os bits sejam decodificados
      for (int j = 0; j < 32; j++) {
        // Obtemos o primeiro e segundo elementos dentro do vetor
        // conforme indicados pelo contador de posicao
        int primeiro = quadro[posicao++];
        int segundo = quadro[posicao++];

        // Variavel que armazenara o bit a ser inserido
        int bit = 0;

        // Inicio do bloco if/else
        // Se o primeiro elemento for igual ao nivel antecessor
        if (primeiro == nivelAnterior) {
          // O bit sera 1, pois indica ausencia de transicao
          bit = 1;
        }
        else { // Caso contrario
          // O bit sera 0, pois havera alguma transicao entre os sinais
          bit = 0;
        } // Fim do bloco if/else

        // Inserimos o bit dentro do valor a ser obtido a partir do deslocamento a esquerda
        valor = (valor << 1) | bit;
        
        // O nivel antecessor sera o sinal indicado pelo segundo elemento
        nivelAnterior = segundo;
      } // Fim do bloco for

      // O valor e inserido dentro do vetor resultado
      resultado[i] = valor;
    } // Fim do vloco for

    // O resultado e retornado
    return resultado;
  }
}
