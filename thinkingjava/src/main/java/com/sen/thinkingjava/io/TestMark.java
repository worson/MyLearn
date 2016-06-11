package com.sen.thinkingjava.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;

/**
 * Created by secon on 2016/6/11.
 */
public class TestMark {

    public static void main(String[] args) {
        InputStream is = null;
        StringBufferInputStream sbi = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try{
            // open input stream test.txt for reading purpose.
//            is = new FileInputStream("c:/test.txt");
            sbi = new StringBufferInputStream("ABCDE");
            // create new input stream reader
            isr = new InputStreamReader(sbi);

            // create new buffered reader
            br = new BufferedReader(isr);

            // reads and prints BufferedReader
            System.out.println((char)br.read());
            System.out.println((char)br.read());

            // mark invoked at this position
            br.mark(1);
            System.out.println("mark() invoked");
            System.out.println((char)br.read());
            System.out.println((char)br.read());

            // reset() repositioned the stream to the mark
            br.reset();
            System.out.println("reset() invoked");
            System.out.println((char)br.read());
            System.out.println((char)br.read());

        } catch (Exception e) {

            // exception occurred.
            e.printStackTrace();
        }finally{

            // releases any system resources associated with the stream
            /*if(is!=null)
                is.close();*/
            try {
                if(sbi!=null)
                    sbi.close();
                if(isr!=null)
                    isr.close();
                if(br!=null)
                    br.close();
            }catch (Exception e){

            }

        }
    }
}
