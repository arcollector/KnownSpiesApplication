package com.jonbott.knownspies.ModelLayer;

import com.jonbott.knownspies.ModelLayer.DTOs.SpyDTO;
import com.jonbott.knownspies.ModelLayer.Enums.Source;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by martin on 7/22/20.
 */

public interface ModelLayer {
    void loadData(
            Consumer<List<SpyDTO>> onNewResults,
            Consumer<Source> notifyDataRecieved
    );

    SpyDTO spyForId(int spyId);
}
