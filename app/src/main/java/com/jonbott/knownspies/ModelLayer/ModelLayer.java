package com.jonbott.knownspies.ModelLayer;

import com.jonbott.knownspies.Helpers.Threading;
import com.jonbott.knownspies.ModelLayer.DTOs.SpyDTO;
import com.jonbott.knownspies.ModelLayer.Database.DataLayer;
import com.jonbott.knownspies.ModelLayer.Database.Realm.Spy;
import com.jonbott.knownspies.ModelLayer.Enums.Source;
import com.jonbott.knownspies.ModelLayer.Network.NetworkLayer;
import com.jonbott.knownspies.ModelLayer.Translation.SpyTranslator;
import com.jonbott.knownspies.ModelLayer.Translation.TranslatorLayer;

import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by martin on 7/22/20.
 */

public class ModelLayer {

    private NetworkLayer networkLayer = new NetworkLayer();
    private DataLayer dataLayer = new DataLayer();
    private TranslatorLayer translatorLayer = new TranslatorLayer();

    public void loadData(
            Consumer<List<Spy>> onNewResults,
            Consumer<Source> notifyDataRecieved
    ) {
        try {
            dataLayer.loadSpiesFromLocal(onNewResults);
            notifyDataRecieved.accept(Source.local);
        } catch (Exception e) {
            e.printStackTrace();
        }

        networkLayer.loadJson(json -> {
            notifyDataRecieved.accept(Source.network);
            persistJson(json, () -> {
                dataLayer.loadSpiesFromLocal(onNewResults);
            });
        });
    }

    private void persistJson(String json, Action finished) {
        List<SpyDTO> dtos = translatorLayer.convertJson(json);

        Threading.async(() -> {

            dataLayer.clearSpies(() -> {
                dtos.forEach(dto -> dto.initialize());
                SpyTranslator translator = translatorLayer.translatorFor(SpyDTO.dtoType);
                dataLayer.persistDTOs(dtos, translator);

                Threading.dispatchMain(() -> finished.run());
            });

            return true;
        });
    }

    //region Data loading

    public Spy spyForId(int spyId) {
        return dataLayer.spyForId(spyId);
    }

    //endregion

}
