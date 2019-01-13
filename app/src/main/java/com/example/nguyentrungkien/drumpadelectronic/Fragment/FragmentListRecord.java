package com.example.nguyentrungkien.drumpadelectronic.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyentrungkien.drumpadelectronic.Adapter.ListRecordAdapter;
import com.example.nguyentrungkien.drumpadelectronic.DTO.RecordDTO;
import com.example.nguyentrungkien.drumpadelectronic.ListRecord;
import com.example.nguyentrungkien.drumpadelectronic.PlayRecordActivity;
import com.example.nguyentrungkien.drumpadelectronic.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentListRecord extends Fragment{

    private RecyclerView rcvListRecord;
    public List<RecordDTO> list = new ArrayList<>();
    private ListRecordAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_record,container,false);
        rcvListRecord = view.findViewById(R.id.rcvListRecord);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rcvListRecord.setLayoutManager(layoutManager);

        list = PlayRecordActivity.list;


        adapter = new ListRecordAdapter(getContext(),list);


        rcvListRecord.setAdapter(adapter);


        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void reload()
    {
        adapter.notifyDataSetChanged();
    }

    public void reloadList()
    {
        list = PlayRecordActivity.list;
        adapter = new ListRecordAdapter(getContext(),list);
        rcvListRecord.setAdapter(adapter);
    }
}
