package DataProcessing.token;

public interface Token {

  class NewlineToken implements Token {

    public static final NewlineToken Instance = new NewlineToken();

    private NewlineToken() {

    }

  }

  class StringToken implements Token {

    private final String value;

    public StringToken(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }

}
