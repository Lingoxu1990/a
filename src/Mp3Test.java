import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by surfacepc on 2016/2/29.
 */
public class Mp3Test {
    public static void main(String[] args) throws IOException, BitstreamException {

        String mp3 =  "C:\\Users\\surfacepc\\Desktop\\demo\\20150327-04.mp3";
        File file = new File(mp3);
        FileInputStream fis=new FileInputStream(file);
        int b=fis.available();
        Bitstream bt=new Bitstream(fis);
        Header h = bt.readFrame();
        float totalMs =  h.total_ms(b);
        System.out.println(totalMs);
    }
}
