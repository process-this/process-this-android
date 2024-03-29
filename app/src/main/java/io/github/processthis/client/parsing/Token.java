/*  Copyright [2019] [Asher Bearce, Jeffery Franken, Matthew Jones, Jennifer Nevares-Diaz]
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
   limitations under the License.
*/

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

  public enum TokenType {
    NUMBER,
    KEYWORD,
    COMMENT,
    STRING
  }
}
