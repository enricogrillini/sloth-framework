package it.eg.sloth.webdesktop.history;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.webdesktop.search.SearchRelevance;
import it.eg.sloth.webdesktop.search.Searcher;
import it.eg.sloth.webdesktop.search.model.SuggestionList;
import it.eg.sloth.webdesktop.search.model.suggestion.SimpleSuggestion;
import it.eg.sloth.webdesktop.search.model.suggestion.Suggestion;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
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
public class HistoryManager {

    LinkedList<HistoryEntry> queue;

    public HistoryManager() {
        queue = new LinkedList<>();
    }

    public Collection<HistoryEntry> getHistoryCollection() {
        return Collections.unmodifiableCollection(queue);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void add(String code, String description, String detail, String icon, String href) {
        for (HistoryEntry historyEntry : getHistoryCollection()) {
            if (BaseFunction.equals(code, historyEntry.getCode())) {
                queue.remove(historyEntry);
            }
        }

        queue.push(new HistoryEntry(code, description, detail, icon, href));
        if (queue.size() > 10) {
            queue.removeLast();
        }
    }
}
