package com.jonbott.knownspies.Activities.SecretDetails;

import com.jonbott.knownspies.Helpers.Threading;
import com.jonbott.knownspies.ModelLayer.DTOs.SpyDTO;
import com.jonbott.knownspies.ModelLayer.ModelLayer;

import io.reactivex.functions.Consumer;

/**
 * Created by martin on 7/22/20.
 */

public class SecretDetailsPresenterImpl implements SecretDetailsPresenter {

    private String password;

    @Override
    public String getPassword() {
        return password;
    }

    private int spyId;

    private SpyDTO spy;
    private ModelLayer modelLayer;

    public SecretDetailsPresenterImpl(int spyId, ModelLayer modelLayer) {
        this.spyId = spyId;
        this.modelLayer = modelLayer;

        configureData();
    }

    private void configureData() {
        spy = modelLayer.spyForId(spyId);
        password = spy.password;
    }

    @Override
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
