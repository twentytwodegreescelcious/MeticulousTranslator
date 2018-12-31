package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.service;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.Result;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by twentytwodegreescelcious on 12/31/2018.
 */
public interface ResultJsonConvertionService {
    List<Result> resultsAsList(JSONArray jsonArray);
}
