package com.anas.project2.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.project2.Model.ModelAttendanceFB;
import com.anas.project2.Model.ModelReportAttendance;
import com.anas.project2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class AdapterReportAttendanceRV extends RealmRecyclerViewAdapter<ModelReportAttendance, AdapterReportAttendanceRV.ViewHolderRA> {

    Context context;
    //    private final Activity mActivity;
    RealmResults<ModelReportAttendance> mList;
    Realm realm;

    String date_room_id;
    String room_id;
    String date;

    OrderedRealmCollection<ModelReportAttendance> data;

    public AdapterReportAttendanceRV(@Nullable OrderedRealmCollection<ModelReportAttendance> data, String date_room_id, String room_id, String date) {
        super(data, true);
        this.data = data;
        this.date_room_id = date_room_id;
        this.room_id = room_id;
        this.date = date;
    }

    @NonNull
    @Override
    public ViewHolderRA onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_attendance_row, parent, false);
        return new ViewHolderRA(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRA holder, int position) {
        ModelReportAttendance temp = getItem(position);
        holder.txtRA_name_row.setText(temp.getStud_name());
        holder.txtRA_uid_row.setText(temp.getStud_uid());
        if (temp.getStatus().equals("Present")) {

            holder.txtRA_status_row.setText("P");
            ModelAttendanceFB model = new ModelAttendanceFB(temp.getStud_name(), temp.getStatus());
            FirebaseDatabase.getInstance().getReference().child("ATTENDIFY")
                    .child(FirebaseAuth.getInstance().getUid())
                    .child("SECTIONS")
                    .child(room_id)
                    .child("ATTENDANCE")
                    .child(date)
                    .child(temp.getStud_uid())
                    .setValue(model);

            holder.cardRA_row.setCardBackgroundColor(context.getResources().getColor(R.color.P));

        } else {

            holder.txtRA_status_row.setText("A");
            ModelAttendanceFB model = new ModelAttendanceFB(temp.getStud_name(), temp.getStatus());
            FirebaseDatabase.getInstance().getReference().child("ATTENDIFY")
                    .child(FirebaseAuth.getInstance().getUid())
                    .child("SECTIONS")
                    .child(room_id)
                    .child("ATTENDANCE")
                    .child(date)
                    .child(temp.getStud_uid())
                    .setValue(model);
            holder.cardRA_row.setCardBackgroundColor(context.getResources().getColor(R.color.A));
        }
    }


    public class ViewHolderRA extends RecyclerView.ViewHolder {

        public TextView txtRA_name_row;
        public TextView txtRA_uid_row;
        public TextView txtRA_status_row;
        public CardView cardRA_row;

//        public Activity mActivity;
//        RealmResults<ModelReportAttendance> mList;

        public ViewHolderRA(@NonNull final View itemView) {
            super(itemView);

            txtRA_name_row = itemView.findViewById(R.id.txtRA_name_row);
            txtRA_uid_row = itemView.findViewById(R.id.txtRA_uid_row);
            txtRA_status_row = itemView.findViewById(R.id.txtRA_status_row);
            cardRA_row = itemView.findViewById(R.id.cardRA_row);

        }

    }


}

