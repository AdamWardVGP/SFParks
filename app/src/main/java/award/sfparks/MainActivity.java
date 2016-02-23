package award.sfparks;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import award.sfparks.adapters.ParkListAdapter;
import award.sfparks.model.ParkInfo;
import award.sfparks.presenter.interfaces.ParkPresenter;
import award.sfparks.view.ParkListView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ParkListView {

    @Inject ParkPresenter presenter;

    @Bind(R.id.recycler_view) RecyclerView recyclerView;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.layout_offline) View offlineLayout;
    @Bind(R.id.progress_indicator) View progressLayout;

    private ParkListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SFParksApp.from(this)
                .getComponent()
                .inject(this);

        ButterKnife.bind(this);
        setupRecyclerView();
        setupToolbar();

        presenter.attachView(this);
        presenter.getParksList();
    }

    private void setupRecyclerView() {
        LinearLayoutManager mgr = new LinearLayoutManager(this);
        mgr.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mgr);
        adapter = new ParkListAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showParkList(List<ParkInfo> parks) {
        adapter.setItems(parks);
        recyclerView.setVisibility(View.VISIBLE);
        offlineLayout.setVisibility(View.GONE);
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        offlineLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        offlineLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        progressLayout.setVisibility(View.VISIBLE);
    }

    static final int LOCATION_REQ_CODE = 0x0;

    @Override
    public void requestPermissions() {
        ActivityCompat.requestPermissions(this,
            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
            LOCATION_REQ_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQ_CODE:
                presenter.LocationPermissionUpdate(grantResults[0] == PackageManager.PERMISSION_GRANTED);
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @OnClick(R.id.button_try_again)
    void onClick(View button) {
        presenter.getParksList();
    }
}
