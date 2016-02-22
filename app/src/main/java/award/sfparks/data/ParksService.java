package award.sfparks.data;

import java.util.List;

import award.sfparks.model.ParkInfo;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Access to the Parks API and performs basic transformations on the data
 */
public class ParksService {

    private ParksAPI parksAPI;

    public ParksService(ParksAPI parksAPI) {
        this.parksAPI = parksAPI;
    }

    public Observable<List<ParkInfo>> getSFParks() {
        return parksAPI
                .getSFParkList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
