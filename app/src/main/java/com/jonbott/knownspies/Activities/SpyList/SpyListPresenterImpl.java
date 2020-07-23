package com.jonbott.knownspies.Activities.SpyList;

import com.jonbott.knownspies.ModelLayer.DTOs.SpyDTO;
import com.jonbott.knownspies.ModelLayer.Enums.Source;
import com.jonbott.knownspies.ModelLayer.ModelLayer;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by martin on 7/22/20.
 */

public class SpyListPresenterImpl implements SpyListPresenter {

    private static final String TAG = "SpyListPresenter";

    ModelLayer modelLayer;

    public SpyListPresenterImpl(ModelLayer modelLayer) {
        this.modelLayer = modelLayer;
    }

    //region Presenter Method

    @Override
    public void loadData(
            Consumer<List<SpyDTO>> onNewResults,
            Consumer<Source> notifyDataReceived
    ) {
        modelLayer.loadData(onNewResults, notifyDataReceived);
    }

    //endregion

}
