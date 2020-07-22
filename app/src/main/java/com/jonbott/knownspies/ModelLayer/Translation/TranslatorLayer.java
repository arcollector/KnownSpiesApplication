package com.jonbott.knownspies.ModelLayer.Translation;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jonbott.knownspies.ModelLayer.DTOs.SpyDTO;
import com.jonbott.knownspies.ModelLayer.Database.Realm.Spy;
import com.jonbott.knownspies.ModelLayer.Enums.DTOType;

import java.util.List;

import io.realm.Realm;

/**
 * Created by martin on 7/22/20.
 */

public class TranslatorLayer {

    private static final String TAG = "TranslatorLayer";

    private SpyTranslator spyTranslator = new SpyTranslator();
    private Gson gson = new Gson();

    public List<SpyDTO> convertJson(String json) {
        Log.d(TAG, "converting json to dtos");

        TypeToken<List<SpyDTO>> token = new TypeToken<List<SpyDTO>>(){};
        return gson.fromJson(json, token.getType());
    }

    public SpyTranslator translatorFor(DTOType type) {
        switch (type) {
            case spy: return spyTranslator;
            default: return spyTranslator;
        }
    }

    public SpyDTO translate(Spy spy) {
        SpyDTO dto = spyTranslator.translate(spy);
        return dto;
    }

    public Spy translate(SpyDTO dto, Realm realm) {
        Spy spy = spyTranslator.translate(dto, realm);
        return spy;
    }
}
