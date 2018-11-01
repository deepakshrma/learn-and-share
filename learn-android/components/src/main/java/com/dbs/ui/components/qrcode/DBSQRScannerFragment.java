/*
 * - Licensed Materials - Property of DBS Bank SG
 * - "Restricted Materials of DBS Bank"
 *
 * APP Studio SDK, Copyright (c) 2018. DBS Bank SG
 *
 * Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *    - Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *
 *    - Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 */

package com.dbs.ui.components.qrcode;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dbs.components.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * The DBSQRScannerFragment helps to scan QR code and return the payload.
 *
 * @author DBS Bank, AppStudio Team
 * @see CompoundBarcodeView
 */
public class DBSQRScannerFragment extends Fragment implements BarcodeCallback {

    public static final String PARAM_CAMERA_PERMISSION_TITLE = "PARAM_TITLE";
    public static final String PARAM_CAMERA_PERMISSION_DESCRIPTION = "PARAM_DESCRIPTION";
    public static final String PARAM_CAMERA_PERMISSION_POSITIVE_BUTTON_TEXT = "PARAM_POSITIVE_BUTTON_TEXT";
    public static final String PARAM_CAMERA_PERMISSION_NEGATIVE_BUTTON_TEXT = "PARAM_NEGATIVE_BUTTON_TEXT";
    public static final String KEY_PERMISSIONS_STATUS = "PERMISSIONS_STATUS";
    private static final int PERMISSION_CALLBACK_CONSTANT = 1001;
    private static final int REQUEST_PERMISSION_SETTING = 1002;
    private SharedPreferences permissionStatusPrefs;
    private CompoundBarcodeView scannerView;
    private DBSScanQRListener scanQRListener;

    private String previousScannedCode;
    private String title;
    private String description;
    private String positiveButtonText;
    private String negativeButtonText;

    /**
     * Create fragment instance with specified bundle
     */
    public static DBSQRScannerFragment newInstance(Bundle args) {
        DBSQRScannerFragment dbsqrScannerFragment = new DBSQRScannerFragment();
        Bundle bundle = args != null ? args : new Bundle();
        dbsqrScannerFragment.setArguments(bundle);
        return dbsqrScannerFragment;
    }

    /**
     * Sets listener for scanned QR code.
     * @see DBSScanQRListener
     */
    public void setListener(DBSScanQRListener onScanListener) {
        scanQRListener = onScanListener;
    }

    /**
     * @inheritDoc
     * Sets current context as listener for scanned QR code if valid instance.
     * @see DBSScanQRListener
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DBSScanQRListener) {
            scanQRListener = (DBSScanQRListener) context;
        }
    }

    /**
     * @inheritDoc
     * Sets data from bundle
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        title = bundle.getString(PARAM_CAMERA_PERMISSION_TITLE, getString(R.string.title_grant_camera_permission));
        description = bundle.getString(PARAM_CAMERA_PERMISSION_DESCRIPTION, getString(R.string.description_grant_camera_permission));
        positiveButtonText = bundle.getString(PARAM_CAMERA_PERMISSION_POSITIVE_BUTTON_TEXT, getString(R.string.allow));
        negativeButtonText = bundle.getString(PARAM_CAMERA_PERMISSION_NEGATIVE_BUTTON_TEXT, getString(R.string.deny));

        previousScannedCode = "";
    }

    /**
     * @inheritDoc
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dbs_qr_scanner, container, false);
        initUi(view);
        return view;
    }

    /**
     * @inheritDoc
     * Checks for Camera Permission and requests if already not granted.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isPermissionGranted()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                permissionStatusPrefs = getActivity().getSharedPreferences(KEY_PERMISSIONS_STATUS, Context.MODE_PRIVATE);
                requestPermission();
            } else {
                showAlertToSetPermissionInSettings();
            }
        }
    }

    private boolean isPermissionGranted() {
        return (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void initUi(View view) {
        scannerView = view.findViewById(R.id.scannerView);
        Collection<BarcodeFormat> formats = Collections.singletonList(BarcodeFormat.QR_CODE);
        scannerView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        scannerView.decodeContinuous(this);
        scannerView.getStatusView().setVisibility(View.GONE);
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
            //default permission rationale when user denies permission without selecting "Don't ask again" in previous run
            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_CALLBACK_CONSTANT);
        } else if (permissionStatusPrefs.getBoolean(Manifest.permission.CAMERA, false)) {
            // when user selects "Don't ask again" and denies camera permission
            // Shows custom alert dialog to set permission in SETTINGS
            showAlertToSetPermissionInSettings();
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_CALLBACK_CONSTANT); //Asks permission in very first run
        }
        SharedPreferences.Editor editor = permissionStatusPrefs.edit();
        editor.putBoolean(Manifest.permission.CAMERA, true);
        editor.apply();
    }

    /**
     * @inheritDoc
     * Notify listener with scanned QR code payload.
     */
    @Override
    public void barcodeResult(BarcodeResult result) {
        if (result == null || TextUtils.isEmpty(result.getText())) {
            return;
        }
        String message = result.getText();
        if (!message.equals(previousScannedCode)) {
            if (scanQRListener != null) {
                scanQRListener.onScanned(message);
            }
            previousScannedCode = message;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void possibleResultPoints(List<ResultPoint> resultPoints) {
        //Nothing to be done.
    }

    /**
     * @inheritDoc
     * Pause scanner view.
     */
    @Override
    public void onPause() {
        super.onPause();
        scannerView.pause();
    }

    /**
     * @inheritDoc
     * Check for permissions and resume scanner view.
     */
    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isPermissionGranted()) {
                scannerView.resume();
            }
        } else {
            scannerView.resume();
        }
    }

    /**
     * @inheritDoc
     * Reset listener for QR code scanning.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        scanQRListener = null;
    }

    /**
     * @inheritDoc
     * If request code matches and permission is granted, resume scanner view.
     * If request code matches and permission denied, notify listener.
     * @throws IllegalArgumentException when requestCode does not match.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (isPermissionGranted()) {
                scannerView.resume();
            } else {
                scanQRListener.onCameraPermissionDenied();
            }
        } else {
            throw new IllegalArgumentException("onActivityResult()  Unhandled request code" + requestCode);
        }
    }

    /**
     * @inheritDoc
     * If request code matches and permission is granted, resume scanner view.
     * If request code matches and permission denied, notify listener.
     * @throws IllegalArgumentException when requestCode does not match.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                scannerView.resume();
            } else {
                scanQRListener.onCameraPermissionDenied();
            }
        } else {
            throw new IllegalArgumentException("onRequestPermissionsResult() Unhandled request code" + requestCode);
        }
    }

    private void showAlertToSetPermissionInSettings() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(description);
        builder.setPositiveButton(positiveButtonText, (dialog, which) -> {
            dialog.cancel();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
        });
        builder.setNegativeButton(negativeButtonText, (dialog, which) -> {
            dialog.cancel();
            scanQRListener.onCameraPermissionDenied();
        });
        builder.show();
    }
}