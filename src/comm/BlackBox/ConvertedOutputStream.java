package comm.BlackBox;


import java.io.OutputStream;
import java.io.IOException;

class ConvertedOutputStream extends OutputStream {
   
    OutputStream outstream;
 
    ConvertedOutputStream(OutputStream outstream) {
    this.outstream = outstream;
    }
 
    public void flush() throws IOException {
    outstream.flush();
    }

    private int prev = 0;
 
    public void write(int b) throws IOException {
    if (b == '\n') {
        if (prev != '\r')
        outstream.write('\r');
    } else if (b == '\r') {
        if (prev != '\n')
        outstream.write('\n');
    }
    prev = b;
    outstream.write(b);
    }
}

