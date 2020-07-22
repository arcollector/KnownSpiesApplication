package com.jonbott.knownspies.Activities.SpyList;

import com.jonbott.knownspies.ModelLayer.DTOs.SpyDTO;
import com.jonbott.knownspies.ModelLayer.Database.Realm.Spy;
import com.jonbott.knownspies.ModelLayer.Enums.Source;
import com.jonbott.knownspies.ModelLayer.ModelLayer;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by martin on 7/22/20.
 */

public class SpyListPresenter {

    private static final String TAG = "SpyListPresenter";

    ModelLayer modelLayer = new ModelLayer();

    //region Presenter Method

    public void loadData(
            Consumer<List<SpyDTO>> onNewResults,
            Consumer<Source> notifyDataRecieved
    ) {
        modelLayer.loadData(onNewResults, notifyDataRecieved);
    }

    //endregion

}
