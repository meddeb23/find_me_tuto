package com.example.findme.ui.dashboard;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.findme.MainActivity;
import com.example.findme.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private static String smsText = "findme";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = binding.editTextPhone.getText().toString();
                System.out.println("phone number: "+phoneNumber);
                if(MainActivity.permission){
                    SmsManager manager = SmsManager.getDefault();
                    manager.sendTextMessage(phoneNumber, null, DashboardFragment.smsText,null, null);
                    System.out.println("sending sms to " + phoneNumber);
                }
            }
        });
    //        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getTex t().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}