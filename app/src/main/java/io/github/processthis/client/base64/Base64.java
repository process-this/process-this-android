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

package io.github.processthis.client.base64;

public class Base64 {

  private static String base64chars =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

  private Base64() {
  }

  public static String encode(String toEncode) {
    String result = "";
    String padding = "";
    int padCount = toEncode.length() % 3;

    if (padCount > 0) {
      for (int i = 0; i < 3; i++) {
        padding += "=";
        toEncode += "\0";
      }
    }

    for (int i = 0; i < toEncode.length(); i += 3) {
      if (i > 0 && (i / 3 * 4) % 76 == 0) {
        result += "\r\n";
      }

      int n = (toEncode.charAt(i) << 16) +
          (toEncode.charAt(i + 1) << 8) + (toEncode.charAt(i + 2));

      int n1 = (n >> 18) & 63;
      int n2 = (n >> 12) & 63;
      int n3 = (n >> 6) & 63;
      int n4 = (n & 63);

      result += "" + base64chars.charAt(n1) + base64chars.charAt(n2) + base64chars.charAt(n3) +
          base64chars.charAt(n4);
    }

    return result.substring(0, result.length() - padding.length()) + padding;
  }

  public static String decode(String toDecode){
    toDecode = toDecode.replaceAll("[^" + base64chars + "=]", "");

    String padding = (toDecode.charAt(toDecode.length() - 1) == '=' ?
        (toDecode.charAt(toDecode.length() - 2) == '=' ? "AA" : "A") : "");

    String result = "";
    toDecode = toDecode.substring(0, toDecode.length() - padding.length()) + padding;

    for (int i = 0; i < toDecode.length(); i += 4){
      int n = (base64chars.indexOf(toDecode.charAt(i)) << 18) +
          (base64chars.indexOf(toDecode.charAt(i + 1)) << 12) +
          (base64chars.indexOf(toDecode.charAt(i + 2)) << 6)  +
          (base64chars.indexOf(toDecode.charAt(i + 3)));
      result += "" + (char) ((n >>> 16) & 0xFF) + (char) ((n >>> 8) & 0xFF) + (char)(n & 0xFF);
    }

    return result.substring(0, result.length() - padding.length());
  }
}
