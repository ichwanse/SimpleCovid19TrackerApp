package io.javaLearn.covidtracker.services;


import io.javaLearn.covidtracker.models.LocationStates;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {
    //corona virus target url
    private static String CORONA_VIRUS_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStates> allState = new ArrayList<>();

    public List<LocationStates> getAllState() { //constructor for get allState
        return allState;
    }

    @PostConstruct // For running this method first each this application run
    @Scheduled(cron = "* * 1 * * *") //corn job, will send request to target url each every hours
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStates> newState = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder() //
                .uri(URI.create(CORONA_VIRUS_URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); //Get response from http network
        //response body will return string

        //Parsing CSV
        StringReader csvReader = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvReader);
        for (CSVRecord record : records) {
            LocationStates locationState = new LocationStates();

            locationState.setState(record.get("Province/State"));
            locationState.setCountry(record.get("Country/Region"));
            int lastDayCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationState.setLatesCases(lastDayCases); //get Total Cases
            locationState.setDiffFromPrevDay(lastDayCases - prevDayCases); //get last day of cases (total cases in 1 last day)
            newState.add(locationState); //store locationState to our instance


        }
        this.allState = newState; //store allState to our model

    }
}
