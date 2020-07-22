package com.jonbott.knownspies.Activities.SecretDetails;

import com.jonbott.knownspies.Helpers.Threading;
import com.jonbott.knownspies.ModelLayer.DTOs.SpyDTO;
import com.jonbott.knownspies.ModelLayer.Database.Realm.Spy;
import com.jonbott.knownspies.ModelLayer.ModelLayer;

import io.reactivex.functions.Consumer;

/**
 * Created by martin on 7/22/20.
 */

public class SecretDetailsPresenter {

    private ModelLayer modelLayer = new ModelLayer();

    private SpyDTO spy;
    private String password;

    public SecretDetailsPresenter(int spyId) {
        spy = modelLayer.spyForId(spyId);

        password = spy.password;
    }

    public void crackPassword(Consumer<String> finished) {
        Threading.async(()-> {
            //fake processing work
            Thread.sleep(2000);
            return true;
        }, success -> {
            finished.accept(password);
        });
    }
}
