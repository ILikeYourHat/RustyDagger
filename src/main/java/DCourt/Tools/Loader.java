package DCourt.Tools;

import DCourt.Items.Item;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/* loaded from: DCourt.jar:DCourt/Tools/Loader.class */
public class Loader {
  String text = new String();
  static final String EXECFILE = "DCcgi17.exe";
  public static final String FINDHERO = "dbFind";
  public static final String SAVEHERO = "dbSaveIt";
  public static final String READHERO = "dbLoad";
  public static final String READRANK = "dbRank";
  public static final String SAVESCORE = "dbScore";
  public static final String SENDMAIL = "dbMail";
  public static final String TAKEMAIL = "dbTake";
  public static final String LISTMAIL = "dbList";
  public static final String MESSAGE = "dbMessage";
  public static final String PEEKCLAN = "dbPeekClan";
  public static final String MAKECLAN = "dbMakeClan";
  public static final String KILLCLAN = "dbKillClan";

  public static Buffer cgiBuffer(String action, String data) {
    return new Buffer(cgi(action, data));
  }

  public static Item cgiItem(String action, String data) {
    return Item.factory(cgi(action, data));
  }

  public static String cgi(String action, String data) {
    System.out.println(action + " : " + data);
    return "";
    /*
    try {
      return operate(
              new URL(
                  String.valueOf(
                      String.valueOf(
                          new StringBuffer(String.valueOf(String.valueOf(Tools.getCgibin())))
                              .append("/")
                              .append(EXECFILE)
                              .append("?")
                              .append("cfg=")
                              .append(Tools.getConfig())
                              .append("&act=")
                              .append(action)))),
              data)
          .trim();
    } catch (MalformedURLException ex) {
      System.err.println(
          String.valueOf(
              String.valueOf(new StringBuffer("Loader Exception: [").append(ex).append("]"))));
      return "";
    }
    */
  }

  public String getText() {
    return this.text;
  }

  public boolean isError() {
    return this.text.startsWith("Error:");
  }

  static String operate(URL path, String send) {
    try {
      URLConnection con = path.openConnection();
      con.setDoInput(true);
      con.setUseCaches(false);
      con.setRequestProperty("content-type", "text/plain");
      con.setDoOutput(true);
      byte[] buf = getBytes(send);
      con.setRequestProperty(
          "content-length", "".concat(String.valueOf(String.valueOf(buf.length))));
      try {
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.write(buf);
        out.flush();
        out.close();
        int size = con.getContentLength();
        if (size < 0) {
          return "";
        }
        try {
          DataInputStream in = new DataInputStream(con.getInputStream());
          return Cryptography.decrypt(size >= 0 ? newLoad(in, size) : oldLoad(in));
        } catch (Exception ex) {
          System.err.println(
              String.valueOf(
                  String.valueOf(new StringBuffer("Loader Exception [").append(ex).append("]"))));
          ex.printStackTrace();
          return "Loader.read() Exception: ".concat(String.valueOf(String.valueOf(ex)));
        }
      } catch (Exception ex2) {
        System.err.println(
            String.valueOf(
                String.valueOf(new StringBuffer("Loader Exception [").append(ex2).append("]"))));
        ex2.printStackTrace();
        return "Loader.send() Exception: ".concat(String.valueOf(String.valueOf(ex2)));
      }
    } catch (Exception ex3) {
      System.err.println(
          String.valueOf(
              String.valueOf(new StringBuffer("Loader Exception [").append(ex3).append("]"))));
      ex3.printStackTrace();
      return "Loader.open() Exception: ".concat(String.valueOf(String.valueOf(ex3)));
    }
  }

  static byte[] getBytes(String msg) {
    if (msg == null) {
      return new byte[0];
    }
    String msg2 = Cryptography.encrypt(msg);
    return msg2.getBytes();
  }

  static String newLoad(DataInputStream in, int size) throws IOException {
    byte[] buf = new byte[size];
    in.readFully(buf);
    in.close();
    return new String(buf);
  }

  static String oldLoad(DataInputStream in) throws IOException {
    int cut;
    String msg = "";
    while (true) {
      String line = in.readLine();
      if (line == null) {
        break;
      }
      msg =
          String.valueOf(String.valueOf(msg))
              .concat(
                  String.valueOf(
                      String.valueOf(String.valueOf(String.valueOf(line)).concat("\n"))));
    }
    return msg;
  }
}
