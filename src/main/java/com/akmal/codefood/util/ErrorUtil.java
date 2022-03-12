package com.akmal.codefood.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

public class ErrorUtil {
    public static String getExceptionStacktrace(Exception e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(baos);
        e.printStackTrace(pw);
        String stacktrace = baos.toString();
        pw.close();

        return baos.toString();
    }
}
