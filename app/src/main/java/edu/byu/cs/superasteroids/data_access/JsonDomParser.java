package edu.byu.cs.superasteroids.data_access;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by athom909 on 2/13/16.
 */
public class JsonDomParser {

    public static JSONObject run(Reader reader) throws Exception {
        return new JSONObject(makeString(reader));

    }

//    public static JSONArray getObjects(JSONObject rootObj) throws Exception {
//        JSONObject asteroidsGameObj = rootObj.getJSONObject("asteroidsGame");
//        JSONArray cdArr = asteroidsGameObj.getJSONArray("objects");
//        return cdArr;
//    }


    private static String makeString(Reader reader) throws IOException {

        StringBuilder sb = new StringBuilder();
        char[] buf = new char[512];

        int n = 0;
        while ((n = reader.read(buf)) > 0) {
            sb.append(buf, 0, n);
        }

        return sb.toString();
    }
}
