package award.sfparks.presenter;

import android.location.Location;
import android.os.AsyncTask;

import java.util.Collections;
import java.util.List;

import award.sfparks.data.ParksService;
import award.sfparks.model.ParkInfo;
import award.sfparks.presenter.interfaces.ParkPresenter;
import award.sfparks.util.Constants;
import award.sfparks.util.LocationComparator;
import award.sfparks.util.LocationService;
import award.sfparks.view.ParkListView;
import rx.Subscriber;


public class ParkPresenterImpl implements ParkPresenter, LocationService.LocationCallbacks {

    private final ParksService parksService;
    private final LocationService locationService;

    private ParkListView view;

    private List<ParkInfo> parkList;
    private Location userLocation;

    public ParkPresenterImpl(ParksService parksService, LocationService locationService) {
        this.parksService = parksService;
        this.locationService = locationService;
        locationService.setListener(this);
    }

    @Override
    public void attachView(ParkListView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void getParksList() {
        if (view != null) {
            view.showProgress();
        }

        locationService.getLocation();

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
            public void onNext(List<ParkInfo> parkList) {
                if (view != null) {
                    ParkPresenterImpl.this.parkList = parkList;
                    joinNetAndLocationRequests();
                }
            }
        });
    }

    @Override
    public void LocationPermissionUpdate(boolean granted) {
        if (granted) {
            locationService.getLocation();
        } else {
            useStubLocation();
            joinNetAndLocationRequests();
        }
    }

    private void joinNetAndLocationRequests() {
        if (parkList != null && userLocation != null) {
            ParkSort sorter = new ParkSort(userLocation);
            sorter.execute(parkList);
        }
    }

    private void useStubLocation() {
        //placeholder location
        userLocation = new Location("");
        userLocation.setLongitude(Constants.DEFAULT_LOCATION_LONGITUDE);
        userLocation.setLatitude(Constants.DEFAULT_LOCATION_LATITUDE);
    }

    @Override
    public void permissionNotGranted() {
        if (view != null)
            view.requestPermissions();
    }

    @Override
    public void noProviderAvailable() {
        useStubLocation();
        locationService.clearListener();
        joinNetAndLocationRequests();
    }

    @Override
    public void locationUpdate(Location location) {
        userLocation = location;
        locationService.clearListener();
        joinNetAndLocationRequests();
    }

    private class ParkSort extends AsyncTask<List<ParkInfo>, Void, List<ParkInfo>> {

        private Location location;

        public ParkSort(Location location) {
            this.location = location;
        }

        @Override
        protected List<ParkInfo> doInBackground(List<ParkInfo>... locationList) {
            Collections.sort(locationList[0], new LocationComparator(location));
            return locationList[0];
        }

        @Override
        protected void onPostExecute(List<ParkInfo> parkInfoList) {
            if (view != null) {
                view.showParkList(parkInfoList);
            }
        }
    }
}
