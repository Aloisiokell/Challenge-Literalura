package literalura.literalura.config.iConfig;

public interface IConverteDados {

    <T> T converterDadosJsonAJava(String json , Class<T> clase);

}
