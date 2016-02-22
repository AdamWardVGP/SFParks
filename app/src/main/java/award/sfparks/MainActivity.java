package award.sfparks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;

import award.sfparks.model.ParkInfo;
import award.sfparks.presenter.interfaces.ParkPresenter;
import award.sfparks.view.ParkListView;

public class MainActivity extends AppCompatActivity implements ParkListView {

    @Inject
    ParkPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SFParksApp.from(this)
                .getComponent()
                .inject(this);

        presenter.attachView(this);
        presenter.getParksList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detatchView();
    }

    @Override
    public void showParkList(List<ParkInfo> parks) {

    }

    @Override
    public void onParkFetchError() {

    }
}
