package com.example.sea.myradio2.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sea.myradio2.R;

import java.util.List;
import java.util.Map;

public class FragmentRecSchedule extends Fragment {

    MainActivity mainActivity;
    int currentPosForSchedule;
    private RecyclerView recView;
    private ScheduleAdapter sam;
    List<Map<String, String>> mScheduleList;
    Map mScheduleItemMap;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View  onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        recView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        //// TODO: 24.01.2018 Получить список с расписанием
        mScheduleList = mainActivity.mScheduleList;
        currentPosForSchedule = mainActivity.currentProgramPosition;

        sam = new ScheduleAdapter(mScheduleList);
        recView.setAdapter(sam);
        recView.scrollTo...(currentPosForSchedule);
    }

    private class ScheduleHolder extends RecyclerView.ViewHolder {
        private TextView mTitleText;
        private TextView mTimeText;
        private TextView mContentText;
        private ImageView mImgSchedule;

        ScheduleHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            super(inflater.inflate(viewType, parent, false));
            mTitleText = (TextView) itemView.findViewById(R.id.title_text);
            mTimeText = (TextView) itemView.findViewById(R.id.time_text);
            mContentText = (TextView) itemView.findViewById(R.id.content_text);
            mImgSchedule = (ImageView) itemView.findViewById(R.id.schedule_item_image);
        }

        void bind(Map scheduleItemMap) {
//            mScheduleItemMap = scheduleItemMap;
            mTitleText.setText((String)scheduleItemMap.get("title"));
            mTimeText.setText((String)scheduleItemMap.get("time"));
            mContentText.setText((String)scheduleItemMap.get("content"));
            mImgSchedule = ((ImageView)scheduleItemMap.get("img"));
        }
    }

    private class ScheduleAdapter extends RecyclerView.Adapter<ScheduleHolder> {

        private List<Map<String, String>> scheduleList;
       String TAG = "MY_LOG";
        ScheduleAdapter(List<Map<String, String>> scheduleList) {
            this.scheduleList = scheduleList;
        }

        @Override
        public ScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.d(TAG, "--- tracing for onCreateViewHolder() method");
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ScheduleHolder(layoutInflater, parent, viewType);
        }

        @Override
        public void onBindViewHolder(ScheduleHolder holder, int position) {
            Log.d(TAG, "--- tracing for onBindViewHolder() , position = " + position);
            Map mScheduleItemMap = mScheduleList.get(position);
            holder.bind(mScheduleItemMap);
        }

        @Override
        public int getItemCount() {
            return scheduleList.size();
        }

        public int getItemViewType(int position) {
            Log.d(TAG, "--- tracing for getItemViewType() , position = " + position);
            if (position == mainActivity.currentProgramPosition) {
                return R.layout.recyc_list_item_now;
            } else return R.layout.recyc_list_item;
        }
    }
}
