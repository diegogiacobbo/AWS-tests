import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class EventBridgeExample {

    public static void main(String[] args) {
        String eventBridgeUrl = "https://eventbridge.us-east-1.amazonaws.com"; // URL do EventBridge na região desejada
        String eventBusName = "seu-event-bus"; // Nome do barramento de eventos
        String eventDetail = "{\"key\": \"value\"}"; // Detalhes do evento em formato JSON

        try {
            // Configuração da conexão HTTP
            URL url = new URL(eventBridgeUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-amz-json-1.1");
            connection.setRequestProperty("Authorization", "Bearer your-access-token");

            // Construção do corpo da solicitação
            String requestBody = String.format("{\"Detail\": %s, \"EventBusName\": \"%s\", \"Source\": \"your-source\"}",
                    eventDetail, eventBusName);
            byte[] postData = requestBody.getBytes(StandardCharsets.UTF_8);

            // Envio da solicitação
            connection.setDoOutput(true);
            try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                outputStream.write(postData);
            }

            // Leitura da resposta
            int responseCode = connection.getResponseCode();
            String responseMessage = connection.getResponseMessage();
            System.out.println("Response Code: " + responseCode);
            System.out.println("Response Message: " + responseMessage);

            // Fechamento da conexão
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
