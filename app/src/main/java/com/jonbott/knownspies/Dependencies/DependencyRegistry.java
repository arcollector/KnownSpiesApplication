package com.jonbott.knownspies.Dependencies;

import android.os.Bundle;

import com.google.gson.Gson;
import com.jonbott.knownspies.Activities.Details.SpyDetailsActivity;
import com.jonbott.knownspies.Activities.Details.SpyDetailsPresenter;
import com.jonbott.knownspies.Activities.Details.SpyDetailsPresenterImpl;
import com.jonbott.knownspies.Activities.SecretDetails.SecretDetailsActivity;
import com.jonbott.knownspies.Activities.SecretDetails.SecretDetailsPresenter;
import com.jonbott.knownspies.Activities.SecretDetails.SecretDetailsPresenterImpl;
import com.jonbott.knownspies.Activities.SpyList.SpyListActivity;
import com.jonbott.knownspies.Activities.SpyList.SpyListPresenter;
import com.jonbott.knownspies.Activities.SpyList.SpyListPresenterImpl;
import com.jonbott.knownspies.Coordinators.RootCoordinator;
import com.jonbott.knownspies.Helpers.Constants;
import com.jonbott.knownspies.ModelLayer.Database.DataLayer;
import com.jonbott.knownspies.ModelLayer.Database.DataLayerImpl;
import com.jonbott.knownspies.ModelLayer.ModelLayer;
import com.jonbott.knownspies.ModelLayer.ModelLayerImpl;
import com.jonbott.knownspies.ModelLayer.Network.NetworkLayer;
import com.jonbott.knownspies.ModelLayer.Network.NetworkLayerImpl;
import com.jonbott.knownspies.ModelLayer.Translation.SpyTranslator;
import com.jonbott.knownspies.ModelLayer.Translation.TranslatorLayer;
import com.jonbott.knownspies.ModelLayer.Translation.TranslatorLayerImpl;

import java.util.NoSuchElementException;

import io.realm.Realm;

/**
 * Created by martin on 7/22/20.
 */

public class DependencyRegistry {

    public static DependencyRegistry shared = new DependencyRegistry();

    // region External Dependencies

    private Gson gson = new Gson();

    private Realm realm = Realm.getDefaultInstance();
    public Realm newRealmInstanceOnCurrentThread() {
        return Realm.getInstance(realm.getConfiguration());
    }

    // endregion

    // region Singletons

    public SpyTranslator spyTranslator = new SpyTranslator();

    public TranslatorLayer translatorLayer = createTranslationLayer();
    private TranslatorLayer createTranslationLayer() {
        return new TranslatorLayerImpl(gson, spyTranslator);
    }

    public DataLayer dataLayer = createDateLayer();
    private DataLayer createDateLayer() {
        return new DataLayerImpl(realm, this::newRealmInstanceOnCurrentThread);
    }

    public NetworkLayer networkLayer = new NetworkLayerImpl();

    public ModelLayer modelLayer = createModelLayer();
    private ModelLayer createModelLayer() {
        return new ModelLayerImpl(networkLayer, dataLayer, translatorLayer);
    }

    // endregion

    // region Injection Methods

    public void inject(SpyDetailsActivity activity, Bundle bundle)
        throws NoSuchElementException {
        int spyId = idFromBundle(bundle);
        SpyDetailsPresenter presenter = new SpyDetailsPresenterImpl(spyId, activity, modelLayer);
        activity.configureWith(presenter);
    }

    public void inject(SecretDetailsActivity activity, Bundle bundle)
            throws NoSuchElementException {
        int spyId = idFromBundle(bundle);
        SecretDetailsPresenter presenter = new SecretDetailsPresenterImpl(spyId, modelLayer);
        activity.configureWith(presenter);
    }

    public void inject(SpyListActivity activity) {
        SpyListPresenter presenter = new SpyListPresenterImpl(modelLayer);
        activity.configureWith(presenter);
    }

    // endregion

    // region Helper Methods

    private int idFromBundle(Bundle bundle) {
        if(bundle == null) {
            throw new NoSuchElementException("Unable to get spy id from bundle");
        }
        int spyId = bundle.getInt(Constants.spyIdKey);
        if(spyId == 0) {
            throw new NoSuchElementException("Unable to get spy id from bundle");
        }
        return spyId;
    }

    // endregion

}
