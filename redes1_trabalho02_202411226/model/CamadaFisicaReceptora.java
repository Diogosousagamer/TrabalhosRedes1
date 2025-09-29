/* ***************************************************************
* Autor............: Diogo Oliveira de Sousa
* Matricula........: 202411226
* Inicio...........: 13/09/2025
* Ultima alteracao.: 26/09/2025
* Nome.............: CamadaFisicaReceptora
* Funcao...........: Esta classe tem como objetivo gerenciar os eventos e operacoes da camada fisica da maquina
                     receptora, responsavel por decodificar os bits recebidos e envia-los para o enlace de dados.
                     
*************************************************************** */

package model;

import controller.TelaPrincipalController;

public class CamadaFisicaReceptora {
  /* Atributos */

  // Vetor de inteiros, responsavel por armazenar o fluxo de bits
  // ja compactado para inteiros  
  private int[] fluxoBrutoDeBits;

  /* Metodos de instanciacao/execucao */

  /*
   * ***************************************************************
   * Metodo: executarCamadaFisicaReceptora
   * Funcao: executa as operacoes da camada fisica receptora
   * Parametros: int[] quadro - vetor de bits a serem decodificados
   * Retorno: void
   ****************************************************************/

  protected void executarCamadaFisicaReceptora(int[] quadro) {
    // Obtem-se o tipo de codificacao selecionado
    int tipoCodificacao = TelaPrincipalController.controller.getTipoCodificacao();

    // Inicio do bloco switch/case
    // A decodificacao sera realizada de acordo com o tipo de codificacao anteriormente
    // selecionado pelo usuario
    switch (tipoCodificacao) {
      case 0: // Executa a decodificacao binaria
        fluxoBrutoDeBits = CamadaFisicaReceptoraCodificacaoBinaria(quadro);
        break;

      case 1: // Executa a decodificacao Manchester
        fluxoBrutoDeBits = CamadaFisicaReceptoraCodificacaoManchester(quadro);
        break;

      case 2: // Executa a decodificacao Manchester diferencial
        fluxoBrutoDeBits = CamadaFisicaReceptoraCodificacaoManchesterDiferencial(quadro);
        break;
    } // Fim do bloco switch/case

    // Inicializa a camada de enlace de dados receptora, que desenquadrara o fluxo de bits
    CamadaEnlaceDadosReceptora enlaceDadosReceptora = new CamadaEnlaceDadosReceptora();
    enlaceDadosReceptora.CamadaEnlaceDeDadosReceptora(fluxoBrutoDeBits);
  }

  /* Metodos de decodificacao */

  /*
   * ***************************************************************
   * Metodo: CamadaFisicaReceptoraCodificacaoBinaria
   * Funcao: decodifica os bits atraves da codificacao binaria
   * Parametros: int[] quadro - vetor de bits a serem decodificados
   * Retorno: int[]
   ****************************************************************/

  private int[] CamadaFisicaReceptoraCodificacaoBinaria(int[] quadro) {
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
   * Metodo: CamadaFisicaReceptoraCodificacaoManchester
   * Funcao: decodifica os bits atraves da codificacao Manchester
   * Parametros: int[] quadro - vetor de bits a serem decodificados
   * Retorno: int[]
   ****************************************************************/

  private int[] CamadaFisicaReceptoraCodificacaoManchester(int[] quadro) {
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
   * Metodo: CamadaFisicaReceptoraCodificacaoManchesterDiferencial
   * Funcao: decodifica os bits atraves da codificacao Manchester Diferencial
   * Parametros: int[] quadro - vetor de bits a serem decodificados
   * Retorno: int[]
   ****************************************************************/

  private int[] CamadaFisicaReceptoraCodificacaoManchesterDiferencial(int[] quadro) {
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