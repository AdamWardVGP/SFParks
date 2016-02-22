package award.sfparks.view;

import java.util.List;

import award.sfparks.model.ParkInfo;

public interface ParkListView {
    void showParkList(List<ParkInfo> parks);
    void onParkFetchError();
}
