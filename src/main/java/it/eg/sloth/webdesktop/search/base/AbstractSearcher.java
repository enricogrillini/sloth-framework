package it.eg.sloth.webdesktop.search.base;

import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.webdesktop.search.Searcher;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractSearcher implements Searcher {

    @Getter
    Set<String> keyWords;

    public AbstractSearcher() {
        keyWords = new HashSet<>();
    }

    public void setKeyWords(String... keyWord) {
        keyWords.clear();
        for (String word : keyWord) {
            keyWords.add(word.toLowerCase());
        }
    }

    /**
     * Verifica se è richiesta una ricerca dedicata overo se
     * la prima parola del testo da cercare è una parola chiave
     *
     * @param text
     * @return
     */
    protected boolean isExactSearch(String text) {
        List<String> words = StringUtil.words(text.toLowerCase());
        if (words.isEmpty()) {
            return false;
        } else {
            return getKeyWords().contains(words.get(0));
        }
    }

    /**
     * Ritorna la lista delle parole da ricercare
     *
     * @param text
     * @return
     */
    protected List<String> getWords(String text) {
        List<String> words = StringUtil.words(text.toLowerCase());
        if (isExactSearch(text)) {
            words.remove(0);
        }

        return words;
    }


}
