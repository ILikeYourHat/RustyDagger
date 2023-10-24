package DCourt.Tools;

import org.jetbrains.annotations.NotNull;

public class Cryptography {

  @NotNull
  public static String encrypt(@NotNull String from) {
    String result = "";
    int size = from.length();
    int dx = 0;
    for (int ix = 0; ix < size; ix++) {
      int temp = (from.charAt(ix) + 128) & 127;
      if (temp < 32) {
        result =
            String.valueOf(String.valueOf(result))
                .concat(String.valueOf(String.valueOf((char) temp)));
      } else {
        result =
            String.valueOf(String.valueOf(result))
                .concat(
                    String.valueOf(
                        String.valueOf((char) ((((temp - 32) + ((size + dx) % 96)) % 96) + 32))));
        dx++;
      }
    }
    return result;
  }

  @NotNull
  public static String decrypt(@NotNull String from) {
    String result = "";
    int size = from.length();
    int dx = 0;
    for (int ix = 0; ix < size; ix++) {
      int temp = (from.charAt(ix) + 128) & 127;
      if (temp < 32) {
        result =
            String.valueOf(String.valueOf(result))
                .concat(String.valueOf(String.valueOf((char) temp)));
      } else {
        result =
            String.valueOf(String.valueOf(result))
                .concat(
                    String.valueOf(
                        String.valueOf(
                            (char) (((((temp - 32) + 96) - ((size + dx) % 96)) % 96) + 32))));
        dx++;
      }
    }
    return result;
  }
}
