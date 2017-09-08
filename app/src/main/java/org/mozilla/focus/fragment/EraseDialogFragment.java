/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.focus.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.mozilla.focus.R;
import org.mozilla.focus.telemetry.TelemetryWrapper;

/**
 * Fragment displaying a dialog prompting to confirm the erasing of the browsing history
 */
public class EraseDialogFragment extends DialogFragment {
    public static final String FRAGMENT_TAG = "should-erase-prompt-dialog";

    private BrowserFragment browserFrag;

    public static EraseDialogFragment instance(BrowserFragment browserFrag) {
        EraseDialogFragment dialogFrag = new EraseDialogFragment();
        dialogFrag.browserFrag = browserFrag;

        return dialogFrag;
    }

    @Override
    public AlertDialog onCreateDialog(Bundle bundle) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.DialogStyle);
        builder.setCancelable(true);
        builder.setTitle(getString(R.string.erase_dialog_title));

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.erase_dialog, null);
        builder.setView(dialogView);

        final TextView eraseDialogMessage = (TextView) dialogView.findViewById(R.id.erase_dialog_prompt_text);
        final Button eraseDialogCancelButton = (Button) dialogView.findViewById(R.id.erase_dialog_cancel);
        final Button eraseDialogEraseButton = (Button) dialogView.findViewById(R.id.erase_dialog_erase);

        eraseDialogMessage.setText(getString(R.string.erase_dialog_prompt_text));
        eraseDialogCancelButton.setText(getString(R.string.erase_dialog_action_cancel));
        eraseDialogEraseButton.setText(getString(R.string.erase_dialog_action_erase));


        eraseDialogCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonCancelClicked();
            }
        });

        eraseDialogEraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonEraseClicked();
            }
        });

        final AlertDialog alert = builder.create();

        return alert;
    }

    private void buttonCancelClicked() {
        this.dismiss();
    }

    private void buttonEraseClicked() {
        TelemetryWrapper.eraseBackToHomeEvent();
        this.browserFrag.erase();

        this.dismiss();
    }

}
