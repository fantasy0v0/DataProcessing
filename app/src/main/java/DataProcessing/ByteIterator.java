package DataProcessing;

import java.io.BufferedInputStream;
import java.io.IOException;

public class ByteIterator {

  private final BufferedInputStream bis;

  private byte current;

  public ByteIterator(BufferedInputStream bis) throws IOException {
    this.bis = bis;
    current = (byte)bis.read();
  }

  public boolean hasNext() {
    return current != -1;
  }

  public byte next() throws IOException {
    if (hasNext()) {
      byte temp = current;
      current = (byte)bis.read();
      return temp;
    } else {
      throw new RuntimeException("错误的分支");
    }
  }

  public byte peek() {
    return current;
  }
}
