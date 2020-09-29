package company.util;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.zip.GZIPInputStream;

public class Base64Util {
    public static final String b64Str = "gHe5qGavRo_6Z74ST1Czx09m=FhwiVQfrB-ODPnsX2lu8Wbtp3KyEAMNLIcYjUkdJ";
    public static final String cryptb64Str = "VECywpPYd46Fh-8BDgo93ebz=MZs2kRqGTjJWLIxUtN1van0uQc7flr_iHm5KSXOA";

    public static String decrypToB64(String indata) {
        StringBuffer indataDecrypt = new StringBuffer();
        for (char c : indata.toCharArray()) {
            try {
                int idx = cryptb64Str.indexOf(c);
                char targetC = b64Str.toCharArray()[idx];
                indataDecrypt.append(targetC);
            } catch (Exception e) {
                indataDecrypt.append(c);
            }
        }
        return indataDecrypt.toString();
    }

    public static boolean isGzip(byte[] data) {
        int b0 = data[0];
        int b1 = data[1];

        int b = (b1 & 0xFF) << 8 | b0;
        return b == GZIPInputStream.GZIP_MAGIC;
    }


    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static String decode(String row) {
        String indata = row.replace("\n", "");
        byte[] rawResult = Base64.getDecoder().decode(Base64Util.decrypToB64(indata).getBytes());

        if (isGzip(rawResult)) {
            rawResult = Base64Util.uncompress(rawResult);
        }
        return new String(rawResult);
    }
}
