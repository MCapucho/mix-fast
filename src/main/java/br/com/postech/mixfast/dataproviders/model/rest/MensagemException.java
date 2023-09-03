package br.com.postech.mixfast.dataproviders.model.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MensagemException {

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<String> causes;
}
