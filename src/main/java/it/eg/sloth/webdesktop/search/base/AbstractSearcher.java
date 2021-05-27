package it.eg.sloth.webdesktop.search.base;

import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.webdesktop.search.Searcher;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author Enrico Grillini
 */
public abstract class AbstractSearcher implements Searcher {

    @Getter
    Set<String> keyWords;

    protected AbstractSearcher() {
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
