import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Node extends Thread {
    // Atributos privados da classe Node
    private final List<Node> knownNodes = new ArrayList<>(); // Lista de nós conhecidos
    private final Queue<Message> messageQueue = new ConcurrentLinkedQueue<>(); // Fila de mensagens
    private int broadcastsCompleted = 0; // Contagem de transmissões concluídas
    private int totalBroadcasts = 10; // Número total de transmissões desejadas
    private final Map<Direction, Integer> decisionMap = new HashMap<>(); // Mapa de decisões

    // Construtor
    public Node(String name) {
        super(name);
        initializeDecisionMap(); // Inicializa o mapa de decisões
    }

    // Inicializa o mapa de decisões com contagens iniciais zeradas
    private void initializeDecisionMap() {
        for (Direction direction : Direction.values()) {
            decisionMap.put(direction, 0);
        }
    }

    // Registra um novo nó na lista de nós conhecidos
    public void registerNode(Node node) {
        knownNodes.add(node);
    }

    // Método para enviar uma mensagem de broadcast para os nós vizinhos
    public void broadcastMessage(String content, String messageType, String metadata, String format, int size,
                                 String qos) {
        if (broadcastsCompleted < totalBroadcasts) {
            Direction[] directions = Direction.values();
            Direction chosenDirection = directions[new Random().nextInt(directions.length)];

            Set<Node> sentNodes = new HashSet<>(); // Conjunto para armazenar os nós que já receberam a direção

            // Enviando a direção escolhida como mensagem para os outros nós conhecidos
            for (Node node : knownNodes) {
                if (node != this && !sentNodes.contains(node)) {
                    node.receiveDirectionMessage(this, chosenDirection); // Envia a direção para o nó vizinho
                    sentNodes.add(node); // Adiciona o nó à lista de nós para os quais a direção foi enviada
                }
            }

            // Imprimindo a direção para o nó atual
            System.out.println("\nSENDER:");
            System.out.println(getName() + "\n" + chosenDirection);

            // Incrementando a contagem para a posição escolhida apenas para o nó atual
            int currentCount = decisionMap.getOrDefault(chosenDirection, 0);
            decisionMap.put(chosenDirection, currentCount + 1);

            // Restante da lógica de broadcast conforme necessário
            broadcastsCompleted++;
            if (broadcastsCompleted == totalBroadcasts) {
                // Verificar se há maioria de direções antes de resetar o mapa
                Direction majorityDirection = getMajorityDirection();
                if (majorityDirection != null) {
                    System.out.println("Maioria de votos para a direção: " + majorityDirection);
                    // Realizar a ação com base na maioria das direções antes do reset
                    // Faça aqui o que deseja com base na direção majoritária encontrada

                    // Aqui, você pode executar a lógica específica com base na direção majoritária
                    // encontrada
                    // Por exemplo, tomar uma decisão com base nessa maioria antes de resetar o mapa

                    // Resetar o mapa de decisões após tomar ação com base na maioria
                    resetDecisionMap();
                    broadcastsCompleted = 0;
                }
            }
        }
    }

    // Determina a direção com a contagem mais alta no mapa de decisões
    public Direction getMajorityDirection() {
        int maxCount = 0;
        Direction majorityDirection = null;

        for (Map.Entry<Direction, Integer> entry : decisionMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                majorityDirection = entry.getKey();
            }
        }

        return majorityDirection;
    }

    // Recebe uma mensagem de direção de um nó vizinho e atualiza o mapa de decisões
    public void receiveDirectionMessage(Node sender, Direction direction) {
        System.out.println(getName() + " received direction message: " + direction);
        // Incrementando a contagem para a posição recebida apenas para o nó atual
        int currentCount = decisionMap.getOrDefault(direction, 0);
        decisionMap.put(direction, currentCount + 1);
    }

    // Reinicia o mapa de decisões, configurando todas as contagens de direções de volta para zero
    public void resetDecisionMap() {
        for (Direction direction : decisionMap.keySet()) {
            decisionMap.put(direction, 0);
        }
    }

    // Imprime o mapa de decisões atual deste nó
    public void printDecisionMap() {
        System.out.println("\nDecision Map for Node :");
        for (Map.Entry<Direction, Integer> entry : decisionMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println();
    }

    // Sobrescrita do método run() da classe Thread
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            while (!messageQueue.isEmpty()) {
                Message message = messageQueue.poll();
                if (!message.getRoute().contains(this)) {
                    if (message.getMessageType().equals("Direction")) {
                        Direction receivedDirection = Direction.valueOf(message.getContent());
                        // Incrementando a contagem para a posição recebida apenas para o nó atual
                        int currentCount = decisionMap.getOrDefault(receivedDirection, 0);
                        decisionMap.put(receivedDirection, currentCount + 1);
                    } else {
                        message.getRoute().add(this);
                        List<Node> nodesForwarded = new ArrayList<>();
                        for (Node node : knownNodes) {
                            if (node != this && !message.getRoute().contains(node) && !nodesForwarded.contains(node)) {
                                node.receiveDirectionMessage(this, message.getDirection());
                                nodesForwarded.add(node);
                            }
                        }
                    }
                }
            }
        }
    }
}
