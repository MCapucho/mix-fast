package br.com.postech.mixfast.entrypoints.docs;

import br.com.postech.mixfast.entrypoints.handler.ErrorResponse;
import br.com.postech.mixfast.entrypoints.http.WebhookHttp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Webhook")
public interface WebhookDocumentable {

    @Operation(summary = "Receber notificação do status do pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status do pagamento atualizado no pedido com sucesso",
                    content = { @Content(array = @ArraySchema(schema = @Schema(implementation = WebhookHttp.class)),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "Erro na comunicação com o banco de dados",
                    content = { @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json") })})
    ResponseEntity<?> receberNotificacaoPagamento(@Parameter WebhookHttp webhookHttp);
}
