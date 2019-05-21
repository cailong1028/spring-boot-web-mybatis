package com.modules.prime.test;

import java.io.UnsupportedEncodingException;

public class BaseX {
    private static char[] baseChar = new char[]{'9', 'j', 'S', 'K', 'z', 'c', '+', '4', 'd', 'f', 'a', 'Z', 'h', '8', 'e', 'Y', 'V', 'X', '6', 'R', 'T', 'U', 'W', 'Q', 'l', 'n', 'i', 'b', '/', 'k', 'm', 'g', 'F', 'u', 'C', '3', 'D', 'E', 'G', 'A', '1', 'B', 'y', 'x', '7', '0', '2', 'P', 't', 'v', 'q', 'p', 'r', 'M', 'H', 'o', 'N', 'w', '5', 'J', 'L', 's', 'O', 'I'};
    private static byte[] position = new byte[128];

    static {
        char p;
        for(byte i = 0; i < baseChar.length; position[p] = i++) {
            p = baseChar[i];
        }

    }

    public BaseX() {
    }

    public static String encode(byte[] b) {
        int code = 0;
        StringBuffer sb = new StringBuffer((b.length - 1) / 3 << 6);

        for(int i = 0; i < b.length; ++i) {
            code |= b[i] << 16 - i % 3 * 8 & 255 << 16 - i % 3 * 8;
            if (i % 3 == 2 || i == b.length - 1) {
                sb.append(baseChar[(code & 16515072) >>> 18]);
                sb.append(baseChar[(code & 258048) >>> 12]);
                sb.append(baseChar[(code & 4032) >>> 6]);
                sb.append(baseChar[code & 63]);
                code = 0;
            }
        }

        if (b.length % 3 > 0) {
            sb.setCharAt(sb.length() - 1, '=');
        }

        if (b.length % 3 == 1) {
            sb.setCharAt(sb.length() - 2, '=');
        }

        return sb.toString();
    }

    public static String decode2str(String code) {
        return decode2str(code, "utf-8");
    }

    public static String decode2str(String code, String characterEncode) {
        String ret = null;
        try {
            ret = new String(decode(code), characterEncode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static byte[] decode(String code) {
        if (code != null && !code.isEmpty()) {
            int len = code.length();
            if (len % 4 != 0) {
                return code.getBytes();
            } else if (code.length() == 0) {
                return new byte[0];
            } else {
                int pad = 0;
                if (code.charAt(len - 1) == '=') {
                    ++pad;
                }

                if (code.charAt(len - 2) == '=') {
                    ++pad;
                }

                int retLen = len / 4 * 3 - pad;
                byte[] ret = new byte[retLen];

                for(int i = 0; i < len; i += 4) {
                    int j = i / 4 * 3;
                    char ch1 = code.charAt(i);
                    char ch2 = code.charAt(i + 1);
                    char ch3 = code.charAt(i + 2);
                    char ch4 = code.charAt(i + 3);
                    int tmp = position[ch1] << 18 | position[ch2] << 12 | position[ch3] << 6 | position[ch4];
                    ret[j] = (byte)((tmp & 16711680) >> 16);
                    if (i < len - 4) {
                        ret[j + 1] = (byte)((tmp & '\uff00') >> 8);
                        ret[j + 2] = (byte)(tmp & 255);
                    } else {
                        if (j + 1 < retLen) {
                            ret[j + 1] = (byte)((tmp & '\uff00') >> 8);
                        }

                        if (j + 2 < retLen) {
                            ret[j + 2] = (byte)(tmp & 255);
                        }
                    }
                }

                return ret;
            }
        } else {
            return new byte[0];
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String code = encode("cl".getBytes());
        System.out.println(code);
        System.out.println(new String(decode(code)));
    }
}
