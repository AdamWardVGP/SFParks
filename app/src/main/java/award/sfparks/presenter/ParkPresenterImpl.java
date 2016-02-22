package award.sfparks.presenter;

import android.util.Log;

import java.util.List;

import award.sfparks.data.ParksService;
import award.sfparks.model.ParkInfo;
import award.sfparks.presenter.interfaces.ParkPresenter;
import award.sfparks.view.ParkListView;
import rx.Subscriber;


public class ParkPresenterImpl implements ParkPresenter {

    private ParksService parksService;
    private ParkListView view;

    public ParkPresenterImpl(ParksService parksService) {
        this.parksService = parksService;
    }

    @Override
    public void attachView(ParkListView view) {
        this.view = view;
    }

    @Override
    public void detatchView() {
        this.view = null;
    }

    @Override
    public void getParksList() {
        parksService.getSFParks().subscribe(new Subscriber<List<ParkInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<ParkInfo> parkInfos) {
                view.showParkList(parkInfos);
            }
        });
    }
}
