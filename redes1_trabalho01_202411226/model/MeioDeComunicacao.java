/* ***************************************************************
* Autor............: Diogo Oliveira de Sousa
* Matricula........: 202411226
* Inicio...........: 16/08/2025
* Ultima alteracao.: 28/08/2025
* Nome.............: MeioDeComunicacao
* Funcao...........: Esta classe representa o meio onde a mensagem sera comunicada entre as maquinas.
                     
*************************************************************** */

package model;

import controller.TelaPrincipalController;
import javafx.animation.Timeline;

public class MeioDeComunicacao {
  /* Atributos */

  // Vetor contendo o fluxo bruto de bits do transmissor
  private int[] fluxoBrutoDeBitsPontoA;

  // Vetor contendo o fluxo bruto de bits a ser transferido para o receptor
  private int[] fluxoBrutoDeBitsPontoB;

  // Vetor contendo o fluxo bruto de bits enviado pela camada fisica da maquina transmissora
  private int[] fluxoBrutoDeBits;

  // Timeline que se encarregara de executar os sinais dos bits
  private Timeline anim;

  /* Metodos de instanciacao/operacao da camada */

  /*
   * ***************************************************************
   * Metodo: MeioDeComunicacao
   * Funcao: cria uma nova instancia da camada
   * Parametros: int[] fluxoBrutoDeBits - vetor contendo a mensagem codificada em bits
   * Retorno: nenhum
   ****************************************************************/

  public MeioDeComunicacao(int[] fluxoBrutoDeBits) {
    // Define o vetor contendo o fluxo bruto de bits
    this.fluxoBrutoDeBits = fluxoBrutoDeBits;
  }
    
  /*
   * ***************************************************************
   * Metodo: MeioComunicacao
   * Funcao: executa as operacoes do meio de comunicacao das maquinas
   * Parametros: nenhum parametro foi definido para esta funcao
   * Retorno: void
   ****************************************************************/

  protected void MeioComunicacao() {
    // Flxuo bruto de bits do transmissor passa a receber o fluxo bruto de bits obtido
    fluxoBrutoDeBitsPontoA = fluxoBrutoDeBits;

    // Gera o fluxo bruto de bits a ser comunicado para a maquina receptora    
    fluxoBrutoDeBitsPontoB = gerarFluxoDeBits(fluxoBrutoDeBitsPontoA);

    // Titulo indicando o debug do fluxo de bits no ponto B
    System.out.println("Fluxo de bits codificado: ");

    // Inicio do bloco for
    for (int valor : fluxoBrutoDeBitsPontoB) {
      // Cada bit contido no fluxo a ser transferido para a outra maquina e impresso no terminak
      // por motivos de depuracao
      System.out.print(valor);
    } // Fim do bloco for

    // Comandos para dar espaco duas vezes de modo que as saidas nao fiquem coladas
    // umas as outras dentro do terminal
    System.out.println();
    System.out.println();

    // Gera a animacao a ser executada de acordo com o tipo de codificacao selecionado
    anim = gerarAnimacao(fluxoBrutoDeBitsPontoB);
    
    // Inicio do bloco setOnFinished
    anim.setOnFinished(e -> {
      // A camada fisica receptora e executada depois de a animacao de transferencia de sinais
      // ser finalizada
      CamadaFisicaReceptora camadaFisicaReceptora = new CamadaFisicaReceptora(fluxoBrutoDeBitsPontoB);
      camadaFisicaReceptora.metodoCamadaFisicaReceptora();
    }); // Fim do bloco setOnFinished

    // Os sinais sao exibidos dentro do painel
    anim.play();
  }

  /*
   * ***************************************************************
   * Metodo: gerarFluxoDeBits
   * Funcao: gera o fluxo de bits a ser comunicado para a maquina
   * Parametros: int[] fluxo - vetor contendo o fluxo de bits a ser transferido
   * Retorno: int[]
   ****************************************************************/

  private int[] gerarFluxoDeBits(int[] fluxo) {
    // Criamos um vetor para armazenar o novo fluxo
    int[] novoFluxo = null;

    // Inicio do bloco if/else
    // Se o tipo de codificacao selecionado for a binaria
    if (TelaPrincipalController.controller.getTipoDeCodificacao() == 0) {
      // Inicializamos o novo fluxo com o tamanho do fluxo passado como parametro vezes 32
      // pois inseriremos a palavra bit a bit dentro do vetor
      novoFluxo = new int[fluxo.length * 32];

      // Contador de posicao, usado para percorrer o vetor
      int posicao = 0;

      // Inicio do bloco for
      // Percorremos os elementos do fluxo para terem seus bits lidos e inseridos
      for (int i = 0; i < fluxo.length; i++) {
        // Inicio do bloco for
        // Percorremos do bit mais significativo ate o menos significativo
        for (int j = 31; j >= 0; j--) {
          // Realizamos a leitura de um bit de um certo elemento do vetor
          int bit = (fluxo[i] >> j) & 1;

          // Inserimos o bit obtido dentro do novo fluxo de bits
          novoFluxo[posicao] |= bit;

          // Incrementamos o contador de posicao para que possamos inserir os demais elementos
          posicao++;
        } // Fim do bloco for
      } // Fim do bloco for
    }
    else { // Para os demais casos
      // Inicializamos o novo fluxo com o mesmo tamanho do fluxo original
      novoFluxo = new int[fluxo.length];

      // Inicio do bloco for
      for (int i = 0; i < fluxo.length; i++) {
        // Simplesmente fazemos a transferencia dos bits do fluxo original para o novo fluxo de bits
        novoFluxo[i] |= fluxo[i];
      } // Fim do bloco for
    } // Fim do bloco if/else

    // O novo fluxo de bits e retornado
    return novoFluxo;
  }

  /*
   * ***************************************************************
   * Metodo: gerarAnimacao
   * Funcao: retorna a animacao da onda a ser executada de acordo com o tipo de codificacao
   * Parametros: int[] fluxo - vetor contendo o fluxo de bits a ser transferido
   * Retorno: void
   ****************************************************************/

  private Timeline gerarAnimacao(int[] fluxo) {
    // Inicio do bloco switch/case
    // A animacao a ser realizada sera determinada de acordo com o tipo de codificacao selecionado
    switch (TelaPrincipalController.controller.getTipoDeCodificacao()) {
      case 0: // Realiza a animacao da codificacao binaria
        return TelaPrincipalController.controller.animacaoBinaria(fluxo);

      case 1: // Realiza a animacao da codificacao Manchester
        return TelaPrincipalController.controller.animacaoManchester(fluxo);

      case 2: // Realiza a animacao da codificacao Manchester diferencial
        return TelaPrincipalController.controller.animacaoManchesterDiferencial(fluxo);
    } // Fim do bloco switch/case

    // Retorna nulo caso as condicoes citadas nao forem validas
    return null;
  }
}