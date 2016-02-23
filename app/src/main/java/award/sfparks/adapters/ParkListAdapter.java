package award.sfparks.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import award.sfparks.R;
import award.sfparks.model.ParkInfo;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ParkListAdapter extends RecyclerView.Adapter<ParkListAdapter.PIViewHolder> {

    List<ParkInfo> parks = new ArrayList<>();

    public void setItems(List<ParkInfo> parks) {
        this.parks = parks;
        notifyDataSetChanged();
    }

    @Override
    public PIViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.park_info,parent,false);

        return new PIViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PIViewHolder holder, int position) {
        ParkInfo park = parks.get(position);

        holder.emailAddress.setText(park.email);
        holder.parkname.setText(park.parkname);
        holder.managerName.setText(park.psamanager);
        holder.phoneNumber.setText(park.number);
    }

    @Override
    public int getItemCount() {
        return parks.size();
    }

    public static class PIViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text_park_name) TextView parkname;
        @Bind(R.id.text_manager_name) TextView managerName;
        @Bind(R.id.text_phone_number) TextView phoneNumber;
        @Bind(R.id.text_email_address) TextView emailAddress;

        public PIViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
