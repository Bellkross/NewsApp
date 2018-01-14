package ua.bellkross.android.newsapp;


import android.graphics.drawable.Drawable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final String API_KEY = "57a35d7d-c2ae-4751-b3b3-3ba09c32f21d";

    private QueryUtils() {
    }

    public static Drawable getDrawableFromUrl(String url){
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    public static ArrayList<News> fetchNewsData(String requestUrl){
        URL url = stringToUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e){
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        ArrayList<News> news = fromJsonToNews(jsonResponse);

        return news;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.addRequestProperty("api_key", API_KEY);
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = streamToString(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static ArrayList<News> fromJsonToNews(String newsJson) {
        if (newsJson.isEmpty())
            return null;

        ArrayList<News> news = new ArrayList<>();

        try {
            JSONObject jsonResponse = new JSONObject(newsJson);
            jsonResponse = jsonResponse.getJSONObject("response");
            JSONArray jsonNewsArray = jsonResponse.getJSONArray("results");
            JSONObject newsObject;

            String title, sectionName, thumbnailUrl, newsUrl, date, author;
            Drawable thumbnail;

            for (int i = 0; i < jsonNewsArray.length(); ++i) {
                author = "";
                newsObject = jsonNewsArray.getJSONObject(i);

                if (newsObject.isNull("webPublicationDate"))
                    continue;

                title = newsObject.getString("webTitle");
                sectionName = newsObject.getString("sectionName");
                date = newsObject.getString("webPublicationDate");
                newsUrl = newsObject.getString("webUrl");

                newsObject = newsObject.getJSONObject("fields");
                thumbnailUrl = newsObject.getString("thumbnail");
                thumbnail = getDrawableFromUrl(thumbnailUrl);

                newsObject = jsonNewsArray.getJSONObject(i);

                if (newsObject.isNull("tags")) {

                    news.add(new News(title, sectionName, thumbnail, newsUrl, date));

                } else {

                    jsonNewsArray = newsObject.getJSONArray("tags");

                    for (int j = 0; j < jsonNewsArray.length(); ) {
                        newsObject = jsonNewsArray.getJSONObject(j);
                        if (!newsObject.isNull("firstName")) {
                            author += newsObject.getString("firstName");
                            if (!newsObject.isNull("lastName")) {
                                author += (author.equals("") ? "" : " ") + newsObject.getString("lastName");
                            }
                        } else if (!newsObject.isNull("lastName")) {
                            author += newsObject.getString("lastName");
                        }

                        if (++j < jsonNewsArray.length())
                            author += ',';
                    }
                    news.add(new News(title, sectionName, thumbnail, newsUrl, date, author));
                }
                jsonNewsArray = jsonResponse.getJSONArray("results");
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the news JSON results", e);
            return new ArrayList<>();
        }

        return news;
    }

    private static String streamToString(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL stringToUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }
}
