package com.jonbott.knownspies.ModelLayer.Translation;

import com.jonbott.knownspies.ModelLayer.DTOs.SpyDTO;
import com.jonbott.knownspies.ModelLayer.Database.Realm.Spy;
import com.jonbott.knownspies.ModelLayer.Enums.DTOType;

import java.util.List;

import io.realm.Realm;

/**
 * Created by martin on 7/22/20.
 */

public interface TranslatorLayer {
    List<SpyDTO> convertJson(String json);

    SpyTranslator translatorFor(DTOType type);

    SpyDTO translate(Spy spy);

    Spy translate(SpyDTO dto, Realm realm);
}
