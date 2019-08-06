package io.github.processthis.client.parsing;

import io.github.processthis.client.parsing.Token.TokenType;
import java.util.LinkedList;

public class Tokenizer {

  private final char[] toTokenize;
  private int currentIndex;
  private static final String[] RESERVED_WORDS = new String[]{
      "abstract", "else", "instanceof", "super",
      "boolean", "enum", "int", "switch",
      "break", "export", "interface", "synchronized",
      "byte", "extends", "let", "this",
      "case", "false", "long", "throw",
      "catch", "final", "native", "throws",
      "char", "finally", "new", "transient",
      "class", "float", "null", "true",
      "const", "for", "package", "try",
      "continue", "function", "private", "typeof",
      "debugger", "goto", "protected", "var",
      "default", "if", "public", "void",
      "delete", "implements", "return", "volatile",
      "do", "import", "short", "while",
      "double", "in", "static", "with"
  };

  public Tokenizer(String toTokenize) {
    this.toTokenize = toTokenize.toCharArray();
  }

  private char nextChar() {
    return toTokenize[(currentIndex + 1 < toTokenize.length) ? ++currentIndex : currentIndex++];
  }

  private boolean hasNext() {
    return currentIndex < toTokenize.length;
  }

  private boolean isReservedWord(String word) {
    for (String reserved : RESERVED_WORDS) {
      if (word.equals(reserved)) {
        return true;
      }
    }

    return false;
  }

  public LinkedList<Token> Tokenize() {
    LinkedList<Token> result = new LinkedList<>();
    char currentChar = toTokenize[0];

    while (hasNext()) {
      switch (currentChar) {
        case ' ':
        case '\t':
        case '\n':
        case '\r': {
          currentChar = nextChar();
          continue;
        }
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9': {
          int index = currentIndex;

          while (hasNext() && (Character.isDigit(currentChar) || currentChar == 'e' ||
              currentChar == '.' || currentChar == '-')) {
            currentChar = nextChar();
          }

          result.addLast(new Token(index, currentIndex, TokenType.NUMBER));
          break;
        }
        case '/': {
          int index = currentIndex;
          currentChar = nextChar();

          if (!hasNext()) {
            break;
          }
          if (currentChar == '/') {
            while (hasNext() && currentChar != '\n') {
              currentChar = nextChar();
            }

            result.addLast(new Token(index, currentIndex, TokenType.COMMENT));
            if (hasNext()) {
              currentChar = nextChar();
            }

          } else if (currentChar == '*') {

            while (hasNext()) {
              currentChar = nextChar();

              if (hasNext() && currentChar == '*') {
                currentChar = nextChar();
                if (hasNext() && currentChar == '/') {
                  currentChar = nextChar();
                  break;
                }
              }
            }

            result.addLast(new Token(index, currentIndex, TokenType.COMMENT));
          }

          break;
        }
        case '\'':
        case '"': {
          char endChar = currentChar == '\'' ? '\'' : '"';
          int index = currentIndex;
          currentChar = nextChar();

          while (hasNext() && currentChar != endChar) {
            currentChar = nextChar();
          }

          if (hasNext()) {
            currentChar = nextChar();
          }

          result.addLast(new Token(index, currentIndex, TokenType.STRING));
        }
        default: {
          if (Character.isAlphabetic(currentChar)) {
            int index = currentIndex;

            while (hasNext() && Character.isAlphabetic(currentChar)) {
              currentChar = nextChar();
            }

            String word = new String(toTokenize).substring(index, currentIndex);
            if (isReservedWord(word)) {
              result.addLast(new Token(index, currentIndex, TokenType.KEYWORD));
            }
          } else {
            if (hasNext()) {
              currentChar = nextChar();
            }
          }
          break;
        }
      }
    }

    return result;
  }
}
