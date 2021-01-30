package com.example.randomtreasure;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * @since 2021-1-25
 */
public class FirstFragment extends Fragment {

    private TextView treasureText;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    /**
     * Handles the generated treasure. It displays the treasure using the supplied TextView
     * @param treasure the top level Treasure Component that describes the treasure
     */
    private void treasureHandler(TreasureComponent treasure) {
        String price = (treasure.cost().value() / 100) + "";
        if (treasure.cost().value() % 100 != 0)
            price = new DecimalFormat("#0.00",
                    DecimalFormatSymbols.getInstance(Locale.ENGLISH)).format(
                    treasure.cost().value() / 100);
        String out = treasure.name() + ", $" + price;
        treasureText.setText(out);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        treasureText = view.findViewById(R.id.treasure);

        view.findViewById(R.id.button_spice).setOnClickListener(view1 -> treasureHandler(TreasureBuilder.buildSpice()));
    }
}