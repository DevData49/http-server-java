package devdata.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

public class Compress {
    public static byte[] compress(String str){
        if(str == null || str.isEmpty()){
            return new byte[0];
        }
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(bos)){
            gzip.write(str.getBytes(StandardCharsets.UTF_8));
            gzip.finish();
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
