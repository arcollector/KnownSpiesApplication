package com.jonbott.knownspies.Activities.SecretDetails;

import io.reactivex.functions.Consumer;

/**
 * Created by martin on 7/22/20.
 */

public interface SecretDetailsPresenter {
    String getPassword();
    void crackPassword(Consumer<String> finished);
}
