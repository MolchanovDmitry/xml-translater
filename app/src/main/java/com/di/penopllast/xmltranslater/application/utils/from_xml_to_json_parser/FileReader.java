package com.di.penopllast.xmltranslater.application.utils.from_xml_to_json_parser;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import androidx.annotation.NonNull;

/**
 * Created by arnaud on 03/12/2016.
 */

class FileReader {

    public static String readFileFromAsset(@NonNull Context context, @NonNull String fileName) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            String result = readFileFromInputStream(inputStream);
            inputStream.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();    // TODO
        }
        return null;
    }

    public static String readFileFromInputStream(@NonNull InputStream inputStream) {

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder result = new StringBuilder();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (IOException exception) {
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e2) {
            }
            try {
                inputStreamReader.close();
            } catch (IOException e2) {
            }
        }
        return null;
    }
}