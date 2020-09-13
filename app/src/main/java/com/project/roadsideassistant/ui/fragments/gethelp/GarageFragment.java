package com.project.roadsideassistant.ui.fragments.gethelp;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.project.roadsideassistant.R;
import com.project.roadsideassistant.data.models.Message;

public class GarageFragment extends Fragment {


    private AutoCompleteTextView garagesAtv;
    private Message message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_garage, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        message = GarageFragmentArgs.fromBundle(requireArguments()).getMessage();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Garages Dropdown
        assert getActivity() != null;

        String[] garages = getActivity().getResources().getStringArray(R.array.garages);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_menu_popup_item, garages);
        garagesAtv = view.findViewById(R.id.garages_atv);
        garagesAtv.setAdapter(adapter);

        garagesAtv.setOnItemClickListener((parent, view1, position, id) -> closeKeyboard());
        //Register the rest of the views
        Button skipButton = view.findViewById(R.id.skip_button);
        Button nextButton = view.findViewById(R.id.next_button);

        skipButton.setOnClickListener(v -> {

            GarageFragmentDirections.ActionGarageFragmentToReviewFragment action = GarageFragmentDirections.actionGarageFragmentToReviewFragment();

            action.setMessage(message);

            Navigation.findNavController(v).navigate(action);

        });

        nextButton.setOnClickListener(v -> {
            String garage = String.valueOf(garagesAtv.getText());

            assert message != null;

            message.setGarage(garage);

            if (TextUtils.isEmpty(garage)) {
                garagesAtv.setError("Select which garage you want");
                garagesAtv.requestFocus();
                return;
            }

            GarageFragmentDirections.ActionGarageFragmentToReviewFragment action = GarageFragmentDirections.actionGarageFragmentToReviewFragment();
            action.setMessage(message);
            Navigation.findNavController(v).navigate(action);

        });

    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null)
            imm.hideSoftInputFromWindow(garagesAtv.getApplicationWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

}
