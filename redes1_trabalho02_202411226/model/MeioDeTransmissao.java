/* ***************************************************************
* Autor............: Diogo Oliveira de Sousa
* Matricula........: 202411226
* Inicio...........: 13/09/2025
* Ultima alteracao.: 27/09/2025
* Nome.............: MeioDeTransmissao
* Funcao...........: Esta classe tem como objetivo gerenciar os eventos e operacoes do meio de transmissao,
                     que se encarregara de transmitir os bits que compoem a mensagem para a camada fisica
                     maquina receptora.
                     
*************************************************************** */

package model;

import java.util.Random;

import controller.TelaPrincipalController;
import javafx.animation.Timeline;

public class MeioDeTransmissao {
  /* Atributos */

  // Fluxo de bits da maquina transmissora
  private int[] fluxoBrutoDeBitsA;

  // Fluxo de bits a ser enviado para a maquina receptora
  private int[] fluxoBrutoDeBitsB;

  // Timeline que executara a animacao dos sinais
  private Timeline anim;

  /* Metodos de instanciacao/execucao */

  /*
   * ***************************************************************
   * Metodo: MeioTransmissao
   * Funcao: executa as operacoes do meio de transmissao
   * Parametros: int[] fluxoBrutoDeBits - vetor contendo o fluxo de bits a ser transmitido
   * Retorno: void
   ****************************************************************/

  protected void MeioTransmissao(int[] fluxoBrutoDeBits) {
    // O fluxo A recebe o fluxo bruto de bits passado como parametro
    fluxoBrutoDeBitsA = fluxoBrutoDeBits;

    // Transfere o fluxo A para o fluxo B
    fluxoBrutoDeBitsB = transferirBits(fluxoBrutoDeBitsA);

    // Gera a animacao a ser executada 
    anim = gerarAnimacao(fluxoBrutoDeBitsB);

    // Prossegue com a transmissao apos a finalizacao da animacao
    anim.setOnFinished( e -> {
      // Inicializa a camada fisica receptora para receber o fluxo de bits
      CamadaFisicaReceptora camadaFisicaReceptora = new CamadaFisicaReceptora();
      camadaFisicaReceptora.executarCamadaFisicaReceptora(fluxoBrutoDeBitsB);
    });

    // Executa a animacao
    anim.play();
  }

  /* Metodos auxiliares */

  /*
   * ***************************************************************
   * Metodo: transferirBits
   * Funcao: transfere bits de um vetor para o outro (com geracao de erros)
   * Parametros: int[] fluxo - vetor de bits a serem transferidos
   * Retorno: int[]
   ****************************************************************/

  private int[] transferirBits(int[] fluxo) {
    // Cria-se um vetor contendo o novo fluxo de bits
    int[] novoFluxo = new int[fluxo.length];

    // Obtem-se a probabilidade de erro selecionada pelo usuario
    int probabilidadeErro = TelaPrincipalController.controller.getProbabilidadeErro();

    // Numero aleatorio para indicar se o erro sera ou nao gerado
    Random rand = new Random();

    // Inicio do bloco for
    for (int i = 0; i < fluxo.length; i++) {
      // Inicio do bloco if/else
      // Se a probabilidade de erro for atingida ou superada
      if (rand.nextInt(100) >= probabilidadeErro) {
        // O bit real e transferido
        novoFluxo[i] |= fluxo[i];
      }
      else { // Em caso contrario
        // O bit a ser transferido e invertido
        novoFluxo[i] ^= fluxo[i];

        // Sinaliza que houve erro na computacao da mensagem para eventual depuracao
        TelaPrincipalController.controller.temErro = true;
      } // Fim do bloco if/else
    } // Fim do bloco for

    // O novo fluxo computado e retornado
    return novoFluxo;
  }

  /*
   * ***************************************************************
   * Metodo: gerarAnimacao
   * Funcao: gera a animacao a ser executada no painelSinais
   * Parametros: int[] quadro - vetor de bits a ser exibido na animacao
   * Retorno: Timeline
   ****************************************************************/

  private Timeline gerarAnimacao(int[] quadro) {
    // Obtem-se a codificacao que esta sendo executada
    int tipoCodificacao = TelaPrincipalController.controller.getTipoCodificacao();

    // Inicio do bloco switch/case
    // A animacao a ser executada dependera da codificacao executada
    switch (tipoCodificacao) {
      case 0: // Executa a animacao da codificacao binaria
        return TelaPrincipalController.controller.animacaoBinaria(quadro);
    
      case 1: // Executa a animacao da codificacao Manchester
        return TelaPrincipalController.controller.animacaoManchester(quadro);

      case 2: // Executa a animacao da codificacao Manchester Diferencial
        return TelaPrincipalController.controller.animacaoManchesterDiferencial(quadro);
    } // Fim do bloco switch/case

    // Retorna nulo caso nenhuma das codificacoes estiverem sendo executadas
    return null;
  }
}