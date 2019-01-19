package com.di.penopllast.xmltranslater.application.utils.from_xml_to_json_parser;

/*
    Copyright 2016 Arnaud Guyon

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Converts XML to JSON
 */

public class XmlToJson {

    private static final String TAG = "XmlToJson";
    private static final String DEFAULT_CONTENT_NAME = "content";
    private static final String DEFAULT_ENCODING = "utf-8";
    private static final String DEFAULT_INDENTATION = "   ";
    private final String mIndentationPattern = DEFAULT_INDENTATION;

    // default values when a Tag is empty
    private static final String DEFAULT_EMPTY_STRING = "";
    private static final int DEFAULT_EMPTY_INTEGER = 0;
    private static final long DEFAULT_EMPTY_LONG = 0;
    private static final double DEFAULT_EMPTY_DOUBLE = 0;
    private static final boolean DEFAULT_EMPTY_BOOLEAN = false;

    /**
     * Builder class to create a XmlToJson object
     */
    public static class Builder {

        private StringReader mStringSource;
        private InputStream mInputStreamSource;
        private String mInputEncoding = DEFAULT_ENCODING;
        private final HashSet<String> mForceListPaths = new HashSet<>();
        private final HashMap<String, String> mAttributeNameReplacements = new HashMap<>();
        private final HashMap<String, String> mContentNameReplacements = new HashMap<>();
        private final HashMap<String, Class> mForceClassForPath = new HashMap<>();    // Integer, Long, Double, Boolean
        private final HashSet<String> mSkippedAttributes = new HashSet<>();
        private final HashSet<String> mSkippedTags = new HashSet<>();

        /**
         * Constructor
         *
         * @param xmlSource XML source
         */
        public Builder(@NonNull String xmlSource) {
            mStringSource = new StringReader(xmlSource);
        }

        /**
         * Constructor
         *
         * @param inputStreamSource XML source
         * @param inputEncoding     XML encoding format, can be null (uses UTF-8 if null).
         */
        public Builder(@NonNull InputStream inputStreamSource, @Nullable String inputEncoding) {
            mInputStreamSource = inputStreamSource;
            mInputEncoding = (inputEncoding != null) ? inputEncoding : DEFAULT_ENCODING;
        }

        /**
         * Creates the XmlToJson object
         *
         * @return a XmlToJson instance
         */
        public XmlToJson build() {
            return new XmlToJson(this);
        }
    }

    private final StringReader mStringSource;
    private final InputStream mInputStreamSource;
    private final String mInputEncoding;
    private final HashSet<String> mForceListPaths;
    private final HashMap<String, String> mAttributeNameReplacements;
    private final HashMap<String, String> mContentNameReplacements;
    private final HashMap<String, Class> mForceClassForPath;
    private final HashSet<String> mSkippedAttributes;
    private final HashSet<String> mSkippedTags;
    private final JSONObject mJsonObject; // Used for caching the result

    private XmlToJson(Builder builder) {
        mStringSource = builder.mStringSource;
        mInputStreamSource = builder.mInputStreamSource;
        mInputEncoding = builder.mInputEncoding;
        mForceListPaths = builder.mForceListPaths;
        mAttributeNameReplacements = builder.mAttributeNameReplacements;
        mContentNameReplacements = builder.mContentNameReplacements;
        mForceClassForPath = builder.mForceClassForPath;
        mSkippedAttributes = builder.mSkippedAttributes;
        mSkippedTags = builder.mSkippedTags;

        mJsonObject = convertToJSONObject(); // Build now so that the InputStream can be closed just after
    }

    /**
     * @return the JSONObject built from the XML
     */
    public
    @Nullable
    JSONObject toJson() {
        return mJsonObject;
    }

    private
    @Nullable
    JSONObject convertToJSONObject() {
        try {
            Tag parentTag = new Tag("", "xml");

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);   // tags with namespace are taken as-is ("namespace:tagname")
            XmlPullParser xpp = factory.newPullParser();

            setInput(xpp);

            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.START_DOCUMENT) {
                eventType = xpp.next();
            }
            readTags(parentTag, xpp);

            unsetInput();

            return convertTagToJson(parentTag);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setInput(XmlPullParser xpp) {
        if (mStringSource != null) {
            try {
                xpp.setInput(mStringSource);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        } else {
            try {
                xpp.setInput(mInputStreamSource, mInputEncoding);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        }
    }

    private void unsetInput() {
        if (mStringSource != null) {
            mStringSource.close();
        }
        // else the InputStream has been given by the user, it is not our role to close it
    }

    private void readTags(Tag parent, XmlPullParser xpp) {
        try {
            int eventType;
            do {
                eventType = xpp.next();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String tagName = xpp.getName();
                        String path = parent.getPath() + "/" + tagName;

                        boolean skipTag = mSkippedTags.contains(path);

                        Tag child = new Tag(path, tagName);
                        if (!skipTag) {
                            parent.addChild(child);
                        }

                        // Attributes are taken into account as key/values in the child
                        int attrCount = xpp.getAttributeCount();
                        for (int i = 0; i < attrCount; ++i) {
                            String attrName = xpp.getAttributeName(i);
                            String attrValue = xpp.getAttributeValue(i);
                            String attrPath = parent.getPath() + "/" + child.getName() + "/" + attrName;

                            // Skip Attributes
                            if (mSkippedAttributes.contains(attrPath)) {
                                continue;
                            }

                            attrName = getAttributeNameReplacement(attrPath, attrName);
                            Tag attribute = new Tag(attrPath, attrName);
                            attribute.setContent(attrValue);
                            child.addChild(attribute);
                        }

                        readTags(child, xpp);
                        break;
                    case XmlPullParser.TEXT:
                        String text = xpp.getText();
                        parent.setContent(text);
                        break;
                    case XmlPullParser.END_TAG:
                        return;
                    case XmlPullParser.END_DOCUMENT:
                        return;
                    default:
                        Log.i(TAG, "unknown xml eventType " + eventType);
                        break;
                }
            } while (true);
        } catch (XmlPullParserException | IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private JSONObject convertTagToJson(Tag tag) {
        JSONObject json = new JSONObject();

        // Content is injected as a key/value
        if (tag.getContent() != null) {
            String path = tag.getPath();
            String name = getContentNameReplacement(path, DEFAULT_CONTENT_NAME);
            putContent(path, json, name, tag.getContent());
        }

        try {

            HashMap<String, ArrayList<Tag>> groups = tag.getGroupedElements(); // groups by tag names so that we can detect lists or single elements
            for (ArrayList<Tag> group : groups.values()) {

                if (group.size() == 1) {    // element, or list of 1
                    Tag child = group.get(0);
                    if (isForcedList(child)) {  // list of 1
                        JSONArray list = new JSONArray();
                        list.put(convertTagToJson(child));
                        String childrenNames = child.getName();
                        json.put(childrenNames, list);
                    } else {    // stand alone element
                        if (child.hasChildren()) {
                            JSONObject jsonChild = convertTagToJson(child);
                            json.put(child.getName(), jsonChild);
                        } else {
                            String path = child.getPath();
                            putContent(path, json, child.getName(), child.getContent());
                        }
                    }
                } else {    // list
                    JSONArray list = new JSONArray();
                    for (Tag child : group) {
                        list.put(convertTagToJson(child));
                    }
                    String childrenNames = group.get(0).getName();
                    json.put(childrenNames, list);
                }
            }
            return json;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void putContent(String path, JSONObject json, String tag, String content) {
        try {
            // checks if the user wants to force a class (Int, Double... for a given path)
            Class forcedClass = mForceClassForPath.get(path);
            if (forcedClass == null) {  // default behaviour, put it as a StringRow
                if (content == null) {
                    content = DEFAULT_EMPTY_STRING;
                }
                json.put(tag, content);
            } else {
                if (forcedClass == Integer.class) {
                    try {
                        Integer number = Integer.parseInt(content);
                        json.put(tag, number);
                    } catch (NumberFormatException exception) {
                        json.put(tag, DEFAULT_EMPTY_INTEGER);
                    }
                } else if (forcedClass == Long.class) {
                    try {
                        Long number = Long.parseLong(content);
                        json.put(tag, number);
                    } catch (NumberFormatException exception) {
                        json.put(tag, DEFAULT_EMPTY_LONG);
                    }
                } else if (forcedClass == Double.class) {
                    try {
                        Double number = Double.parseDouble(content);
                        json.put(tag, number);
                    } catch (NumberFormatException exception) {
                        json.put(tag, DEFAULT_EMPTY_DOUBLE);
                    }
                } else if (forcedClass == Boolean.class) {
                    if (content == null) {
                        json.put(tag, DEFAULT_EMPTY_BOOLEAN);
                    } else if (content.equalsIgnoreCase("true")) {
                        json.put(tag, true);
                    } else if (content.equalsIgnoreCase("false")) {
                        json.put(tag, false);
                    } else {
                        json.put(tag, DEFAULT_EMPTY_BOOLEAN);
                    }
                }
            }

        } catch (JSONException exception) {
            // keep continue in case of error
        }
    }

    private boolean isForcedList(Tag tag) {
        String path = tag.getPath();
        return mForceListPaths.contains(path);
    }

    private String getAttributeNameReplacement(String path, String defaultValue) {
        String result = mAttributeNameReplacements.get(path);
        if (result != null) {
            return result;
        }
        return defaultValue;
    }

    private String getContentNameReplacement(String path, String defaultValue) {
        String result = mContentNameReplacements.get(path);
        if (result != null) {
            return result;
        }
        return defaultValue;
    }

    @Override
    public String toString() {
        if (mJsonObject != null) {
            return mJsonObject.toString();
        }
        return null;
    }

    private void format(JSONObject jsonObject, StringBuilder builder, String indent) {
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            builder.append(indent);
            builder.append(mIndentationPattern);
            builder.append("\"");
            builder.append(key);
            builder.append("\": ");
            Object value = jsonObject.opt(key);
            if (value instanceof JSONObject) {
                JSONObject child = (JSONObject) value;
                builder.append(indent);
                builder.append("{\n");
                format(child, builder, indent + mIndentationPattern);
                builder.append(indent);
                builder.append(mIndentationPattern);
                builder.append("}");
            } else if (value instanceof JSONArray) {
                JSONArray array = (JSONArray) value;
                formatArray(array, builder, indent + mIndentationPattern);
            } else {
                formatValue(value, builder);
            }
            if (keys.hasNext()) {
                builder.append(",\n");
            } else {
                builder.append("\n");
            }
        }
    }

    private void formatArray(JSONArray array, StringBuilder builder, String indent) {
        builder.append("[\n");

        for (int i = 0; i < array.length(); ++i) {
            Object element = array.opt(i);
            if (element instanceof JSONObject) {
                JSONObject child = (JSONObject) element;
                builder.append(indent);
                builder.append(mIndentationPattern);
                builder.append("{\n");
                format(child, builder, indent + mIndentationPattern);
                builder.append(indent);
                builder.append(mIndentationPattern);
                builder.append("}");
            } else if (element instanceof JSONArray) {
                JSONArray child = (JSONArray) element;
                formatArray(child, builder, indent + mIndentationPattern);
            } else {
                formatValue(element, builder);
            }
            if (i < array.length() - 1) {
                builder.append(",");
            }
            builder.append("\n");
        }
        builder.append(indent);
        builder.append("]");
    }

    private void formatValue(Object value, StringBuilder builder) {
        if (value instanceof String) {
            String string = (String) value;

            // Escape special characters
            string = string.replaceAll("\\\\", "\\\\\\\\");                     // escape backslash
            string = string.replaceAll("\"", Matcher.quoteReplacement("\\\"")); // escape double quotes
            string = string.replaceAll("/", "\\\\/");                           // escape slash
            string = string.replaceAll("\n", "\\\\n").replaceAll("\t", "\\\\t");  // escape \n and \t
            string = string.replaceAll("\r", "\\\\r");  // escape \r

            builder.append("\"");
            builder.append(string);
            builder.append("\"");
        } else if (value instanceof Long) {
            Long longValue = (Long) value;
            builder.append(longValue);
        } else if (value instanceof Integer) {
            Integer intValue = (Integer) value;
            builder.append(intValue);
        } else if (value instanceof Boolean) {
            Boolean bool = (Boolean) value;
            builder.append(bool);
        } else if (value instanceof Double) {
            Double db = (Double) value;
            builder.append(db);
        } else {
            builder.append(value.toString());
        }
    }

}
