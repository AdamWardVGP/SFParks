package award.sfparks.data;

import java.util.List;

import award.sfparks.model.ParkInfo;
import retrofit.http.GET;
import rx.Observable;

/**
 * Interface for retrofit endpoint for SFParks API
 */
public interface ParksAPI {

        String ENDPOINT = "https://data.sfgov.org/";

        @GET("/resource/z76i-7s65.json")
        Observable<List<ParkInfo>> getSFParkList();
}
