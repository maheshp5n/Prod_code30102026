package com.psl.exicom.bt;

public class MiscellaneousUtils {
  public static String fillZero(String msg, int size) {
    StringBuilder builder = new StringBuilder();
    if (msg.length() >= size) {
      builder.append(msg);
      return builder.toString();
    } 
    int restCount = size - msg.length();
    for (int i = 0; i < restCount; i++)
      builder.append("0"); 
    builder.append(msg);
    return builder.toString();
  }
}
