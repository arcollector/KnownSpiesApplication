package com.jonbott.knownspies.ModelLayer.Network;

import io.reactivex.functions.Consumer;

/**
 * Created by martin on 7/22/20.
 */

public interface NetworkLayer {
    void loadJson(Consumer<String> finished);
}
