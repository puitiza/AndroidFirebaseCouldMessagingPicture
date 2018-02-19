package pe.anthony.androidfcmpicture.FirebaseService;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

/**
 * Created by ANTHONY on 18/02/2018.
 */

public class MyFirebaseService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
      super.onTokenRefresh();
      sendNewTokenToServer(FirebaseInstanceId.getInstance().getToken());
    }

    private void sendNewTokenToServer(String token) {
        //Aqui se puede actualizar tu token en server
        //Para esta demostracion solo lo voy a print
        Log.d(TAG, "Refreshed token: " + token);
    }
}
