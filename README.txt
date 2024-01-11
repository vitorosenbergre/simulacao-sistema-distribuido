Conceitos e informações:

Proper: Refere-se aos valores elegíveis para uma proposta dentro de um nó.
Proposta: São os valores dentro do conjunto "proper" que cada nó possui.
Decisão: É tomada com base no valor de proposta de um nó que possui o maior relógio lógico.
Decisão final: O resultado final de uma rodada de decisão.
quorum: Total de propostas recebidas.

Roteamento:

Total Order Broadcast

FowardFloodingSerialList = encaminha mensagens para nós conectados na rede, desde que a mensagem seja mais recente (com base no número serial) e não tenha atingido o limite de TTL. O TTL é decrementado a cada encaminhamento para evitar ciclos infinitos na rede.


Estruturas de dados utilizado:

ENUM proposta { LEFT, RIGHT, UP, DOWN }: Conjunto de valores de proposta possíveis.

private final Map<Direction, Integer> decisionMap = new HashMap<>() = Mapeamento das direções para valores específicos.

MgetMajorityDirection (quorum): Avalia maioria dos valores para as propostas de nós.

Tabela de nós:
LEFT: 0
RIGHT: 0
UP: 0
DOWN: 0

Análise do algoritmo:

Processo de tomada de decisão distribuído entre os nós, onde cada nó tem uma proposta dentro de um conjunto de valores permitidos (directions/proper). A decisão é tomada com base no valor de proposta do nó que possui o relógio lógico mais alto (após o fim do turno). 

A estrutura esta configurada para uma simulação de algoritmo de consenso distribuído, mas os valores específicos (LEFT, RIGHT, UP, DOWN).
