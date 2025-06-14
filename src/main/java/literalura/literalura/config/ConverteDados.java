package literalura.literalura.config;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import literalura.literalura.config.iConfig.IConverteDados;

public class ConverteDados implements IConverteDados {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T converterDadosJsonAJava(String json, Class<T> classe) {
        try {
            return objectMapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
