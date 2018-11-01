package com.dbs.ui.components;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.dbs.ui.components.qrcode.DBSQRScannerFragment;
import com.dbs.ui.components.qrcode.DBSScanQRListener;
import com.dbs.ui.components.utils.PermissionUtils;
import com.journeyapps.barcodescanner.BarcodeResult;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DBSQRScannerFragmentTest {

    private static final long SLEEP_TIME = 500;

    @Rule
    public ActivityTestRule<DBSQRScannerActivity> rule = new ActivityTestRule<>(DBSQRScannerActivity.class);

    @Rule
    public GrantPermissionRule grantPermissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA);

    private DBSQRScannerFragment dbsqrScannerFragment;

    private DBSScanQRListener mockScanQRListener;

    @BeforeClass
    public static void beforeAll() {
        PermissionUtils.resetPermissions(Manifest.permission.CAMERA);
        resetPermissionStatusSharedPreferences();
    }

    @Before
    public void beforeEach() throws InterruptedException {
        mockScanQRListener = mock(DBSScanQRListener.class);
        dbsqrScannerFragment = DBSQRScannerFragment.newInstance(null);

        FragmentManager fragmentManager = rule.getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(com.dbs.components.test.R.id.container, dbsqrScannerFragment)
                .commit();

        sleepThread(SLEEP_TIME);
    }

    @Test
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.M)
    @Ignore
    public void a_shouldInvokePermissionDeniedWhenCameraRuntimePermissionIsDenied() throws UiObjectNotFoundException, InterruptedException {
        dbsqrScannerFragment.setListener(mockScanQRListener);
        sleepThread(SLEEP_TIME);

        PermissionUtils.denyRuntimePermission();
        sleepThread(SLEEP_TIME);

        verify(mockScanQRListener, times(1)).onCameraPermissionDenied();
    }

    @Test
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.M)
    @Ignore
    public void d_shouldInvokePermissionDeniedWhenDoNotAskAgainIsSelectedAndCameraPermissionIsDenied() throws UiObjectNotFoundException, InterruptedException {
        dbsqrScannerFragment.setListener(mockScanQRListener);
        sleepThread(SLEEP_TIME);

        PermissionUtils.selectDoNotAskMeAgain();
        PermissionUtils.denyRuntimePermission();
        sleepThread(SLEEP_TIME);

        verify(mockScanQRListener, times(1)).onCameraPermissionDenied();
    }

    @Test
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.M)
    @Ignore
    public void e_shouldInvokePermissionDeniedCallBackWhenDenyIsClickedOnCustomPermissionAlert() throws UiObjectNotFoundException, InterruptedException {
        dbsqrScannerFragment.setListener(mockScanQRListener);
        sleepThread(SLEEP_TIME);

        PermissionUtils.denyRuntimePermission();
        sleepThread(SLEEP_TIME);

        verify(mockScanQRListener, times(1)).onCameraPermissionDenied();
    }

    @Test
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.M)
    @Ignore
    public void f_shouldShowAlertWithCustomParametersSetInBundleIfCameraRuntimePermissionWasDeniedPreviously() throws InterruptedException {
        Bundle bundle = getTestBundle();
        DBSQRScannerFragment dbsqrScannerFragment = DBSQRScannerFragment.newInstance(bundle);
        rule.getActivity().getSupportFragmentManager().beginTransaction()
                .add(com.dbs.components.test.R.id.container, dbsqrScannerFragment)
                .commit();
        sleepThread(SLEEP_TIME);


        DBSScanQRListener onScanQRListener = mock(DBSScanQRListener.class);
        dbsqrScannerFragment.setListener(onScanQRListener);
        sleepThread(SLEEP_TIME);

        onView(ViewMatchers.withText("Test title")).check(matches(ViewMatchers.isDisplayed()));
        onView(ViewMatchers.withText("Test description")).check(matches(ViewMatchers.isDisplayed()));
        onView(ViewMatchers.withText("OK")).check(matches(ViewMatchers.isDisplayed()));
        onView(ViewMatchers.withText("NOK")).check(matches(ViewMatchers.isDisplayed()));
    }

    private Bundle getTestBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(DBSQRScannerFragment.PARAM_CAMERA_PERMISSION_TITLE, "Test title");
        bundle.putString(DBSQRScannerFragment.PARAM_CAMERA_PERMISSION_DESCRIPTION, "Test description");
        bundle.putString(DBSQRScannerFragment.PARAM_CAMERA_PERMISSION_POSITIVE_BUTTON_TEXT, "OK");
        bundle.putString(DBSQRScannerFragment.PARAM_CAMERA_PERMISSION_NEGATIVE_BUTTON_TEXT, "NOK");
        return bundle;
    }


    @Test
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.M)
    @Ignore
    public void g_shouldInvokePermissionDeniedWhenUserIsTakenToSettingsButDoesNotAllowCameraPermission() throws UiObjectNotFoundException, InterruptedException {
        dbsqrScannerFragment.setListener(mockScanQRListener);
        sleepThread(SLEEP_TIME);

        PermissionUtils.allowPermission();
        PermissionUtils.openPermissionsScreenInSettings();
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        device.pressBack();
        device.pressBack();
        sleepThread(SLEEP_TIME);

        verify(mockScanQRListener, times(1)).onCameraPermissionDenied();
    }

    @Test
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.M)
    @Ignore
    public void h_shouldInvokeOnScannedCallbackAfterUserEnablesCameraPermissionInSettings() throws UiObjectNotFoundException, InterruptedException {
        dbsqrScannerFragment.setListener(mockScanQRListener);
        sleepThread(SLEEP_TIME);

        PermissionUtils.allowPermission();
        PermissionUtils.openPermissionsScreenInSettings();
        PermissionUtils.enablePermissionInSettings("Camera");
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        device.pressBack();
        device.pressBack();
        sleepThread(SLEEP_TIME);

        BarcodeResult barcodeResult = mock(BarcodeResult.class);
        when(barcodeResult.getText()).thenReturn("ABC");
        dbsqrScannerFragment.barcodeResult(barcodeResult);

        verify(mockScanQRListener, times(1)).onScanned("ABC");
    }


    @Test
    public void i_shouldInvokeOnScannedCallbackWhenThereIsAValidBarcodeResult() {
        dbsqrScannerFragment.setListener(mockScanQRListener);

        BarcodeResult barcodeResult = mock(BarcodeResult.class);
        when(barcodeResult.getText()).thenReturn("decodedQR");
        dbsqrScannerFragment.barcodeResult(barcodeResult);

        verify(mockScanQRListener, times(1)).onScanned("decodedQR");
    }

    @Test
    public void j_shouldInvokeOnScannedCallBackOnlyOnceForSameQRCode() {
        dbsqrScannerFragment.setListener(mockScanQRListener);
        BarcodeResult barcodeResult = mock(BarcodeResult.class);

        when(barcodeResult.getText()).thenReturn("decodedQR");
        dbsqrScannerFragment.barcodeResult(barcodeResult);
        dbsqrScannerFragment.barcodeResult(barcodeResult);

        when(barcodeResult.getText()).thenReturn("decodedQR1");
        dbsqrScannerFragment.barcodeResult(barcodeResult);

        verify(mockScanQRListener, times(1)).onScanned("decodedQR");
        verify(mockScanQRListener, times(1)).onScanned("decodedQR1");
    }

    @Test
    public void k_shouldNotInvokeOnScannedCallBackIfBarcodeResultIsNull() {
        dbsqrScannerFragment.setListener(mockScanQRListener);
        dbsqrScannerFragment.barcodeResult(null);

        verify(mockScanQRListener, never()).onScanned(any(String.class));
    }

    @Test
    public void l_shouldNotInvokeOnScannedCallBackIfBarcodeResultTextIsEmpty() {
        dbsqrScannerFragment.setListener(mockScanQRListener);
        BarcodeResult barcodeResult = mock(BarcodeResult.class);
        when(barcodeResult.getText()).thenReturn("");
        dbsqrScannerFragment.barcodeResult(barcodeResult);

        verify(mockScanQRListener, never()).onScanned(any(String.class));
    }

    @Test
    public void m_shouldNotInvokeCallBackIfListenerIsNullAndItCanBeSetAgain() {
        dbsqrScannerFragment.setListener(null);

        BarcodeResult barcodeResult = mock(BarcodeResult.class);
        when(barcodeResult.getText()).thenReturn("ABC");
        dbsqrScannerFragment.barcodeResult(barcodeResult);

        dbsqrScannerFragment.setListener(mockScanQRListener);

        BarcodeResult barcodeResultAgain = mock(BarcodeResult.class);
        when(barcodeResultAgain.getText()).thenReturn("ABCD");
        dbsqrScannerFragment.barcodeResult(barcodeResultAgain);

        verify(mockScanQRListener, times(1)).onScanned("ABCD");
    }

    @Test
    public void n_shouldAllowUsingActivityAsListenerForQrScan() {
        BarcodeResult barcodeResult = mock(BarcodeResult.class);
        when(barcodeResult.getText()).thenReturn("ABC");

        dbsqrScannerFragment.barcodeResult(barcodeResult);

        Assert.assertEquals("ABC", rule.getActivity().getScannedString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void o_shouldThrowIllegalArgumentExceptionIfPermissionResultIsReceivedWithInvalidRequestCode() {
        String[] permissions = new String[1];
        permissions[0] = Manifest.permission.ACCESS_COARSE_LOCATION;
        int[] grantResults = new int[1];
        grantResults[0] = 9090;

        dbsqrScannerFragment.onRequestPermissionsResult(-1, permissions, grantResults);
    }


    @Test(expected = IllegalArgumentException.class)
    public void p_shouldThrowIllegalArgumentExceptionOnActivityResultWithInvalidRequestCode() {
        dbsqrScannerFragment.onActivityResult(-1, Activity.RESULT_OK, null);
    }

    @Test
    public void q_shouldInvokeOnScannedCallbackAfterRuntimePermissionIsGrantedFromNativeAlert() throws UiObjectNotFoundException, InterruptedException {
        dbsqrScannerFragment.setListener(mockScanQRListener);
        PermissionUtils.resetPermissions(Manifest.permission.CAMERA);

        sleepThread(SLEEP_TIME);
        PermissionUtils.allowPermission();
        sleepThread(SLEEP_TIME);

        BarcodeResult barcodeResult = mock(BarcodeResult.class);
        when(barcodeResult.getText()).thenReturn("ABC");
        dbsqrScannerFragment.barcodeResult(barcodeResult);

        verify(mockScanQRListener, times(1)).onScanned("ABC");
    }

    public static void sleepThread(long millis) throws InterruptedException {
        Thread.sleep(millis);
    }

    private static void resetPermissionStatusSharedPreferences() {
        SharedPreferences.Editor editor = InstrumentationRegistry.getContext().getSharedPreferences(DBSQRScannerFragment.KEY_PERMISSIONS_STATUS, Context.MODE_PRIVATE).edit();
        editor.putBoolean(Manifest.permission.CAMERA, false);
        editor.commit();
    }

    public static class DBSQRScannerActivity extends AppCompatActivity implements DBSScanQRListener {

        private String scannedString;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(com.dbs.components.test.R.layout.layout_dbs_qr_scan_test);
        }

        @Override
        public void onScanned(String code) {
            scannedString = code;
        }

        @Override
        public void onCameraPermissionDenied() {

        }

        public String getScannedString() {
            return scannedString;
        }
    }
}
