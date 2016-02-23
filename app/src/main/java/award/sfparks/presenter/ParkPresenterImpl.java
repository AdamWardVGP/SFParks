package award.sfparks.presenter;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.content.ContextCompat;

import java.util.Collections;
import java.util.List;

import award.sfparks.data.ParksService;
import award.sfparks.model.ParkInfo;
import award.sfparks.presenter.interfaces.ParkPresenter;
import award.sfparks.util.Constants;
import award.sfparks.util.LocationComparator;
import award.sfparks.view.ParkListView;
import rx.Subscriber;


public class ParkPresenterImpl implements ParkPresenter {

    private ParksService parksService;
    private ParkListView view;
    private List<ParkInfo> parkList;
    private Location userLocaiton;

    public ParkPresenterImpl(ParksService parksService) {
        this.parksService = parksService;
        getUserLocation();
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
        if (view != null) {
            view.showProgress();
        }

        parksService.getSFParks().subscribe(new Subscriber<List<ParkInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (view != null) {
                    view.showError();
                }
            }

            @Override
            public void onNext(List<ParkInfo> parkInfos) {
                if (view != null) {
                    parkList = parkInfos;
                    joinNetAndLocationRequests();
                }
            }
        });
    }

    private void getUserLocation() {
        //placeholder location
        userLocaiton = new Location("");
        userLocaiton.setLongitude(Constants.DEFAULT_LOCATION_LONGITUDE);
        userLocaiton.setLatitude(Constants.DEFAULT_LOCATION_LATITUDE);
    }

    private void joinNetAndLocationRequests() {
        if(parkList != null && userLocaiton != null) {
            ParkSort sorter = new ParkSort(userLocaiton);
            sorter.execute(parkList);
        }
    }

    private class ParkSort extends AsyncTask<List<ParkInfo>, Void, List<ParkInfo>> {

        private Location location;

        public ParkSort(Location location) {
            this.location = location;
        }

        @Override
        protected List<ParkInfo> doInBackground(List<ParkInfo> ... locationList) {
            Collections.sort(locationList[0],new LocationComparator(location));
            return locationList[0];
        }

        @Override
        protected void onPostExecute(List<ParkInfo> infos) {
            if(view != null) {
                view.showParkList(infos);
            }
        }
    }
}
