package edu.esiea.a2025_s5_esiea_android_finals.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import edu.esiea.a2025_s5_esiea_android_finals.R;
import edu.esiea.a2025_s5_esiea_android_finals.ViewModel.PlaceViewModel;
import edu.esiea.a2025_s5_esiea_android_finals.business.models.Accomodation;

public class PointsOfInterestListFragment extends Fragment {
    private ListView poiListView;
    private ArrayAdapter<String> adapter;
    private PlaceViewModel viewModel;

    public PointsOfInterestListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_poi_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        poiListView = view.findViewById(R.id.poi_list_view);
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, new ArrayList<>());
        poiListView.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(PlaceViewModel.class);
        viewModel.getAllAccommodations().observe(getViewLifecycleOwner(), accommodations -> {
            adapter.clear();
            if (accommodations != null) {
                for (Accomodation accommodation : accommodations) {
                    // Here you can customize what you show (e.g., name and description)
                    adapter.add(accommodation.name);
                }
            }
            adapter.notifyDataSetChanged();
        });

        // Set click listener for the back button
        view.findViewById(R.id.btn_back).setOnClickListener(v -> backToMap());

    }

    public void backToMap() {
        // Go back to the map fragment
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new MapFragment());
        fragmentTransaction.commit();
    }
}