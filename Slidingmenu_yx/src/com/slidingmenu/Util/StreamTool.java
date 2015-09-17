package com.slidingmenu.Util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ������������������������
 * @author android_ls
 */
public final class StreamTool {

    /**
     * ����������ȡ����
     * 
     * @param inStream
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static byte[] read(InputStream inStream) throws IOException {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }

}

