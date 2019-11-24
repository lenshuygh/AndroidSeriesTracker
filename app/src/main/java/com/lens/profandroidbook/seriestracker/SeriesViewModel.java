package com.lens.profandroidbook.seriestracker;

import android.app.Application;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private int seriesId = 0;
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
                    String seriesFeedBaseUrl = getApplication().getString(R.string.base_url);
                    String[] seriesIdList = getApplication().getResources().getStringArray(R.array.series_url_array);
                    URLConnection urlConnection;
                    for (String seriesId : seriesIdList) {
                        String seriesFeedUrl = seriesFeedBaseUrl + "&" + seriesId;
                        url = new URL(seriesFeedUrl);

                        urlConnection = url.openConnection();

                        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
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

    private Series getSeriesFromInputStream(InputStream inputStream) {
        return parseJsonFromInputStream(inputStream);

    }

    private Series parseJsonFromInputStream(InputStream inputStream) {
        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            Series newSeries = null;
            String title = null;
            String startDate = null;


            //root node
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                if (name.equals("Title")) {
                    title = jsonReader.nextString();
                    Log.i(TAG, "parseJsonFromInputStream: title received -> " + title);
                } else if (name.equals("Released")) {
                    startDate = jsonReader.nextString();
                } else {
                    jsonReader.skipValue();
                }
            }
            LocalDate localDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd MMM yyyy"));
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            newSeries = new Series(seriesId++, title, localDate, dayOfWeek);
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
