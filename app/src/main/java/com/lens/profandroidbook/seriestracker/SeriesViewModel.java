package com.lens.profandroidbook.seriestracker;

import android.app.Application;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class SeriesViewModel extends AndroidViewModel {
    private MutableLiveData<List<Series>> seriesListData;

    public SeriesViewModel(Application application) {
        super(application);
    }

    public LiveData<List<Series>> getSeries() {
        if (seriesListData == null) {
            seriesListData = new MutableLiveData<>();
            loadSeries();
        }
        return seriesListData;
    }

    private void loadSeries() {
        new AsyncTask<Void, Void, List<Series>>() {
            @Override
            protected List<Series> doInBackground(Void... voids) {
                ArrayList<Series> series = new ArrayList<>(0);
                URL url;

                try {
                    String apiKey = getApplication().getResources().getString(R.string.api_key);
                    String bearerToken = getBearerToken(apiKey);

                    String seriesFeedBaseUrl = getApplication().getString(R.string.base_url);
                    String[] seriesIdList = getApplication().getResources().getStringArray(R.array.series_url_array);
                    URLConnection urlConnection;
                    for (String seriesId : seriesIdList) {
                        String seriesFeedUrl = seriesFeedBaseUrl + seriesId;
                        url = new URL(seriesFeedUrl);

                        urlConnection = url.openConnection();

                        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

                        httpURLConnection.setRequestProperty("Authorization", "Bearer " + bearerToken);

                        int responseCode = httpURLConnection.getResponseCode();

                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            InputStream inputStream = httpURLConnection.getInputStream();

                            series.add(getSeriesFromInputStream(inputStream));
                        }
                        httpURLConnection.disconnect();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return series;
            }

            @Override
            protected void onPostExecute(List<Series> series) {
                seriesListData.setValue(series);
            }
        }.execute();
    }

    private String getBearerToken(String apiKey) {
        try {
            URL url = new URL(getApplication().getResources().getString(R.string.token_url));
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("apikey", apiKey);
            } catch (JSONException e) {
                Log.i(TAG, "getBearerToken: json-creation failed", e);
                e.printStackTrace();
            }


            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");

            httpURLConnection.setRequestProperty("Content-length", jsonObject.toString().getBytes().length + "");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(jsonObject.toString().getBytes("UTF-8"));
            outputStream.close();

            httpURLConnection.connect();


            int status = httpURLConnection.getResponseCode();
            String response = httpURLConnection.getResponseMessage();

            if (status == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                bufferedReader.close();
                JSONObject tokenJsonObject = new JSONObject(String.valueOf(sb));
                return tokenJsonObject.getString("token");
            }

        } catch (MalformedURLException e) {
            Log.e(TAG, "getBearerToken: MalformedURLException", e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "getBearerToken: IOException", e);
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e(TAG, "getBearerToken: JSON Response parse", e);
            e.printStackTrace();
        }
        return "";
    }

    private Series getSeriesFromInputStream(InputStream inputStream) {
        return parseJsonFromInputStream(inputStream);

    }

    private Series parseJsonFromInputStream(InputStream inputStream) {
        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            Series newSeries = null;
            int id = 0;
            String title = null;
            String startDate = null;


            //root node
            jsonReader.beginObject();
            //next node 'Data'
            jsonReader.nextName();
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                if (name.equals("id")) {
                    id = jsonReader.nextInt();
                    Log.i(TAG, "parseJsonFromInputStream: id received -> " + id);
                } else if (name.equals("seriesName")) {
                    title = jsonReader.nextString();
                    Log.i(TAG, "parseJsonFromInputStream: title received -> " + title);
                } else if (name.equals("firstAired")) {
                    startDate = jsonReader.nextString();
                } else {
                    jsonReader.skipValue();
                }
            }
            LocalDate localDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            newSeries = new Series(id, title, localDate, dayOfWeek);
            jsonReader.endObject();
            Log.i(TAG, "parseJsonFromInputStream: series created -> " + newSeries);
            return newSeries;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
