package award.sfparks.presenter.interfaces;

import award.sfparks.view.ParkListView;
import dagger.Module;

@Module
public interface ParkPresenter {

    void attachView(ParkListView view);

    void detatchView();

    void getParksList();
}
