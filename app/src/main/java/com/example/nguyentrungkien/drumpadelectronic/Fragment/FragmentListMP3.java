package com.example.nguyentrungkien.drumpadelectronic.Fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyentrungkien.drumpadelectronic.Adapter.Mp3Adapter;
import com.example.nguyentrungkien.drumpadelectronic.DTO.Mp3DTO;
import com.example.nguyentrungkien.drumpadelectronic.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FragmentListMP3 extends Fragment{

    private List<Mp3DTO> list;
    private Mp3Adapter adapter;
    private File directory;
    private RecyclerView rcvListMP3;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_mp3,container,false);
        rcvListMP3 = view.findViewById(R.id.rcvListMP3);
        if(Environment.getExternalStorageState() == null)
        {
            directory = new File(Environment.getDataDirectory().getAbsolutePath());
        }
        else if(Environment.getExternalStorageState() != null)
        {
            directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        }

        list = new ArrayList<>();
        List<File> files = getListFiles(directory);

        for (int i = 0; i < files.size(); i++)
        {
            Mp3DTO mp3DTO = new Mp3DTO();
            mp3DTO.setFileName(files.get(i).getName());
            mp3DTO.setFilePath(files.get(i).getAbsolutePath());
            list.add(mp3DTO);

            Log.e("aaaaaaaaaaaaaaa",files.get(i).getName());
            Log.e("aaaaaaaaaaaaaaa",files.get(i).getAbsolutePath());
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rcvListMP3.setLayoutManager(layoutManager);

        adapter = new Mp3Adapter(getContext(),list);
        rcvListMP3.setAdapter(adapter);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if(file.getName().endsWith(".mp3")||file.getName().endsWith(".3gp")){
                    inFiles.add(file);
                }
            }
        }
        return inFiles;
    }
}
