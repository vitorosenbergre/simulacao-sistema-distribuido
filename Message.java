import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Message {
  private final String content;
  private final Node sender;
  private final Node recipient;
  private final String messageType;
  private final Direction direction; // Adicionando o campo para armazenar a direção da mensagem
  private final String metadata;
  private final String format;
  private final int size;
  private final String qos;
  private final String id;
  private final List<Node> route;

  public Message(String content, Node sender, Node recipient, String messageType, String metadata, String format,
      int size, String qos, Direction direction) {
    this.content = content;
    this.sender = sender;
    this.recipient = recipient;
    this.messageType = messageType;
    this.metadata = metadata;
    this.format = format;
    this.size = size;
    this.qos = qos;
    this.direction = direction;
    this.id = generateUniqueId();
    this.route = new ArrayList<>();
  }

  public String getContent() {
    return content;
  }

  public Node getSender() {
    return sender;
  }

  public Node getRecipient() {
    return recipient;
  }

  public String getMessageType() {
    return messageType;
  }

  public String getMetadata() {
    return metadata;
  }

  public String getFormat() {
    return format;
  }

  public int getSize() {
    return size;
  }

  public String getQoS() {
    return qos;
  }

  public String getId() {
    return id;
  }

  public Direction getDirection() {
    return direction;
  }

  public List<Node> getRoute() {
    return route;
  }

  private String generateUniqueId() {
    return UUID.randomUUID().toString();
  }
}
