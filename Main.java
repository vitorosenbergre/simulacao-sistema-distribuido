import java.util.*;

public class Main {
  public static void main(String[] args) {
    // Criando os nós
    Node nodeA = new Node("Node A");
    Node nodeB = new Node("Node B");
    Node nodeC = new Node("Node C");
    Node nodeD = new Node("Node D");
    Node nodeE = new Node("Node E");
    Node nodeF = new Node("Node F");
    Node nodeG = new Node("Node G");
    Node nodeH = new Node("Node H");
    Node nodeI = new Node("Node I");
    Node nodeJ = new Node("Node J");

    // Registrando os nós entre si
    nodeA.registerNode(nodeB);
    nodeA.registerNode(nodeC);
    nodeA.registerNode(nodeD);
    nodeA.registerNode(nodeE);
    nodeA.registerNode(nodeF);
    nodeA.registerNode(nodeG);
    nodeA.registerNode(nodeH);
    nodeA.registerNode(nodeI);
    nodeA.registerNode(nodeJ);

    // Registrando nós existentes com os novos nós
    Node[] allNodes = { nodeA, nodeB, nodeC, nodeD, nodeE, nodeF, nodeG, nodeH, nodeI, nodeJ };
    for (Node node : allNodes) {
      node.registerNode(nodeA);
      node.registerNode(nodeB);
      node.registerNode(nodeC);
      node.registerNode(nodeD);
      node.registerNode(nodeE);
      node.registerNode(nodeF);
      node.registerNode(nodeG);
      node.registerNode(nodeH);
      node.registerNode(nodeI);
      node.registerNode(nodeJ);
    }

    // Iniciando os nós
    nodeA.start();
    nodeB.start();
    nodeC.start();
    nodeD.start();
    nodeE.start();
    nodeF.start();
    nodeG.start();
    nodeH.start();
    nodeI.start();
    nodeJ.start();

    // Realizando 10 broadcasts com nós diferentes enviando a mensagem
    Node[] nodes = { nodeA, nodeB, nodeC, nodeD, nodeE, nodeF, nodeG, nodeH, nodeI, nodeJ };
    for (int i = 0; i < 20; i++) {

      if (i == 0) {
        System.out.println("\n-----------------PRIMEIRO TURNO-----------------\n");
      } else if (i == 10) {
        System.out.println("ROUND Quorum: " + nodes[0].getMajorityDirection() + " \n");
        System.out.println("\n-----------------SEGUNDO TURNO-----------------\n");
        // Resetar o mapa de decisões de cada nó após 5 broadcasts
        for (Node node : nodes) {
          node.resetDecisionMap();
        }
      }

      System.out.println("\nINICIO DO BROADCAST\n");
      // Escolhendo um nó diferente para enviar a mensagem em cada broadcast
      Node sender = nodes[i % nodes.length]; // Usando o operador % para obter um índice válido no array

      System.out.println("SENDER:\n" + sender.getName() + "\n");
      // Simulando um broadcast
      sender.broadcastMessage("Broadcast message from " + sender.getName() + "!", "Broadcast", "metadata", "format", 20,
          "QoS");

      // Aguardando um pouco entre cada broadcast
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      // Após cada broadcast, imprimir a quantidade de cada posição para o primeiro nó
      nodes[0].printDecisionMap();

      if (i == 19) {
        System.out.println("ROUND Quorum: " + nodes[0].getMajorityDirection() + " \n");
      }
    }

    // Encerrando os nós após os broadcasts
    for (Node node : nodes) {
      node.interrupt();
    }
  }
}
