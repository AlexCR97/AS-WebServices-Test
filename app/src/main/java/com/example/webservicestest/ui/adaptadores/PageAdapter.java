package com.example.webservicestest.ui.adaptadores;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.webservicestest.ui.fragmentos.TabChatsFragment;
import com.example.webservicestest.ui.fragmentos.TabUsuariosFragment;

public class PageAdapter extends FragmentStatePagerAdapter {

    private final int tabCount;

    public PageAdapter(@NonNull FragmentManager fm, int tabCount) {
        super(fm, tabCount);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new TabChatsFragment();
            case 1: return new TabUsuariosFragment();

            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
