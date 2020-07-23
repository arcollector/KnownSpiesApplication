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

public class ModelLayerImpl implements ModelLayer {

    private NetworkLayer networkLayer;
    private DataLayer dataLayer;
    private TranslatorLayer translatorLayer;

    public ModelLayerImpl(NetworkLayer networkLayer, DataLayer dataLayer, TranslatorLayer translatorLayer) {
        this.networkLayer = networkLayer;
        this.dataLayer = dataLayer;
        this.translatorLayer = translatorLayer;
    }

    @Override
    public void loadData(
            Consumer<List<SpyDTO>> onNewResults,
            Consumer<Source> notifyDataRecieved
    ) {
        SpyTranslator spyTranslator = translatorLayer.translatorFor(SpyDTO.dtoType);
        try {
            dataLayer.loadSpiesFromLocal(spyTranslator::translate, onNewResults);
            notifyDataRecieved.accept(Source.local);
        } catch (Exception e) {
            e.printStackTrace();
        }

        networkLayer.loadJson(json -> {
            notifyDataRecieved.accept(Source.network);
            persistJson(json, () -> {
                dataLayer.loadSpiesFromLocal(spyTranslator::translate, onNewResults);
            });
        });
    }

    @Override
    public SpyDTO spyForId(int spyId) {
        Spy spy = dataLayer.spyForId(spyId);
        SpyDTO dto = translatorLayer.translate(spy);
        return dto;
    }

    @Override
    public SpyDTO spyForName(String name) {
        Spy spy = dataLayer.spyForName(name);
        SpyDTO spyDTO = translatorLayer.translate(spy);
        return spyDTO;
    }

    @Override
    public void save(List<SpyDTO> dtos, Action finished) {
        Threading.async(() -> {
            persistDTOs(dtos, finished);
            return true;
        });
    }

    private void persistDTOs(List<SpyDTO> dtos, Action finished) {
        SpyTranslator translator = translatorLayer.translatorFor(SpyDTO.dtoType);
        dataLayer.persistDTOs(dtos, translator::translate);

        Threading.dispatchMain(() -> finished.run());
    }

    private void persistJson(String json, Action finished) {
        List<SpyDTO> dtos = translatorLayer.convertJson(json);

        Threading.async(() -> {

            dataLayer.clearSpies(() -> {
                dtos.forEach(dto -> dto.initialize());
                persistDTOs(dtos, finished);

                Threading.dispatchMain(() -> finished.run());
            });

            return true;
        });
    }


}
