package mobiledimension.exchangerates;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.SocialNetworkManager;
import com.github.gorbin.asne.core.listener.OnLoginCompleteListener;
import com.github.gorbin.asne.core.listener.OnPostingCompleteListener;
import com.github.gorbin.asne.vk.VkSocialNetwork;
import com.vk.sdk.VKScope;

import java.util.List;

/**
 * Created by Турал on 11.12.2017.
 */

public class FragmentSocialNetworks extends Fragment implements SocialNetworkManager.OnInitializationCompleteListener, OnLoginCompleteListener {
    public static final String SOCIAL_NETWORK_TAG = "SocialIntegrationMain.SOCIAL_NETWORK_TAG";
    final String VK_KEY = "6040457";
    String[] vkScope = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.STATUS,
    };
    public static SocialNetworkManager mSocialNetworkManager;
    Bitmap vkPhoto;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment, null);

        Button VK_share = (Button) v.findViewById(R.id.VK_share);
        VK_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int networkId = 0; //на случай если будут кнопки от других соц сетей
                networkId = VkSocialNetwork.ID;
                screenshot();
                SocialNetwork socialNetwork = mSocialNetworkManager.getSocialNetwork(networkId);
                if (!socialNetwork.isConnected()) {
                    if (networkId != 0) {
                        socialNetwork.requestLogin();
                        //Menu.showProgress("Loading social person");

                        Toast.makeText(getActivity(), "После авторизации нажмите повторно на публикацию", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Wrong networkId", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Bundle postParams = new Bundle();
                    postParams.putString(SocialNetwork.BUNDLE_LINK, "https://fixer.io");
                   // postParams.putString(SocialNetwork.BUNDLE_PICTURE, "photo14354205_456239074");

                    socialNetwork.requestPostPhotoMessageLink(vkPhoto, postParams, "ExchangeRates", postingComplete);
                }
            }
        });

        mSocialNetworkManager = (SocialNetworkManager) getFragmentManager().findFragmentByTag(SOCIAL_NETWORK_TAG);

        //Check if manager exist
        if (mSocialNetworkManager == null) {
            mSocialNetworkManager = new SocialNetworkManager();

            //Init and add to manager VkSocialNetwork
            VkSocialNetwork vkNetwork = new VkSocialNetwork(this, VK_KEY, vkScope);
            mSocialNetworkManager.addSocialNetwork(vkNetwork);


            //Initiate every network from mSocialNetworkManager
            getFragmentManager().beginTransaction().add(mSocialNetworkManager, SOCIAL_NETWORK_TAG).detach(mSocialNetworkManager).commit(); //Как я понял это Фрагмент в котором отображается оконо социальной сети, если я к ней подключен или подключусь.
            //getFragmentManager().beginTransaction().hide(mSocialNetworkManager).commit();
            mSocialNetworkManager.setOnInitializationCompleteListener(this);
        } else {
            //if manager exist - get and setup login only for initialized SocialNetworks
            if (!mSocialNetworkManager.getInitializedSocialNetworks().isEmpty()) {
                List<SocialNetwork> socialNetworks = mSocialNetworkManager.getInitializedSocialNetworks();
                for (SocialNetwork socialNetwork : socialNetworks) {
                    socialNetwork.setOnLoginCompleteListener(this);
                    initSocialNetwork(socialNetwork);
                }
            }
        }
        return v;
    }


    private OnPostingCompleteListener postingComplete = new OnPostingCompleteListener() {
        @Override
        public void onPostSuccessfully(int socialNetworkID) {
            try {
                Toast.makeText(getActivity(), "Опубликовано", Toast.LENGTH_LONG).show(); //выпадает NPE , если до вывода сообщения диалог уже закрыт
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
            Toast.makeText(getActivity(), "Error while sending: " + errorMessage, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onError(int networkId, String requestID, String errorMessage, Object data) {
        Toast.makeText(getActivity(), "ERROR: " + errorMessage, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onLoginSuccess(int networkId) {
        // startProfile(networkId);
    }


    private void initSocialNetwork(SocialNetwork socialNetwork) {
        if (socialNetwork.isConnected()) {
            switch (socialNetwork.getID()) {
                case VkSocialNetwork.ID:
                    // vk_share.setText("Show VK profile");
                    break;
            }
        }
    }


    @Override
    public void onSocialNetworkManagerInitialized() {
        //when init SocialNetworks - get and setup login only for initialized SocialNetworks
        for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.setOnLoginCompleteListener(this);
            initSocialNetwork(socialNetwork);
        }
    }


    void screenshot() {
        View rootView = getActivity().findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        vkPhoto = rootView.getDrawingCache();

    }
}
