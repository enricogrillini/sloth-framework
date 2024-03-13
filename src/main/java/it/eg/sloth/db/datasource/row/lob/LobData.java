package it.eg.sloth.db.datasource.row.lob;

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
 * <p>
 *
 * @author Enrico Grillini
 */
public abstract class LobData<O extends Object> {

    public static final int OFF_LINE = 0;
    public static final int ON_LINE = 1;
    public static final int CHANGED = 2;

    private int status;

    protected O value;

    protected LobData() {
        setStatus(OFF_LINE);
        value = null;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public O getValue() {
        return value;
    }

    public void setValue(O value) {
        setStatus(CHANGED);
        this.value = value;
    }

}
