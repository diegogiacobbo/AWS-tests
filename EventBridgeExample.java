import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.*;

public class EventBridgeExample {

    public static void main(String[] args) {
        // Configurar as credenciais e a região
        DefaultCredentialsProvider credentialsProvider = DefaultCredentialsProvider.create();
        Region region = Region.US_EAST_1; // Altere para a região desejada

        // Criar um cliente do EventBridge
        EventBridgeClient eventBridgeClient = EventBridgeClient.builder()
                .region(region)
                .credentialsProvider(credentialsProvider)
                .build();

        // Criar um evento
        PutEventsRequestEntry eventEntry = PutEventsRequestEntry.builder()
                .source("my-application")
                .detailType("my-event")
                .detail("{\"key1\":\"value1\",\"key2\":\"value2\"}") // Detalhes adicionais do evento em formato JSON
                .build();

        // Criar o pedido de envio de eventos
        PutEventsRequest request = PutEventsRequest.builder()
                .entries(eventEntry)
                .build();

        // Enviar o evento
        PutEventsResponse response = eventBridgeClient.putEvents(request);

        // Verificar a resposta
        if (response.failedEntryCount() == 0) {
            System.out.println("Evento enviado com sucesso!");
        } else {
            System.out.println("Falha ao enviar o evento. Número de entradas com falha: " + response.failedEntryCount());
        }

        // Fechar o cliente do EventBridge
        eventBridgeClient.close();
    }
}
