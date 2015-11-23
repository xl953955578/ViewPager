package com.lyf.viewpager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private MyAdapter adapter;
    private List<String> food_names;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param lists  数据集合
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(ArrayList<String> lists) {
        if(lists==null){
            return null;
        }
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, lists);
        fragment.setArguments(args);
        return fragment;
    }

    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            food_names = (List<String>) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_blank, container, false);
        GridView list_food = (GridView) v.findViewById(R.id.list_food);
        adapter = new MyAdapter(getActivity().getApplicationContext(), food_names);
        list_food.setEmptyView(v.findViewById(R.id.tv_empty));
        list_food.setAdapter(adapter);
        return v;
    }


}
