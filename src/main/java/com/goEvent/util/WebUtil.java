package com.GoEvent.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebUtil {

    public static String responseForError(HttpServletResponse res, String message) throws IOException {
        res.getWriter().println(message);
        res.getWriter().close();
        return null;
    }

}
