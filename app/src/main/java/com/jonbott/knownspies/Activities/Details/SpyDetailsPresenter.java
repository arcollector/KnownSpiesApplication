package com.jonbott.knownspies.Activities.Details;

import android.content.Context;

import com.jonbott.knownspies.Helpers.Helper;
import com.jonbott.knownspies.ModelLayer.Database.Realm.Spy;
import com.jonbott.knownspies.ModelLayer.ModelLayer;

/**
 * Created by martin on 7/22/20.
 */

public class SpyDetailsPresenter {

    private ModelLayer modelLayer = new ModelLayer();

    Spy spy;

    public String age;
    public int imageId;
    public String name;
    public String gender;
    public String imageName;
    public int spyId;

    private Context context;

    public SpyDetailsPresenter(int spyId) {
        this.spyId = spyId;

        spy = modelLayer.spyForId(spyId);

        configureSpy();
    }

    private void configureSpy() {
        age = String.valueOf(spy.age);
        name = spy.name;
        gender = spy.gender;
        imageName = spy.imageName;
    }

    public void configureWithContext(Context context) {
        this.context = context;
        imageId = Helper.resourceIdWith(context, imageName);
    }

}
