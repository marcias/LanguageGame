package com.marciasc.languagegame;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class WordsRepository {
    private static WordsRepository sInstance;
    private List<WordTranslation> mWordsRepository = new ArrayList<>();

    private WordsRepository() {

    }

    synchronized
    public static WordsRepository getInstance() {
        if (sInstance == null) {
            sInstance = new WordsRepository();
        }
        return sInstance;
    }

    public List<WordTranslation> loadWords(Context context) {
        if (mWordsRepository.isEmpty()) {
            String json = loadWordsFromFile(context);
            mWordsRepository = new Gson().fromJson(json, new TypeToken<List<WordTranslation>>(){}.getType());
        }
        return mWordsRepository;
    }

    private String loadWordsFromFile(Context context) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(R.raw.words_v2);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
