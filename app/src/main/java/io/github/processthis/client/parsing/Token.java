package io.github.processthis.client.parsing;

public class Token {
  private final int start;
  private final int end;
  private final TokenType type;

  public int getStart() {
    return start;
  }

  public int getEnd() {
    return end;
  }

  public TokenType getType() {
    return type;
  }

  public Token(int start, int end, TokenType type) {
    this.start = start;
    this.end = end;
    this.type = type;
  }

  public enum TokenType{
    NUMBER,
    KEYWORD,
    COMMENT,
    STRING
  }
}
