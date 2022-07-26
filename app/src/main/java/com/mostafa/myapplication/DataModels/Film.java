package com.mostafa.myapplication.DataModels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.mostafa.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(tableName = "Films")

public class Film {
    public static final String TITLE_KEY = "Title";
    public static final String YEAR_KEY = "Year";
    public static final String RATED_KEY = "Rated";
    public static final String RELEASE_KEY = "Released";
    public static final String RUNTIME_KEY = "Runtime";
    public static final String GENRE_KEY = "Genre";
    public static final String DIRECTOR_KEY = "Director";
    public static final String WRITER_KEY = "Writer";
    public static final String ACTORS_KEY = "Actors";
    public static final String PLOTS_KEY = "Plot";
    public static final String LANGUAGE_KEY = "Language";
    public static final String COUNTRY_KEY = "Country";
    public static final String AWARDS_KEY = "Awards";
    public static final String POSTER_KEY = "Poster";
    public static final String RATINGS_KEY = "Ratings";
    public static final String METASCORE_KEY = "Metascore";
    public static final String IMDBRATE_KEY = "imdbRating";
    public static final String IMDBVOTES_KEY = "imdbVotes";
    public static final String IMDBID_KEY = "imdbID";
    public static final String TYPE_KEY = "DVD";
    public static final String BOXOFFICE_KEY = "BoxOffice";
    public static final String PRODUCTION_KEY = "Production";
    public static final String WEBSITE_KEY = "Website";
    public static final String RESPONSE_KEY = "Response";
    public static final String DVD_KEY ="DVD" ;


    public Film(int index, JSONObject film) throws JSONException {
        this.index = index;
        title = film.getString(TITLE_KEY);
        year = film.getString(YEAR_KEY);
        rated = film.getString(RATED_KEY);
        releaseDate = film.getString(RELEASE_KEY);
        runTime = film.getString(RUNTIME_KEY);
        genre = film.getString(GENRE_KEY);
        director = film.getString(DIRECTOR_KEY);
        writer = film.getString(WRITER_KEY);
        actors = film.getString(ACTORS_KEY);
        plot = film.getString(PLOTS_KEY);
        language = film.getString(LANGUAGE_KEY);
        country = film.getString(COUNTRY_KEY);
        awards = film.getString(AWARDS_KEY);
        posterUrl = film.getString(POSTER_KEY);
        metaScore = film.getInt(METASCORE_KEY);
        imdbRatings = film.getDouble(IMDBRATE_KEY);
        imdbVotes = Long.parseLong(film.getString(IMDBVOTES_KEY).replace(",",""));
        imdbID = film.getString(IMDBID_KEY);
        type = film.getString(TYPE_KEY);
        dvd = film.getString(DVD_KEY);
        boxOffice = film.getString(BOXOFFICE_KEY);
        production = film.getString(PRODUCTION_KEY);
        website = film.getString(WEBSITE_KEY);
        response = film.getString(RESPONSE_KEY);
        JSONArray ratesJSON = film.getJSONArray(RATINGS_KEY);
        rates = new ArrayList<>();
        for (int i = 0; i < ratesJSON.length(); i++) {
            rates.add(new Rate(ratesJSON.getJSONObject(i).getString(Rate.SOURCE_KEY), ratesJSON.getJSONObject(i).getString(Rate.VALUE_KEY)));
        }
    }

    public Film(int index, @NonNull String title, @NonNull String year, @NonNull String posterUrl,@NonNull String imdbId) {
        this.index = index;
        this.title = title;
        this.year = year;
        this.posterUrl = posterUrl;
        this.imdbID = imdbId;
    }

    public Film(int index, @NonNull String title, @NonNull String year, String rated, String releaseDate, String runTime, String genre, String director, String writer, String actors, String plot, String language, String country, String awards, @NonNull String posterUrl, List<Rate> rates, int metaScore, double imdbRatings, long imdbVotes, @NonNull String imdbID, String type, String dvd, String boxOffice, String production, String website, String response) {
        this.index = index;
        this.title = title;
        this.year = year;
        this.rated = rated;
        this.releaseDate = releaseDate;
        this.runTime = runTime;
        this.genre = genre;
        this.director = director;
        this.writer = writer;
        this.actors = actors;
        this.plot = plot;
        this.language = language;
        this.country = country;
        this.awards = awards;
        this.posterUrl = posterUrl;
        this.rates = rates;
        this.metaScore = metaScore;
        this.imdbRatings = imdbRatings;
        this.imdbVotes = imdbVotes;
        this.imdbID = imdbID;
        this.type = type;
        this.dvd = dvd;
        this.boxOffice = boxOffice;
        this.production = production;
        this.website = website;
        this.response = response;
    }

    @PrimaryKey
    private int index;
    @NonNull
    private String title;
    @NonNull
    private String year;
    private String rated;
    private String releaseDate;
    private String runTime;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String plot;
    private String language;
    private String country;
    private String awards;
    @NonNull
    private String posterUrl;
    private List<Rate> rates;
    private int metaScore;
    private double imdbRatings;
    private long imdbVotes;
    @NonNull
    private String imdbID;
    private String type;
    private String dvd;
    private String boxOffice;
    private String production;
    private String website;
    private String response;

    public static String getTitleKey() {
        return TITLE_KEY;
    }

    public static String getYearKey() {
        return YEAR_KEY;
    }

    public static String getRatedKey() {
        return RATED_KEY;
    }

    public static String getReleaseKey() {
        return RELEASE_KEY;
    }

    public static String getRuntimeKey() {
        return RUNTIME_KEY;
    }

    public static String getGenreKey() {
        return GENRE_KEY;
    }

    public static String getDirectorKey() {
        return DIRECTOR_KEY;
    }

    public static String getWriterKey() {
        return WRITER_KEY;
    }

    public static String getActorsKey() {
        return ACTORS_KEY;
    }

    public static String getPlotsKey() {
        return PLOTS_KEY;
    }

    public static String getLanguageKey() {
        return LANGUAGE_KEY;
    }

    public static String getCountryKey() {
        return COUNTRY_KEY;
    }

    public static String getAwardsKey() {
        return AWARDS_KEY;
    }

    public static String getPosterKey() {
        return POSTER_KEY;
    }

    public static String getRatingsKey() {
        return RATINGS_KEY;
    }

    public static String getMetascoreKey() {
        return METASCORE_KEY;
    }

    public static String getImdbrateKey() {
        return IMDBRATE_KEY;
    }

    public static String getImdbvotesKey() {
        return IMDBVOTES_KEY;
    }

    public static String getImdbidKey() {
        return IMDBID_KEY;
    }

    public static String getTypeKey() {
        return TYPE_KEY;
    }

    public static String getBoxofficeKey() {
        return BOXOFFICE_KEY;
    }

    public static String getProductionKey() {
        return PRODUCTION_KEY;
    }

    public static String getWebsiteKey() {
        return WEBSITE_KEY;
    }

    public static String getResponseKey() {
        return RESPONSE_KEY;
    }

    @NonNull
    public String getWriter() {
        return writer;
    }

    public void setWriter(@NonNull String writer) {
        this.writer = writer;
    }

    @NonNull
    public int getMetaScore() {
        return metaScore;
    }

    public void setMetaScore(@NonNull int metaScore) {
        this.metaScore = metaScore;
    }

    @NonNull
    public double getImdbRatings() {
        return imdbRatings;
    }

    public void setImdbRatings(@NonNull double imdbRatings) {
        this.imdbRatings = imdbRatings;
    }

    @NonNull
    public long getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(@NonNull long imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    @NonNull
    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(@NonNull String imdbID) {
        this.imdbID = imdbID;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    @NonNull
    public String getDvd() {
        return dvd;
    }

    public void setDvd(@NonNull String dvd) {
        this.dvd = dvd;
    }

    @NonNull
    public String getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(@NonNull String boxOffice) {
        this.boxOffice = boxOffice;
    }

    @NonNull
    public String getProduction() {
        return production;
    }

    public void setProduction(@NonNull String production) {
        this.production = production;
    }

    @NonNull
    public String getWebsite() {
        return website;
    }

    public void setWebsite(@NonNull String website) {
        this.website = website;
    }

    @NonNull
    public String getResponse() {
        return response;
    }

    public void setResponse(@NonNull String response) {
        this.response = response;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getYear() {
        return year;
    }

    public void setYear(@NonNull String year) {
        this.year = year;
    }

    @NonNull
    public String getRated() {
        return rated;
    }

    public void setRated(@NonNull String rated) {
        this.rated = rated;
    }

    @NonNull
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(@NonNull String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @NonNull
    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(@NonNull String runTime) {
        this.runTime = runTime;
    }

    @NonNull
    public String getGenre() {
        return genre;
    }

    public void setGenre(@NonNull String genre) {
        this.genre = genre;
    }

    @NonNull
    public String getDirector() {
        return director;
    }

    public void setDirector(@NonNull String director) {
        this.director = director;
    }

    @NonNull
    public String getActors() {
        return actors;
    }

    public void setActors(@NonNull String actors) {
        this.actors = actors;
    }

    @NonNull
    public String getPlot() {
        return plot;
    }

    public void setPlot(@NonNull String plot) {
        this.plot = plot;
    }

    @NonNull
    public String getLanguage() {
        return language;
    }

    public void setLanguage(@NonNull String language) {
        this.language = language;
    }

    @NonNull
    public String getCountry() {
        return country;
    }

    public void setCountry(@NonNull String country) {
        this.country = country;
    }

    @NonNull
    public String getAwards() {
        return awards;
    }

    public void setAwards(@NonNull String awards) {
        this.awards = awards;
    }

    @NonNull
    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(@NonNull String posterUrl) {
        this.posterUrl = posterUrl;
    }

    @NonNull
    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(@NonNull List<Rate> rates) {
        this.rates = rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return imdbID.equals(film.imdbID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imdbID);
    }

    public List<Property> getProperties(Context context) {
        List<Property> properties = new ArrayList<>();
        properties.add(new Property(context.getResources().getString(R.string.Title),title));
        properties.add(new Property(context.getResources().getString(R.string.Year),year));
        properties.add(new Property(context.getResources().getString(R.string.Rated),rated));
        properties.add(new Property(context.getResources().getString(R.string.Realease_date),releaseDate));
        properties.add(new Property(context.getResources().getString(R.string.Run_time),runTime));
        properties.add(new Property(context.getResources().getString(R.string.Genre),genre));
        properties.add(new Property(context.getResources().getString(R.string.Directors),director));
        properties.add(new Property(context.getResources().getString(R.string.Writers),writer));
        properties.add(new Property(context.getResources().getString(R.string.Actors),actors));
        properties.add(new Property(context.getResources().getString(R.string.Plot),plot));
        properties.add(new Property(context.getResources().getString(R.string.Country),country));
        properties.add(new Property(context.getResources().getString(R.string.Awards),awards));
        properties.add(new Property(context.getResources().getString(R.string.Metascore),metaScore + ""));
        properties.add(new Property(context.getResources().getString(R.string.Imdb),imdbRatings + ""));
        properties.add(new Property(context.getResources().getString(R.string.imdb_votes),imdbVotes + ""));
        for (int i = 0; i < rates.size(); i++) {
            properties.add(new Property(rates.get(i).getSource(),rates.get(i).getValue()));
        }
        properties.add(new Property(context.getResources().getString(R.string.type),type));
        properties.add(new Property(context.getResources().getString(R.string.DVD_relaease_date),dvd));
        properties.add(new Property(context.getResources().getString(R.string.Box_office),boxOffice));
        properties.add(new Property(context.getResources().getString(R.string.Production),production));
        properties.add(new Property(context.getResources().getString(R.string.Website),website));

        return properties;
    }

    @Override
    public String toString() {
        return "Film{" +
                "index=" + index +
                ", title='" + title + '\'' +
                ", imdbID='" + imdbID + '\'' +
                '}';
    }
}
