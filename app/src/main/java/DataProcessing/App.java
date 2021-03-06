/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package DataProcessing;

import DataProcessing.token.Token;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class App {

  public static void main(String[] args) throws IOException {
    new App().run(args);
  }

  private App() {
  }

  private ByteIterator iterator;

  public void run(String[] args) throws IOException {
    if (args.length != 1) {
      System.err.println("请提供文件地址");
      return;
    }
    File file = new File(args[0]);
    if (!file.exists()) {
      System.err.println("提供的文件地址" + file.getAbsolutePath() + "不存在");
      return;
    }
    FileInputStream fis = new FileInputStream(file);
    // 5M
    final int bufferSize = 5242880;
    BufferedInputStream bis = new BufferedInputStream(fis, bufferSize);
    this.iterator = new ByteIterator(bis);
    List<Token> tokens = new ArrayList<>();
    while (iterator.hasNext()) {
      byte b = iterator.next();
      if ('\n' == b) {
        tokens.add(Token.NewlineToken.Instance);
      } else if ('"' == b) {
        tokens.add(readString(null));
      } else if (b >= 33 && b <= 126) {
        tokens.add(readString(b));
      } else {
        continue;
      }
    }
    for (Token token : tokens) {
      if (token instanceof Token.NewlineToken) {
        System.out.println();
      } else {
        System.out.print(token.toString());
        System.out.print("__");
      }
    }
  }

  private Token.StringToken readString(Byte b) throws IOException {
    boolean doubleQuotationMark = true;
    StringBuilder buff = new StringBuilder();
    if (null != b) {
      buff.append((char)b.byteValue());
      doubleQuotationMark = false;
    }
    while (iterator.hasNext()) {
      b = iterator.peek();
      if ('"' == b) {
        iterator.next();
        return new Token.StringToken(buff.toString());
      } else if (b >= 33 && b <= 126) {
        buff.append((char)iterator.next());
      } else {
        if (doubleQuotationMark) {
          buff.append((char)iterator.next());
        } else {
          return new Token.StringToken(buff.toString());
        }
      }
    }
    return new Token.StringToken(buff.toString());
  }
}
