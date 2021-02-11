package com.nishanth.recorderapp.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.nishanth.recorderapp.Fragments.AudioFragment;
import com.nishanth.recorderapp.Fragments.RecordFragment;

public class FragmentsAdapter extends FragmentPagerAdapter {

    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public FragmentsAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                return new AudioFragment();


            case 1:
                return new RecordFragment();



            default:
                return new AudioFragment();
        }


    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title=null;

        if(position==0)
        {
            title="Audio";
        }
        if(position==1)
        {
            title="Record";
        }

        return title;
    }
}
