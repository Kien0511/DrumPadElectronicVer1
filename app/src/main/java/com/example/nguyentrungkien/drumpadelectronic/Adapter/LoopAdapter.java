package com.example.nguyentrungkien.drumpadelectronic.Adapter;

import android.content.Context;
import android.media.SoundPool;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.nguyentrungkien.drumpadelectronic.DTO.LoopDTO;
import com.example.nguyentrungkien.drumpadelectronic.LoopActivity;
import com.example.nguyentrungkien.drumpadelectronic.R;

import java.util.List;

public class LoopAdapter extends RecyclerView.Adapter<LoopAdapter.ViewHolder> {

    Context context;
    List<LoopDTO> list;

    Runnable runnable;
    Runnable runnable1,runnable2,runnable3,runnable4,runnable5,runnable6,runnable7,runnable8;
    Runnable runnable9,runnable10,runnable11,runnable12,runnable13,runnable14,runnable15,runnable16;
    Runnable runnable17,runnable18,runnable19,runnable20,runnable21,runnable22,runnable23,runnable24;
    Runnable runnable25,runnable26,runnable27,runnable28,runnable29,runnable30,runnable31,runnable32;
    Handler handler;

    int soundId;

    int delay = 200;
    SoundPool sp;
    ImageButton im;
    int resPause, resNormal;
    public LoopAdapter(Context context, List<LoopDTO> list,SoundPool sp, int soundId, ImageButton im, int resPause, int resNormal) {
        this.context = context;
        this.list = list;
        this.sp = sp;
        this.soundId = soundId;
        this.im = im;
        this.resPause = resPause;
        this.resNormal = resNormal;
    }

    public LoopAdapter(Context context, List<LoopDTO> list,SoundPool sp, int soundId) {
        this.context = context;
        this.list = list;
        this.sp = sp;
        this.soundId = soundId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_loop, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        setDefault(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,holder.ibtnLoop5,holder.ibtnLoop6,
                holder.ibtnLoop7,holder.ibtnLoop8,holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,holder.ibtnLoop17,holder.ibtnLoop18,
                holder.ibtnLoop19,holder.ibtnLoop20,holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,holder.ibtnLoop29,holder.ibtnLoop30,
                holder.ibtnLoop31,holder.ibtnLoop32,position);

        setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                im,resNormal);

        holder.ibtnLoop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop1.getTag();
                if(tag == R.drawable.mark)
                {

                    holder.ibtnLoop1.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop1.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);

                }
                else
                {
                    holder.ibtnLoop1.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop1.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop2.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop2.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop2.setTag(R.drawable.base);
                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);

                }
                else
                {
                    holder.ibtnLoop2.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop2.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop3.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop3.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop3.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop3.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop3.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop4.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop4.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop4.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop4.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop4.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });


        holder.ibtnLoop5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop5.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop5.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop5.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop5.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop5.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop6.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop6.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop6.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop6.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop6.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop7.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop7.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop7.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop7.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop7.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop8.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop8.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop8.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop8.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop8.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop9.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop9.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop9.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop9.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop9.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });


        holder.ibtnLoop10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop10.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop10.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop10.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop10.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop10.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop11.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop11.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop11.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop11.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop11.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop12.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop12.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop12.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop12.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop12.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop13.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop13.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop13.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop13.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop13.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });


        holder.ibtnLoop14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop14.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop14.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop14.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop14.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop14.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop15.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop15.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop15.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop15.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop15.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop16.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop16.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop16.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop16.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop16.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop17.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop17.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop17.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop17.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop17.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop18.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop18.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop18.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop18.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop18.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop19.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop19.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop19.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop19.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop19.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });



        holder.ibtnLoop20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop20.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop20.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop20.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop20.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop20.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });




        holder.ibtnLoop21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop21.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop21.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop21.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop21.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop21.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop22.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop22.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop22.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop22.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop22.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop23.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop23.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop23.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop23.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop23.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop24.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop24.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop24.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop24.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop24.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop25.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop25.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop25.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop25.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop25.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop26.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop26.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop26.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop26.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop26.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });


        holder.ibtnLoop27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop27.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop27.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop27.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop27.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop27.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop28.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop28.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop28.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop28.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop28.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop29.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop29.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop29.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop29.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop29.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop30.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop30.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop30.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop30.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop30.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });

        holder.ibtnLoop31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop31.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop31.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop31.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop31.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop31.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });


        holder.ibtnLoop32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) holder.ibtnLoop32.getTag();
                if(tag == R.drawable.mark)
                {
                    holder.ibtnLoop32.setBackgroundResource(R.drawable.base);
                    holder.ibtnLoop32.setTag(R.drawable.base);

                    setResNormal(holder.ibtnLoop1,holder.ibtnLoop2,holder.ibtnLoop3,holder.ibtnLoop4,
                            holder.ibtnLoop5,holder.ibtnLoop6,holder.ibtnLoop7,holder.ibtnLoop8,
                            holder.ibtnLoop9,holder.ibtnLoop10,holder.ibtnLoop11,holder.ibtnLoop12,
                            holder.ibtnLoop13,holder.ibtnLoop14,holder.ibtnLoop15,holder.ibtnLoop16,
                            holder.ibtnLoop17,holder.ibtnLoop18,holder.ibtnLoop19,holder.ibtnLoop20,
                            holder.ibtnLoop21,holder.ibtnLoop22,holder.ibtnLoop23,holder.ibtnLoop24,
                            holder.ibtnLoop25,holder.ibtnLoop26,holder.ibtnLoop27,holder.ibtnLoop28,
                            holder.ibtnLoop29,holder.ibtnLoop30,holder.ibtnLoop31,holder.ibtnLoop32,
                            im,resNormal);
                }
                else
                {
                    holder.ibtnLoop32.setBackgroundResource(R.drawable.mark);
                    holder.ibtnLoop32.setTag(R.drawable.mark);

                    if((int) im.getTag() != resPause)
                    {
                        im.setBackgroundResource(resPause);
                        im.setTag(resPause);
                    }
                }
            }
        });





    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageButton ibtnLoop1, ibtnLoop2,ibtnLoop3,ibtnLoop4,ibtnLoop5,ibtnLoop6,ibtnLoop7,ibtnLoop8;
        ImageButton ibtnLoop9, ibtnLoop10,ibtnLoop11,ibtnLoop12,ibtnLoop13,ibtnLoop14,ibtnLoop15,ibtnLoop16;
        ImageButton ibtnLoop17, ibtnLoop18,ibtnLoop19,ibtnLoop20,ibtnLoop21,ibtnLoop22,ibtnLoop23,ibtnLoop24;
        ImageButton ibtnLoop25, ibtnLoop26,ibtnLoop27,ibtnLoop28,ibtnLoop29,ibtnLoop30,ibtnLoop31,ibtnLoop32;

        public ViewHolder(View itemView) {
            super(itemView);
            ibtnLoop1 = itemView.findViewById(R.id.ibtnLoop1);
            ibtnLoop2 = itemView.findViewById(R.id.ibtnLoop2);
            ibtnLoop3 = itemView.findViewById(R.id.ibtnLoop3);
            ibtnLoop4 = itemView.findViewById(R.id.ibtnLoop4);
            ibtnLoop5 = itemView.findViewById(R.id.ibtnLoop5);
            ibtnLoop6 = itemView.findViewById(R.id.ibtnLoop6);
            ibtnLoop7 = itemView.findViewById(R.id.ibtnLoop7);
            ibtnLoop8 = itemView.findViewById(R.id.ibtnLoop8);

            ibtnLoop9 = itemView.findViewById(R.id.ibtnLoop9);
            ibtnLoop10 = itemView.findViewById(R.id.ibtnLoop10);
            ibtnLoop11 = itemView.findViewById(R.id.ibtnLoop11);
            ibtnLoop12 = itemView.findViewById(R.id.ibtnLoop12);
            ibtnLoop13 = itemView.findViewById(R.id.ibtnLoop13);
            ibtnLoop14 = itemView.findViewById(R.id.ibtnLoop14);
            ibtnLoop15 = itemView.findViewById(R.id.ibtnLoop15);
            ibtnLoop16 = itemView.findViewById(R.id.ibtnLoop16);

            ibtnLoop17 = itemView.findViewById(R.id.ibtnLoop17);
            ibtnLoop18 = itemView.findViewById(R.id.ibtnLoop18);
            ibtnLoop19 = itemView.findViewById(R.id.ibtnLoop19);
            ibtnLoop20 = itemView.findViewById(R.id.ibtnLoop20);
            ibtnLoop21 = itemView.findViewById(R.id.ibtnLoop21);
            ibtnLoop22 = itemView.findViewById(R.id.ibtnLoop22);
            ibtnLoop23 = itemView.findViewById(R.id.ibtnLoop23);
            ibtnLoop24 = itemView.findViewById(R.id.ibtnLoop24);

            ibtnLoop25 = itemView.findViewById(R.id.ibtnLoop25);
            ibtnLoop26 = itemView.findViewById(R.id.ibtnLoop26);
            ibtnLoop27 = itemView.findViewById(R.id.ibtnLoop27);
            ibtnLoop28 = itemView.findViewById(R.id.ibtnLoop28);
            ibtnLoop29 = itemView.findViewById(R.id.ibtnLoop29);
            ibtnLoop30 = itemView.findViewById(R.id.ibtnLoop30);
            ibtnLoop31 = itemView.findViewById(R.id.ibtnLoop31);
            ibtnLoop32 = itemView.findViewById(R.id.ibtnLoop32);

            runHandler(ibtnLoop1,ibtnLoop2,ibtnLoop3,ibtnLoop4,ibtnLoop5,ibtnLoop6,ibtnLoop7,ibtnLoop8,
                    ibtnLoop9,ibtnLoop10,ibtnLoop11,ibtnLoop12,ibtnLoop13,ibtnLoop14,ibtnLoop15,ibtnLoop16,
                    ibtnLoop17,ibtnLoop18,ibtnLoop19,ibtnLoop20,ibtnLoop21,ibtnLoop22,ibtnLoop23,ibtnLoop24,
                    ibtnLoop25,ibtnLoop26,ibtnLoop27,ibtnLoop28,ibtnLoop29,ibtnLoop30,ibtnLoop31,ibtnLoop32);
        }



        public void runHandler(final ImageButton im1, final ImageButton im2,final ImageButton im3,final ImageButton im4,
                               final ImageButton im5, final ImageButton im6,final ImageButton im7,final ImageButton im8,
                               final ImageButton im9, final ImageButton im10,final ImageButton im11,final ImageButton im12,
                               final ImageButton im13, final ImageButton im14,final ImageButton im15,final ImageButton im16,
                               final ImageButton im17, final ImageButton im18,final ImageButton im19,final ImageButton im20,
                               final ImageButton im21, final ImageButton im22,final ImageButton im23,final ImageButton im24,
                               final ImageButton im25, final ImageButton im26,final ImageButton im27,final ImageButton im28,
                               final ImageButton im29, final ImageButton im30,final ImageButton im31,final ImageButton im32)
        {
            handler = new Handler();
            runnable1 = new Runnable() {
                @Override
                public void run() {
                    im32.setImageResource(android.R.color.transparent);
                    im1.setImageResource(R.drawable.move);
                    checkPlay((int) im1.getTag(),sp,soundId);

                    handler.postDelayed(runnable2,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable2 = new Runnable() {
                @Override
                public void run() {
                    im1.setImageResource(android.R.color.transparent);
                    im2.setImageResource(R.drawable.move);
                    checkPlay((int) im2.getTag(),sp,soundId);

                    handler.postDelayed(runnable3,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable3 = new Runnable() {
                @Override
                public void run() {
                    im2.setImageResource(android.R.color.transparent);
                    im3.setImageResource(R.drawable.move);
                    checkPlay((int) im3.getTag(),sp,soundId);

                    handler.postDelayed(runnable4,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable4 = new Runnable() {
                @Override
                public void run() {
                    im3.setImageResource(android.R.color.transparent);
                    im4.setImageResource(R.drawable.move);
                    checkPlay((int) im4.getTag(),sp,soundId);

                    handler.postDelayed(runnable5,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable5 = new Runnable() {
                @Override
                public void run() {
                    im4.setImageResource(android.R.color.transparent);
                    im5.setImageResource(R.drawable.move);
                    checkPlay((int) im5.getTag(),sp,soundId);

                    handler.postDelayed(runnable6,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable6 = new Runnable() {
                @Override
                public void run() {
                    im5.setImageResource(android.R.color.transparent);
                    im6.setImageResource(R.drawable.move);
                    checkPlay((int) im6.getTag(),sp,soundId);

                    handler.postDelayed(runnable7,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable7 = new Runnable() {
                @Override
                public void run() {
                    im6.setImageResource(android.R.color.transparent);
                    im7.setImageResource(R.drawable.move);
                    checkPlay((int) im7.getTag(),sp,soundId);

                    handler.postDelayed(runnable8,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable8 = new Runnable() {
                @Override
                public void run() {
                    im7.setImageResource(android.R.color.transparent);
                    im8.setImageResource(R.drawable.move);
                    checkPlay((int) im8.getTag(),sp,soundId);

                    handler.postDelayed(runnable9,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable9 = new Runnable() {
                @Override
                public void run() {
                    im8.setImageResource(android.R.color.transparent);
                    im9.setImageResource(R.drawable.move);
                    checkPlay((int) im9.getTag(),sp,soundId);

                    handler.postDelayed(runnable10,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable10 = new Runnable() {
                @Override
                public void run() {
                    im9.setImageResource(android.R.color.transparent);
                    im10.setImageResource(R.drawable.move);
                    checkPlay((int) im10.getTag(),sp,soundId);

                    handler.postDelayed(runnable11,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable11 = new Runnable() {
                @Override
                public void run() {
                    im10.setImageResource(android.R.color.transparent);
                    im11.setImageResource(R.drawable.move);
                    checkPlay((int) im11.getTag(),sp,soundId);

                    handler.postDelayed(runnable12,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable12 = new Runnable() {
                @Override
                public void run() {
                    im11.setImageResource(android.R.color.transparent);
                    im12.setImageResource(R.drawable.move);
                    checkPlay((int) im12.getTag(),sp,soundId);

                    handler.postDelayed(runnable13,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable13 = new Runnable() {
                @Override
                public void run() {
                    im12.setImageResource(android.R.color.transparent);
                    im13.setImageResource(R.drawable.move);
                    checkPlay((int) im13.getTag(),sp,soundId);

                    handler.postDelayed(runnable14,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable14 = new Runnable() {
                @Override
                public void run() {
                    im13.setImageResource(android.R.color.transparent);
                    im14.setImageResource(R.drawable.move);
                    checkPlay((int) im14.getTag(),sp,soundId);

                    handler.postDelayed(runnable15,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable15 = new Runnable() {
                @Override
                public void run() {
                    im14.setImageResource(android.R.color.transparent);
                    im15.setImageResource(R.drawable.move);
                    checkPlay((int) im15.getTag(),sp,soundId);

                    handler.postDelayed(runnable16,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable16 = new Runnable() {
                @Override
                public void run() {
                    im15.setImageResource(android.R.color.transparent);
                    im16.setImageResource(R.drawable.move);
                    checkPlay((int) im16.getTag(),sp,soundId);

                    handler.postDelayed(runnable17,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable17 = new Runnable() {
                @Override
                public void run() {
                    im16.setImageResource(android.R.color.transparent);
                    im17.setImageResource(R.drawable.move);
                    checkPlay((int) im17.getTag(),sp,soundId);

                    handler.postDelayed(runnable18,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable18 = new Runnable() {
                @Override
                public void run() {
                    im17.setImageResource(android.R.color.transparent);
                    im18.setImageResource(R.drawable.move);
                    checkPlay((int) im18.getTag(),sp,soundId);

                    handler.postDelayed(runnable19,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };
            runnable19 = new Runnable() {
                @Override
                public void run() {
                    im18.setImageResource(android.R.color.transparent);
                    im19.setImageResource(R.drawable.move);
                    checkPlay((int) im19.getTag(),sp,soundId);

                    handler.postDelayed(runnable20,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };
            runnable20 = new Runnable() {
                @Override
                public void run() {
                    im19.setImageResource(android.R.color.transparent);
                    im20.setImageResource(R.drawable.move);
                    checkPlay((int) im20.getTag(),sp,soundId);

                    handler.postDelayed(runnable21,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable21 = new Runnable() {
                @Override
                public void run() {
                    im20.setImageResource(android.R.color.transparent);
                    im21.setImageResource(R.drawable.move);
                    checkPlay((int) im21.getTag(),sp,soundId);

                    handler.postDelayed(runnable22,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };
            runnable22 = new Runnable() {
                @Override
                public void run() {
                    im21.setImageResource(android.R.color.transparent);
                    im22.setImageResource(R.drawable.move);
                    checkPlay((int) im22.getTag(),sp,soundId);

                    handler.postDelayed(runnable23,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable23 = new Runnable() {
                @Override
                public void run() {
                    im22.setImageResource(android.R.color.transparent);
                    im23.setImageResource(R.drawable.move);
                    checkPlay((int) im23.getTag(),sp,soundId);

                    handler.postDelayed(runnable24,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable24 = new Runnable() {
                @Override
                public void run() {
                    im23.setImageResource(android.R.color.transparent);
                    im24.setImageResource(R.drawable.move);
                    checkPlay((int) im24.getTag(),sp,soundId);

                    handler.postDelayed(runnable25,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable25 = new Runnable() {
                @Override
                public void run() {
                    im24.setImageResource(android.R.color.transparent);
                    im25.setImageResource(R.drawable.move);
                    checkPlay((int) im25.getTag(),sp,soundId);

                    handler.postDelayed(runnable26,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable26 = new Runnable() {
                @Override
                public void run() {
                    im25.setImageResource(android.R.color.transparent);
                    im26.setImageResource(R.drawable.move);
                    checkPlay((int) im26.getTag(),sp,soundId);

                    handler.postDelayed(runnable27,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable27 = new Runnable() {
                @Override
                public void run() {
                    im26.setImageResource(android.R.color.transparent);
                    im27.setImageResource(R.drawable.move);
                    checkPlay((int) im27.getTag(),sp,soundId);

                    handler.postDelayed(runnable28,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable28 = new Runnable() {
                @Override
                public void run() {
                    im27.setImageResource(android.R.color.transparent);
                    im28.setImageResource(R.drawable.move);
                    checkPlay((int) im28.getTag(),sp,soundId);

                    handler.postDelayed(runnable29,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable29 = new Runnable() {
                @Override
                public void run() {
                    im28.setImageResource(android.R.color.transparent);
                    im29.setImageResource(R.drawable.move);
                    checkPlay((int) im29.getTag(),sp,soundId);

                    handler.postDelayed(runnable30,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));

                }
            };

            runnable30 = new Runnable() {
                @Override
                public void run() {
                    im29.setImageResource(android.R.color.transparent);
                    im30.setImageResource(R.drawable.move);
                    checkPlay((int) im30.getTag(),sp,soundId);

                    handler.postDelayed(runnable31,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));
                }
            };

            runnable31 = new Runnable() {
                @Override
                public void run() {
                    im30.setImageResource(android.R.color.transparent);
                    im31.setImageResource(R.drawable.move);
                    checkPlay((int) im31.getTag(),sp,soundId);

                    handler.postDelayed(runnable32,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));
                }
            };

            runnable32 = new Runnable() {
                @Override
                public void run() {
                    im31.setImageResource(android.R.color.transparent);
                    im32.setImageResource(R.drawable.move);
                    checkPlay((int) im32.getTag(),sp,soundId);

                    handler.postDelayed(runnable1,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));
                }
            };



            handler.postDelayed(runnable1,timeDelay(Integer.parseInt(LoopActivity.txtCurrentProgress.getText().toString())));
        }
    }



    public void playSoundPool(SoundPool sp, int soundId)
    {
        sp.play(soundId,1,1,0,0,1);
    }

    public void checkPlay(int tag,SoundPool sp, int soundId)
    {

        if(tag == R.drawable.mark && (int) im.getTag() == resPause)
        {
            playSoundPool(sp,soundId);
            im.setBackgroundResource(R.color.colorWhite);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    im.setBackgroundResource(resPause);
                }
            },100);
        }
    }

    public void removeHandler()
    {
        handler.removeCallbacksAndMessages(null);
    }

    public long timeDelay(int time)
    {
        long delayHandler = (long) (delay * 100)/(long) time;

        return delayHandler;
    }

    public void setResNormal(ImageButton im1, ImageButton im2,ImageButton im3,ImageButton im4,
                             ImageButton im5, ImageButton im6,ImageButton im7,ImageButton im8,
                             ImageButton im9, ImageButton im10,ImageButton im11,ImageButton im12,
                             ImageButton im13, ImageButton im14,ImageButton im15,ImageButton im16,
                             ImageButton im17, ImageButton im18,ImageButton im19,ImageButton im20,
                             ImageButton im21, ImageButton im22,ImageButton im23,ImageButton im24,
                             ImageButton im25, ImageButton im26,ImageButton im27,ImageButton im28,
                             ImageButton im29, ImageButton im30,ImageButton im31,ImageButton im32,
                             ImageButton im, int resNormal)
    {
        int tag1 = (int) im1.getTag();
        int tag2 = (int) im2.getTag();
        int tag3 = (int) im3.getTag();
        int tag4 = (int) im4.getTag();
        int tag5 = (int) im5.getTag();
        int tag6 = (int) im6.getTag();
        int tag7 = (int) im7.getTag();
        int tag8 = (int) im8.getTag();
        int tag9 = (int) im9.getTag();
        int tag10 = (int) im10.getTag();
        int tag11 = (int) im11.getTag();
        int tag12 = (int) im12.getTag();
        int tag13 = (int) im13.getTag();
        int tag14 = (int) im14.getTag();
        int tag15 = (int) im15.getTag();
        int tag16 = (int) im16.getTag();
        int tag17 = (int) im17.getTag();
        int tag18 = (int) im18.getTag();
        int tag19 = (int) im19.getTag();
        int tag20 = (int) im20.getTag();
        int tag21 = (int) im21.getTag();
        int tag22 = (int) im22.getTag();
        int tag23 = (int) im23.getTag();
        int tag24 = (int) im24.getTag();
        int tag25 = (int) im25.getTag();
        int tag26 = (int) im26.getTag();
        int tag27 = (int) im27.getTag();
        int tag28 = (int) im28.getTag();
        int tag29 = (int) im29.getTag();
        int tag30 = (int) im30.getTag();
        int tag31 = (int) im31.getTag();
        int tag32 = (int) im32.getTag();
        if(tag1 == R.drawable.base && tag2 == R.drawable.base && tag3 == R.drawable.base && tag4 == R.drawable.base
                && tag5 == R.drawable.base && tag6 == R.drawable.base && tag7 == R.drawable.base && tag8 == R.drawable.base
                && tag9 == R.drawable.base && tag10 == R.drawable.base && tag11 == R.drawable.base && tag12 == R.drawable.base
                && tag13 == R.drawable.base && tag14 == R.drawable.base && tag15 == R.drawable.base && tag16 == R.drawable.base
                && tag17 == R.drawable.base && tag18 == R.drawable.base && tag19 == R.drawable.base && tag20 == R.drawable.base
                && tag21 == R.drawable.base && tag22 == R.drawable.base && tag23 == R.drawable.base && tag24 == R.drawable.base
                && tag25 == R.drawable.base && tag26 == R.drawable.base && tag27 == R.drawable.base && tag28 == R.drawable.base
                && tag29 == R.drawable.base && tag30 == R.drawable.base && tag31 == R.drawable.base && tag32 == R.drawable.base
                ){
            im.setBackgroundResource(resNormal);
            im.setTag(resNormal);
        }
    }

    public void setDefault(ImageButton im1, ImageButton im2,ImageButton im3,ImageButton im4,
                           ImageButton im5, ImageButton im6,ImageButton im7,ImageButton im8,
                           ImageButton im9, ImageButton im10,ImageButton im11,ImageButton im12,
                           ImageButton im13, ImageButton im14,ImageButton im15,ImageButton im16,
                           ImageButton im17, ImageButton im18,ImageButton im19,ImageButton im20,
                           ImageButton im21, ImageButton im22,ImageButton im23,ImageButton im24,
                           ImageButton im25, ImageButton im26,ImageButton im27,ImageButton im28,
                           ImageButton im29, ImageButton im30,ImageButton im31,ImageButton im32,int position)
    {
        im1.setBackgroundResource(list.get(position).getSrc());
        im1.setTag(R.drawable.base);

        im2.setBackgroundResource(list.get(position).getSrc());
        im2.setTag(R.drawable.base);

        im3.setBackgroundResource(list.get(position).getSrc());
        im3.setTag(R.drawable.base);

        im4.setBackgroundResource(list.get(position).getSrc());
        im4.setTag(R.drawable.base);

        im5.setBackgroundResource(list.get(position).getSrc());
        im5.setTag(R.drawable.base);

        im6.setBackgroundResource(list.get(position).getSrc());
        im6.setTag(R.drawable.base);

        im7.setBackgroundResource(list.get(position).getSrc());
        im7.setTag(R.drawable.base);

        im8.setBackgroundResource(list.get(position).getSrc());
        im8.setTag(R.drawable.base);

        im9.setBackgroundResource(list.get(position).getSrc());
        im9.setTag(R.drawable.base);

        im10.setBackgroundResource(list.get(position).getSrc());
        im10.setTag(R.drawable.base);

        im11.setBackgroundResource(list.get(position).getSrc());
        im11.setTag(R.drawable.base);

        im12.setBackgroundResource(list.get(position).getSrc());
        im12.setTag(R.drawable.base);

        im13.setBackgroundResource(list.get(position).getSrc());
        im13.setTag(R.drawable.base);

        im14.setBackgroundResource(list.get(position).getSrc());
        im14.setTag(R.drawable.base);

        im15.setBackgroundResource(list.get(position).getSrc());
        im15.setTag(R.drawable.base);

        im16.setBackgroundResource(list.get(position).getSrc());
        im16.setTag(R.drawable.base);

        im17.setBackgroundResource(list.get(position).getSrc());
        im17.setTag(R.drawable.base);

        im18.setBackgroundResource(list.get(position).getSrc());
        im18.setTag(R.drawable.base);

        im19.setBackgroundResource(list.get(position).getSrc());
        im19.setTag(R.drawable.base);

        im20.setBackgroundResource(list.get(position).getSrc());
        im20.setTag(R.drawable.base);

        im21.setBackgroundResource(list.get(position).getSrc());
        im21.setTag(R.drawable.base);

        im22.setBackgroundResource(list.get(position).getSrc());
        im22.setTag(R.drawable.base);

        im23.setBackgroundResource(list.get(position).getSrc());
        im23.setTag(R.drawable.base);

        im24.setBackgroundResource(list.get(position).getSrc());
        im24.setTag(R.drawable.base);

        im25.setBackgroundResource(list.get(position).getSrc());
        im25.setTag(R.drawable.base);

        im26.setBackgroundResource(list.get(position).getSrc());
        im26.setTag(R.drawable.base);

        im27.setBackgroundResource(list.get(position).getSrc());
        im27.setTag(R.drawable.base);

        im28.setBackgroundResource(list.get(position).getSrc());
        im28.setTag(R.drawable.base);

        im29.setBackgroundResource(list.get(position).getSrc());
        im29.setTag(R.drawable.base);

        im30.setBackgroundResource(list.get(position).getSrc());
        im30.setTag(R.drawable.base);

        im31.setBackgroundResource(list.get(position).getSrc());
        im31.setTag(R.drawable.base);

        im32.setBackgroundResource(list.get(position).getSrc());
        im32.setTag(R.drawable.base);
    }

    public void setList(List<LoopDTO> list)
    {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}
