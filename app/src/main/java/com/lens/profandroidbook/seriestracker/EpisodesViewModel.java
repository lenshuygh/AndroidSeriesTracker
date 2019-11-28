package com.lens.profandroidbook.seriestracker;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.JsonReader;
import android.util.Log;

import androidx.annotation.NonNull;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class EpisodesViewModel extends AndroidViewModel {
    private MutableLiveData<List<Episode>> episodesListData;


    public EpisodesViewModel(@NonNull Application application) {
        super(application);
    }

    private String bearerToken = SeriesViewModel.getBearerToken();

    int seriesId = getSeriesIdFromSharedPReferences();

    private int getSeriesIdFromSharedPReferences() {
        Context appContext = getApplication().getApplicationContext();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext);
        return sharedPreferences.getInt("SeriesId", 0);
    }

    public LiveData<List<Episode>> getEpisodes() {
        if (episodesListData == null) {
            episodesListData = new MutableLiveData<>();
            loadEpisodes();
        }
        return episodesListData;
    }

    private void loadEpisodes() {
        Log.i(TAG, "EpisodesViewModel -> getSeriesIdFromSharedPReferences: seriesId = " + seriesId);
        new AsyncTask<Void, Void, List<Episode>>() {
            @Override
            protected List<Episode> doInBackground(Void... voids) {
                ArrayList<Episode> episodes = new ArrayList<>(0);
                String episodesFeedBaseUrl = getApplication().getString(R.string.base_url);

                String episodesFeedUrl = episodesFeedBaseUrl + seriesId + '/' + "episodes";

                URLConnection urlConnection;
                try {
                    URL url = new URL(episodesFeedUrl);
                    urlConnection = url.openConnection();
                    HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                    httpURLConnection.setRequestProperty("Authorization", "Bearer " + bearerToken);
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        episodes.addAll(addEpisodesToEpisodesArray(inputStream));
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return episodes;
            }

            @Override
            protected void onPostExecute(List<Episode> episodes) {
                episodesListData.setValue(episodes);
            }
        }.execute();
    }

    private ArrayList<Episode> addEpisodesToEpisodesArray(InputStream inputStream) {
        return getEpisodesAsArrayList(inputStream);
    }

    private ArrayList<Episode> getEpisodesAsArrayList(InputStream inputStream) {
        ArrayList<Episode> episodeList = new ArrayList<>(0);
        try {
            int id = 0;
            int season = 0;
            int episode = 0;
            String airedDate = null;
            JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            // root node
            jsonReader.beginObject();
            // links node
            jsonReader.nextName();
            // data node
            jsonReader.skipValue();
            jsonReader.nextName();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String name = jsonReader.nextName();
                    switch (name) {
                        case "id":
                            id = jsonReader.nextInt();
                            break;
                        case "airedSeason":
                            season = jsonReader.nextInt();
                            break;
                        case "airedEpisodeNumber":
                            episode = jsonReader.nextInt();
                            break;
                        case "firstAired":
                            airedDate = jsonReader.nextString();
                            break;
                        default:
                            jsonReader.skipValue();
                    }
                }
                LocalDate localDate;
                if ("".equals(airedDate)) {
                    localDate = null;
                } else {
                    localDate = LocalDate.parse(airedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
                Episode newEpisode = new Episode(id, season, episode, localDate, false, seriesId);
                episodeList.add(newEpisode);
                jsonReader.endObject();
            }
            jsonReader.endArray();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return episodeList;
    }
}
